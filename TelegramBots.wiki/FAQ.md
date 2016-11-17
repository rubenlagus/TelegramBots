* [How to get picture?](#how_to_get_picture)  
* [How to send photos?](#how_to_send_photos)  
* [How to use custom keyboards?](#how_to_use_custom_keyboards)
* [How can I run my bot?](#how_to_host)     
* [How can I compile my project?](#how_to_compile)


## <a id="how_to_get_picture"></a>How to download photo? ##

To download a picture (or any other file), you will need the `file_path` of the file. Let start by finding the photo we want to download, the following method will extract the `PhotoSize` from a photo sent to the bot (in our case, we are taken the bigger size of those provided):

```java
public PhotoSize getPhoto(Update update) {
    // Check that the update contains a message and the message has a photo
    if (update.hasMessage() && update.getMessage().hasPhoto()) {
        // When receiving a photo, you usually get different sizes of it
        List<PhotoSize> photos = update.getMessage().getPhoto();

        // We fetch the bigger photo
        return photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null);
    }

    // Return null if not found
    return null;
}
```

Once we have the *photo* we have to options: The `file_path` is already present or we need to get it, the following method will handle both of them and return the final `file_path`:

```java
public String getFilePath(PhotoSize photo) {
    Objects.requireNonNull(photo);

    if (photo.hasFilePath()) { // If the file_path is already present, we are done!
        return photo.getFilePath();
    } else { // If not, let find it
        // We create a GetFile method and set the file_id from the photo
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(photo.getFileId());
        try {
            // We execute the method using AbsSender::getFile method.
            File file = getFile(getFileMethod);
            // We now have the file_path
            return file.getFilePath();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    return null; // Just in case
}
```

Now that we have the `file_path` we can download it:

```java
public java.io.File downloadPhotoByFilePath(String filePath) {
    try {
        // Download the file calling AbsSender::downloadFile method
        return downloadFile(filePath);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }

    return null;
}
```

The returned `java.io.File` object will be your photo

## <a id="how_to_send_photos"></a>How to send photos? ##

There are several method to send a photo to an user using `sendPhoto` method: With a `file_id`, with an `url` or uploading the file. In this example, we assume that we already have the *chat_id* where we want to send the photo:

```java
    public void sendImageFromUrl(String url, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(url);
        try {
            // Execute the method
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImageFromFileId(String fileId, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(fileId);
        try {
            // Execute the method
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a method overload)
        sendPhotoRequest.setNewPhoto(new File(filePath));
        try {
            // Execute the method
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
```


## <a id="how_to_use_custom_keyboards"></a>How to use custom keyboards? ##

Custom keyboards can be appended to messages using the `setReplyMarkup`. In this example, we will build a simple [ReplyKeyboardMarkup](https://core.telegram.org/bots/api#replykeyboardmarkup) with two rows and three buttons per row, but you can also use other types like [ReplyKeyboardHide](https://core.telegram.org/bots/api#replykeyboardhide), [ForceReply](https://core.telegram.org/bots/api#forcereply) or [InlineKeyboardMarkup](https://core.telegram.org/bots/api#inlinekeyboardmarkup): 

```java
    public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Custom message text");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
```

## <a id="how_to_host"></a>How can I run my bot? ##

You don't need to spend a lot of money into hosting your own telegram bot. Basically, there are two options around how to host:
   
   1. Hosting on your own hardware. It can be a Mini-PC like a Raspberry Pi. The costs for the hardware (~35€) and annual costs for power (~7-8€) are low. Keep in mind that your internet connection might be limited and a Mini-Pc is not ideal for a large users base.
   2. Run your bot in a Virtual Server/dedicated root server. There are many hosters out there that are providing cheap servers that fit your needs. The cheapest one should be openVZ-Containers or a KVM vServer. Example providers are [Hetzner](https://www.hetzner.de/ot/), [DigitalOcean](https://www.digitalocean.com/),  (are providing systems that have a high availability but cost's a bit more) and [OVH](https://ovh.com)

## <a id="how_to_compile"></a>How can I compile my project? ##

This is just one way, how you can compile it (here with maven). The example below below is compiling the TelegramBotsExample repo. 
   [![asciicast](https://asciinema.org/a/4np9i2u9onuitkg287ism23kj.png)](https://asciinema.org/a/4np9i2u9onuitkg287ism23kj)