package org.telegram.telegrambots.extensions.bots.timedbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Daniil Nikanov aka JetCoder
 */

public abstract class TimedSendLongPollingBot extends TelegramLongPollingBot
{
	private static final long MANY_CHATS_SEND_INTERVAL = 33;
	private static final long ONE_CHAT_SEND_INTERVAL = 1000;
	private static final long CHAT_INACTIVE_INTERVAL = 1000 * 60 * 10;
	private final Timer mSendTimer = new Timer(true);
	private final ConcurrentHashMap<Long,MessageQueue> mMessagesMap = new ConcurrentHashMap<>(32, 0.75f, 1);
	private final ArrayList<MessageQueue> mSendQueues = new ArrayList<>();
	private final AtomicBoolean mSendRequested = new AtomicBoolean(false);

	private final class MessageSenderTask extends TimerTask
	{
		@Override
		public void run()
		{
			//mSendRequested used for optimisation to not traverse all mMessagesMap 30 times per second all the time
			if (!mSendRequested.getAndSet(false))
				return;

			long currentTime = System.currentTimeMillis();
			mSendQueues.clear();
			boolean processNext = false;

			//First step - find all chats in which already allowed to send message (passed more than 1000 ms from previuos send)
			Iterator<Map.Entry<Long,MessageQueue>> it = mMessagesMap.entrySet().iterator();
			while (it.hasNext())
			{
				MessageQueue queue = it.next().getValue();
				int state = queue.getCurrentState(currentTime); //Actual check here
				if (state == MessageQueue.GET_MESSAGE)
				{
					mSendQueues.add(queue);
					processNext = true;
				}
				else if (state == MessageQueue.WAIT)
				{
					processNext = true;
				}
				else if (state == MessageQueue.DELETE)
				{
					it.remove();
				}
			}

			//If any of chats are in state WAIT or GET_MESSAGE, request another iteration
			if (processNext)
				mSendRequested.set(true);

			//Second step - find oldest waiting queue and peek it's message
			MessageQueue sendQueue = null;
			long oldestPutTime = Long.MAX_VALUE;
			for (MessageQueue queue : mSendQueues) {
				long putTime = queue.getPutTime();
				if (putTime < oldestPutTime) {
					oldestPutTime = putTime;
					sendQueue = queue;
				}
			}
			if (sendQueue == null) //Possible if on first step wasn't found any chats in state GET_MESSAGE
				return;

			//Invoke the send callback. ChatId is passed for possible additional processing
			sendMessageCallback(sendQueue.getChatId(), sendQueue.getMessage(currentTime));
		}
	}

	private static class MessageQueue
	{
		public static final int EMPTY = 0; //Queue is empty
		public static final int WAIT = 1; //Queue has message(s) but not yet allowed to send
		public static final int DELETE = 2; //No one message of given queue was sent longer than CHAT_INACTIVE_INTERVAL, delete for optimisation
		public static final int GET_MESSAGE = 3; //Queue has message(s) and ready to send
		private final ConcurrentLinkedQueue<Object> mQueue = new ConcurrentLinkedQueue<>();
		private final Long mChatId;
		private long mLastSendTime; //Time of last peek from queue
		private volatile long mLastPutTime; //Time of last put into queue

		public MessageQueue(Long chatId)
		{
			mChatId = chatId;
		}

		public synchronized void putMessage(Object msg)
		{
			mQueue.add(msg);
			mLastPutTime = System.currentTimeMillis();
		}

		public synchronized int getCurrentState(long currentTime)
		{
			//currentTime is passed as parameter for optimisation to do not recall currentTimeMillis() many times
			long interval = currentTime - mLastSendTime;
			boolean empty = mQueue.isEmpty();
			if (!empty && interval > ONE_CHAT_SEND_INTERVAL)
				return GET_MESSAGE;
			else if (interval > CHAT_INACTIVE_INTERVAL)
				return DELETE;
			else if (empty)
				return EMPTY;
			else
				return WAIT;
		}

		public synchronized Object getMessage(long currentTime)
		{
			mLastSendTime = currentTime;
			return mQueue.poll();
		}

		public long getPutTime()
		{
			return mLastPutTime;
		}

		public Long getChatId()
		{
			return mChatId;
		}
	}

	//Constructor

	/**
	 * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
	 */
	@Deprecated
	protected TimedSendLongPollingBot()
	{
		super();
		mSendTimer.schedule(new MessageSenderTask(), MANY_CHATS_SEND_INTERVAL, MANY_CHATS_SEND_INTERVAL);
	}

	protected TimedSendLongPollingBot(String botToken)
	{
		super(botToken);
		mSendTimer.schedule(new MessageSenderTask(), MANY_CHATS_SEND_INTERVAL, MANY_CHATS_SEND_INTERVAL);
	}

	//Something like destructor
	public void finish()
	{
		mSendTimer.cancel();
	}

	//This method must be called instead of all calls to sendMessage(), editMessageText(), sendChatAction() etc...
	//for performing time-based sends obeying the basic Telegram limits (no more 30 msgs per second in different chats,
	//no more 1 msg per second in any single chat). The method can be safely called from multiple threads.
	//Order of sends to any given chat is guaranteed to remain the same as order of calls. Sends to different chats can be out-of-order depending on timing.
	//Example of call:
	/**
		 SendMessage sendMessageRequest = new SendMessage();
		 sendMessageRequest.setChatId(chatId);
		 sendMessageRequest.setParseMode("HTML");
		 sendMessageRequest.setText(text);
		 sendMessageRequest.setReplyMarkup(replyMarkup);
		 sendTimed(chatId, sendMessageRequest);  // <-- Instead of sendMessage() API method
	 */
	public void sendTimed(Long chatId, Object messageRequest)
	{
		MessageQueue queue = mMessagesMap.get(chatId);
		if (queue == null)
		{
			queue = new MessageQueue(chatId);
			queue.putMessage(messageRequest);
			mMessagesMap.put(chatId, queue);
		}
		else
		{
			queue.putMessage(messageRequest);
			mMessagesMap.putIfAbsent(chatId, queue); //Double check, because the queue can be removed from hashmap on state DELETE
		}
		mSendRequested.set(true);
	}


	//When time of actual send comes this callback is called with the same parameters as in call to sendTimed().
	//It's implementation must use 'instanceof' operator to distinguish type of the message request and call the proper send API method.
	//Example:
	/**
	//@Override
	public void sendMessageCallback(Long chatId, Object messageRequest)
	{
		try
		{
			if (messageRequest instanceof SendMessage)
			{
				sendMessage((SendMessage) messageRequest);
			}
			else if (messageRequest instanceof EditMessageText)
			{
				editMessageText((EditMessageText) messageRequest);
			}
			else if (messageRequest instanceof SendChatAction)
			{
				sendChatAction((SendChatAction) messageRequest);
			}
			else if (messageRequest instanceof SendDocument)
			{
				sendDocument((SendDocument) messageRequest);
			}
	        //Etc...
		}
		catch (TelegramApiException e)
		{
			LOG.error(EXC, e);
		}
		catch (Exception e)
		{
			LOG.fatal(EXC, e);
		}
	}
    */
	public abstract void sendMessageCallback(Long chatId, Object messageRequest);
}
