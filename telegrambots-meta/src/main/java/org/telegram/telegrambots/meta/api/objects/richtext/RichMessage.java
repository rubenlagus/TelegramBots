package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlock;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Rich formatted message.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichMessage implements BotApiObject {
    private static final String BLOCKS_FIELD = "blocks";
    private static final String IS_RTL_FIELD = "is_rtl";

    /**
     * Content of the message
     */
    @JsonProperty(BLOCKS_FIELD)
    @NonNull
    private List<RichBlock> blocks;

    /**
     * Optional. True, if the rich message must be shown right-to-left
     */
    @JsonProperty(IS_RTL_FIELD)
    private Boolean isRtl;
}
