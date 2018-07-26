package org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand;

/**
 * Represents a Command that, aside the normal command description provides an extended Description similar to the output of the Linux <i>man</i> command
 * @author Lukas Prediger(Chase)
 * @version 1.0.0
 */
public interface IManCommand {
	/**
	 * Returns the extended Description of this command
	 * @return the extendedDescription
	 */
	String getExtendedDescription();
	
	/**
	 * Returns a String Representations of the Command and it's extended Description.
	 * @return a String representing the Command and it's extended Description
	 */
	String toMan();

}