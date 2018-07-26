package org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

public abstract class ManCommand extends BotCommand implements IManCommand {
	
	private final String extendedDescription;
	
	/**
	 * Create a new ManCommand providing a commandIdentifier, a short description and the extended description
	 * @param commandIdentifier the unique identifier of this command (e.g. the command string to enter into chat)
     * @param description the description of this command
	 * @param extendedDescription The extended Description for the Command, should provide detailed information about arguments and possible options
	 */
	public ManCommand(String commandIdentifier, String description, String extendedDescription) {
		super(commandIdentifier, description);
		this.extendedDescription = extendedDescription;
	}
	
	/* (non-Javadoc)
	 * @see org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.IManCommand#getExtendedDescription()
	 */
	@Override
	public String getExtendedDescription() {
		return extendedDescription;
	}
	
	/* (non-Javadoc)
	 * @see org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.IManCommand#toMan()
	 */
	@Override
	public String toMan() {
		StringBuilder sb = new StringBuilder(toString());
		sb.append(System.lineSeparator())
		.append("-----------------")
		.append(System.lineSeparator());
		if (getExtendedDescription() != null) sb.append(getExtendedDescription());
		return sb.toString();
	}

}
