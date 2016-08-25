# How to use TelegramBots with Eclipse

If you **don't** need to modify the sources, you can just download the jar file from [here](https://github.com/rubenlagus/TelegramBots/releases) and import it as an external library.

If you need to modify the sources, follow these steps:

### Step 1: Install Maven
To get started, you need to install the Maven-Plugin for Eclipse. Click on Help > Eclipse Marketplace. After it finished loading, search for “Maven”. 
Now install **“Maven (Java EE) Integration for Eclipse WTP”**. You have to restart, after the installation is completed.

### Step 2: Download the Project
Now you need to get the project itself. 
Visit [TelegramBots - github.com][1] and choose “Download Zip”, to download the zip-file. [Here] is a direct link if you are lazy.

### Step 3: Create folder
Now let’s setup the project-folder. Go to your Eclipse-Workspace and create a folder (name it whatever you want it to be called). I just named it **“TelegramBotApi”**. Afterwards copy everything from the zip-file into that folder.

### Step 4: Import project
To import your project into the workspace you have to go to File > Import > (Folder) Maven > Existing Maven Projects > Next. Choose the folder, you have created in Step 3. In my case: **“TelegramBotApi”**. Click on finish and let Maven import the dependencies.

### Step 5: Setting the compliance level
In this last step you need to change the Compiler compliance level. To do this right-click on your Project (in my case **“TelegramBotApi”**) > Properties > Java Compiler. Uncheck the “Use compliance from…” if necessary and set it to 1.8

*Now you are done. Everything should work fine.*

[Google Drive]:https://goo.gl/5jd40w
[here]:https://github.com/rubenlagus/TelegramBots/archive/master.zip
[1]:https://github.com/rubenlagus/TelegramBots/
