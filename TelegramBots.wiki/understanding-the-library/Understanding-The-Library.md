# Understanding the Library

This little handy guide will not teach you how to do a particular thing in the library. 
It will also not teach you how to create bots or anything in the liking of that. 
For that take a look at the [Getting Started] guide

This guide will give you a general understanding on how the library function and will answer a lot of frequently 
asked questions. I recommend everyone who wants to work with this library more to read this guide.

## Topics
* The library and the bot API
* Understanding how the API is mapped in the library
* The general infrastructure of the library

## The Library and the Bot API
Often in Conversations about the library they talk about the API or Application Programming Interface. Sometimes these 
terms are used interchangeably, which is not correct.

To understand the differences between those two things lets take a look at this diagram

![](Telegram-Diagram.png)

As you might have noticed. Our bot never actually talks to the user directly. Actually, Every communication between user 
and bot happens on the Telegram Servers (A little disclaimer. I actually don't know the entire infrastructure of telegram. 
So take everything between Bot API and Telegram Client with a grain of salt)

Important is that the Library communicates with the Telegram Bot API for everything. Sending Messages, Sending Pictures, 
and receiving Updates from Telegram

So we can conclude that the Library is the part of your code that handles all the communication between your bot and the
Bot API. The Bot API is the actual interface telegram offers to implement bots. Everything we can do in the library, 
we can also do directly calling the library.

Take this piece of code:
```java
AbsSender ourBot = getOurBot();

GetMe getMe = new GetMe();

User bot = ourBot.execute(getMe);
```

If we do this for one of our bots this is what theBot will look like:<br>
![](Bot_intellij.png)

(you better be grateful for that picture. Spend an eternity trying to find a username)

We can also go ahead and just call the bot api directly:<br>
![](Bot_curl.png)

We get the same result. The library just handily maps it to an object for us to work with.

So how do we find out how to do things with the library?

## Understanding how the API is mapped in the library
The base for all our operations is the [telegram bot api documentations](https://core.telegram.org/bots/api) . This 220 kB monster of a website will tell us almost everything we need to know about how to use the api.

It is split into multiple sections. We can ignore the sections *Authorizing your bot", "Making Requests" and "Getting Updates". These things are done for us by the library.

So, let's see how we can send a poll for example. First, lets take a look at the *Available Methods*. It already gives us a bunch of send methods

* sendMessage
* sendPhoto
* sendAudio
* sendDocument
* sendVideo
* sendAnimation
* sendVoice
* sendVideoNote
* sendMediaGroup
* sendLocation
* sendVenue
* sendContact
* sendPoll
* sendChatAction

One of them sticks out to us. *sendPoll* looks promising, and when we take a look at the description