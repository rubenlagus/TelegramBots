So, you just wanna program a own Telegram bot with @rubenlagus library TelegramBots? Then I'm going to show you how to start ;)

##### Table of Contents  
[Preperations](#preperations)  
[Let's code!](#lets_code)  
[FAQ](#faq)  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. [How to get picture?](#question_how_to_get_picture)  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. [How to send photos?](#question_how_to_send_photos)  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. [How to use custom keyboards?](#question_how_to_use_custom_keyboards)  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. [How can I compile my project?](#question_how_to_compile)  
    
    

<a name="preperations"/>
## Preperations
First you need to download the latest .jar from the Release site [here](https://github.com/rubenlagus/TelegramBots/releases). You can choose between Jar dependencies and wthout. If you don't know what to choose, we recommend to download the full jar with all dependencies ;)

Next, you need to integrate it into your project.

Create a new project for your bot, in the example below we are showing you how to do it in eclipse. But of course, you are free to code in whatever IDE you want ;)

If you don't know how to include a external .jar into your Eclipse project, maybe [this](https://www.youtube.com/watch?v=VWnfHkBgO1I) video is helpfull for you

More information on how to use it with Gradly or Maven, can you find here [here](https://jitpack.io/#rubenlagus/TelegramBots/v2.3.3.4)


<a name="lets_code"/>
## Let's code!
Create a new class that is extending one of the following

```TelegramLongPollingBot``` -> bot is asking the Telegram servers continuously if new updates are available

```TelegramWebhookBot``` -> our bot is "called" from the Telegram servers when updates are available

```TelegramLongPollingCommandBot``` -> simply like TelegramLongPollingBot, but based around the idea of Commands


Due to the fact that the TelegramLongPollingBot is a little bit less complicated than the others, are we going to work with him in the following.


Extend ```TelegramLongPollingBot``` with one of your own classes. If we want that the bot can work normally, we must implement the following methods: ```getBotUsername():String```, ```getBotToken():String``` und ```onUpdateReceived(update: Update)```

The first two methods are really easy to implement. Jus create a new class that contains all our informations for the bot (username, token and maybe in the future some database information)

At the end it could look like this:

```java
public class BotConfig {

	public static final String BOT_USERNAME = "echoBot";
	
	public static final String BOT_TOKEN = "{you secret bot token you got from the BotFather}";
	
}
```

After it, return these static variables like this one:
```java
@Override
	public String getBotToken() {
		return BotConfig.BOT_TOKEN;
	}

```

The last method could look like this:

```java
@Override
        public void onUpdateReceived(Update update) {

                //check if the update has a message
                if(update.hasMessage()){
                        Message message = update.getMessage();

                        //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
                        if(message.hasText()){

                                //create a object that contains the information to send back the message
                                SendMessage sendMessageRequest = new SendMessage();
                                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                                sendMessageRequest.setText("you said: " + message.getText());
                                try {
                                        sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                                } catch (TelegramApiException e) {
                                        //do some error handling
                                }
                        }
                }

        }
```
The princip is rather easy, we have an ```update```, we see that it contains a text -> we create a Send*** object, fill it up with all necessary infos (user/chatId, text) and fire it up
        
If you want to send also other types of media (such as photos or audio), then check out our FAQ at the end if this HOWTO. Also please check out the [TelegramBotsExample](https://github.com/rubenlagus/TelegramBotsExample) repo. It contains usefull informations on how to use the lib from @rubenlagus.
        
If you have questions that are not handled here or in the example repo, than feel free to open a new Issue :P
In any case, you can reach us in our chat at [our group chat](https://telegram.me/JavaBotsApi) ;)
        
But to be in context: our bot is not ready, yet. It lacks of a used way to the tell lib that we have a super cool new UpdateHandler written, you remember? 😏
        
        
```java
public static void main(String[] args) {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MyProjectHandler());
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
```
        
<a name="faq"/>
## FAQ  
   
   Question: <a name="question_how_to_get_picture"/>
   <b>How to get a picture?</b>  
   Answer: A ```onUpdateReceived()``` Method that just downloads every Photo that users send to the bot could look like this:  
   
```java
   @Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();

            //check if we got some photos
            if(message.getPhoto() != null){


                /*
                 * Just save our received photos in a list. At this point, we do not really have the photos. We have just their id.
                 * And with their id's we can download them from telegram servers
                 */

                for(int i = 0; i < photos.size(); i++){

                    GetFile getFileRequest = new GetFile();

                    getFileRequest.setFileId(photos.get(0).getFileId());
                    try {

                        //we send a request with our fileId to get our filePath.
                        File file = getFile(getFileRequest);

                        /*
                         * After that, we can now start to save them on our local machine.
                         * Please have a look on the API specification, on how to download the files with their filepaths you just got in the code above
                         * https://core.telegram.org/bots/api#file
                         * 
                         * Just replace <file_path> with File.getFilePath();
                         */

                        // In this example, we just print here the filePaths
                        System.out.println(file.getFilePath());
                    } catch (TelegramApiException e) {
                        //TODO: so some error handling
                    }

                }

            }

        }


    }
    
```
   Question: <a name="question_how_to_send_photos"/>
    <b>How to send photos?</b>  
    Answer:  
      
   
```java
   @Override
    public void onUpdateReceived(Update update) {

        //check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();

            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if(message.hasText()){

                if(message.getText().equals("/wiki")){

                    SendPhoto sendPhotoRequest = new SendPhoto();
                    sendPhotoRequest.setChatId(message.getChatId().toString());


                                                    //path: String,                     photoName: String
                    sendPhotoRequest.setNewPhoto("/home/marcel/Downloads/potd_wikipedia.jpg", "Good Friday.jpg"); //
                    try {
                        sendPhoto(sendPhotoRequest);
                    } catch (TelegramApiException e) {
                        /*
                         * Do some error handling
                         * e.printStackTrace();
                         */
                    }

                }

            }
        }

    }
```
   
   This method uploads the photo every time the user send the bot /wiki. Telegram stores the files we upload on their server. And if next time someone wants to retrieve THE SAME photo we uploaded some time ago, you should use instead of SendPhoto.setNewPhoto() the SendPhoto.setPhoto() method. This method has just one parameter, file_id.
   
   Question: <a name="question_how_to_use_custom_keyboards"/> 
   <b>How to use custom keyboards?</b>    
   Answer:
```java
   //first, create a normal SendMessage object. Our CutsomKeyboard is attached to this object. But don't forget to assign a text. Otherwise the user get's no message
SendMessage sendMessageRequest = this.selectLanguage();


sendMessageRequest.setChatId(message.getChatId().toString());
sendMessageRequest.setText("Change language");

//ready top takeoff!
sendMessage(sendMessageRequest);
```

The selectLanguage() method could look like this (you also could put the code of the extra methode in your main() )

```java
public static List<KeyboardRow> selectLanguage(Message message){

        /* changed from "List<List<String>>" to "List<KeyboardRow>"
        * Now we have just a one dimension array. Like a stack or something like that
        */
        List<KeyboardRow> rows = new ArrayList<KeyboardRow>();
        rows.add(ReplyKeyboardFabricator.getHeader(message));

        //Instead of a list that collects our strings, or "buttons" for each row, now we have a own Object for that
        KeyboardRow row = new KeyboardRow();
        row.add(LocalisationService.getInstance().getString("change_language", DatabaseManager.getInstance().getUserLanguage(EchoHandler.getUserId(message))));
        rows.add(row);

        row = new KeyboardRow();
        row.add("🇦🇹 Deutsch (Östereich)");
        rows.add(row);

        //I just reused the object above. Of cource you could also create a new Keyboardrow for each real row
        row = new KeyboardRow();
        row.add("🇩🇪 Deutsch (Deutschland)");
        rows.add(row);

        row = new KeyboardRow();
        row.add("🇧🇷 Português");
        rows.add(row);

        row = new KeyboardRow();
        row.add("🇺🇸 English (United States)");
        rows.add(row);

        return rows;

    }
```

For more example on this, please have a look at [this](https://github.com/rubenlagus/TelegramBotsExample/blob/master/src/main/java/org/telegram/updateshandlers/WeatherHandlers.java) bot in the example repo
   
   <a name="question_how_to_compile"/>
   Question: <b>How can I compile my project?</b>     
   Answer: This is just one way, how you can compile it (here with maven). The example below below is compiling the TelegramBotsExample repo. 
   [![asciicast](https://asciinema.org/a/4np9i2u9onuitkg287ism23kj.png)](https://asciinema.org/a/4np9i2u9onuitkg287ism23kj)
   
   
