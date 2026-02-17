package org.telegram.telegrambots.meta.api.objects;

import tools.jackson.databind.util.StdConverter;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 */
public class InputFileConverter extends StdConverter<InputFile, String> {
    @Override
    public String convert(InputFile value) {
        return value.getAttachName();
    }
}
