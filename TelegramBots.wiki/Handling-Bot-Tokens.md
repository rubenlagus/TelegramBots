* [Bot Token Rules](#bot-token-rules)
* [Using Enviroment Variables](#using-environment-variables)
  * [Setting Enviroment Variables](#setting-environment-variables)
  * [Accessing Enviroment Variables](#accessing-enviroment-variables)
* [Using Command Line Arguments](#using-command-line-arguments)

# <a id="bot-token-rules"></a> Bot Token Rules ##
* Tokens should not be hardcoded into the bot code
* Tokens should never be published
* Tokens should not be pushed into Repositorys

# <a id="using-environment-variables"></a> Using Environment Variables ###
One convenient way to inject your bot token into the application is by using Environment Variables. Environment Variables are Values that are set in the Environment the Bot is running.

Those Values are not defined in the Application and therefore are not visible in the code.

## <a id="setting-environment-variables"></a> Setting Environment Variables ###

### Windows
Enviroment Variables in Windows can be set using the Console (CMD) using
```batchfile
SETX [VARIABLE_NAME] [YOUR_BOT_TOKEN]
```

It can also be set using the Windows GUI
* From the desktop, right click the Computer icon.
* Choose Properties from the context menu.
* Click the Advanced system settings link.
* Click Environment Variables...
* In the 'User Variables for X' click New and enter a Name and your Token as the Value

### Linux & Mac
* Open the '~/.bash_profile' File
* Append the following to it:
```bash
export VARIABLE_NAME = {YOUR_BOT_TOKEN}
```
* Save the file
* Either start a new terminal or run the command above

### IntelliJ
* Go to Run->Edit Configuratuions...
* Navigate to your Java Run Configuration
* Under Enviroment->Enviroment Variables click the Folder Icon
* Click the Plus Icon to add a new Variable
* Enter a Name and your Token as the Value

### Heroku Cloud
* Naviage to your App
* In the Settings Tab under Config Vars, click "Reveal Config Vars"
* Enter a Name and your Token as the Value
* Click the "Add" button

## <a id="accessing-enviroment-variables"></a> Accessing Enviroment Variables ##

### Java
You can access the Enviroment Variables by using System.getEnv()

```java
String BOT_TOKEN = System.getenv("VARIABLE_NAME");
```

### Spring

In Spring the @Value annotation allows you to inject the Value into your class
```java
public class Bot extends TelegramLongPollingBot {
    public Bot(@Value("${VARIABLE_NAME}") String botToken) {
        this.botToken = botToken;
    }
}
```

# <a id="sing-command-line-arguments"></a> Using Command Line Arguments
An easier but not Recommended way of injecting the Bottoken is by utilizing Command Line Arguments when starting the Application

In this case your main Method is responsible for taking in the Token

```java
public static void main(String[] args) {
    String botToken = args[0];
}
```

You now have to call your jar by using
```
java -jar myBot.jar [BOT_TOKEN]
```
