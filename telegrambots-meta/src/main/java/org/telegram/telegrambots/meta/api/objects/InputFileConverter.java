package org.telegram.telegrambots.meta.api.objects;

import tools.jackson.databind.util.StdConverter;

public class InputFileConverter extends StdConverter<InputFile, String> {
    @Override
    public String convert(InputFile value) {
        return value.getAttachName();
    }
}
