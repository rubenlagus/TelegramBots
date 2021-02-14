package org.telegram.telegrambots.extensions.bots.commandbot;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Varun Singh
 * This class adds argument functionality to a given message that a user typed, that also counts as a command
 */
public class CommandMessage {
    /** 
     * The message associated with the command
     */
    private Message commandMessage;

    /**
     * Constructor for CommandMessage
     * @param msg The message that represents the command that the user typed
     * Preconditions: 
        * msg.isCommand() is true
        * msg.hasText() is true
     * Postcondition:
        * The developer has the ability to determine which command the message is executing
     */
    public CommandMessage(Message msg) {
        commandMessage = msg;
    }

    public Message getCommandMessage() {
        return commandMessage;
    }

    /**
     * @return the String representation of the message for ease
     */
    public String getMessageText() {
        return commandMessage.getText();
    }
    
    /**
     * Separates the given text after the command into an ordered array of arguments
     * @return each argument as a String item in an array
     */
    public String[] getArgs() {
        return getArgsStr().split(" ");
    }
    
    /**
     * @return the text of the command WITHOUT the slash
     * For example, if /language English
     */
    public String getCommandText() {
        return getCommandText(false).substring(1);     
    }
    
    /**
     * @return The text of the message from and including the '/' to the space,
     * which assumes the end of the command name and start of the command arguments
     */
    public String getCommandText(boolean includeHandle) {
        if (includeHandle || commandMessage.isUserMessage()) {
            return getMessageText().substring(0, getMessageText().indexOf(" "));
        } else
            return getMessageText().substring(0, getMessageText().indexOf("@"));
    }

    /**
     * Singles out the text of the message after the command
     * @return all the arguments grouped in one string
     */
    public String getArgsStr() {
        return getMessageText().substring(getCommandText(true).length() + 1); // Skip space
    }

}
