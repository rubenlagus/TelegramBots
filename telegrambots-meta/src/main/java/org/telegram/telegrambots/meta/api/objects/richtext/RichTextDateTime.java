package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Formatted date and time.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextDateTime implements RichText {
    public static final String TYPE = "date_time";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";
    private static final String UNIX_TIME_FIELD = "unix_time";
    private static final String DATE_TIME_FORMAT_FIELD = "date_time_format";

    /**
     * Type of the rich text, always "date_time"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;

    /**
     * The Unix time associated with the entity
     */
    @JsonProperty(UNIX_TIME_FIELD)
    @NonNull
    private Integer unixTime;

    /**
     * The string that defines the formatting of the date and time
     */
    @JsonProperty(DATE_TIME_FORMAT_FIELD)
    @NonNull
    private String dateTimeFormat;
}
