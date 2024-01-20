package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * 2-letter ISO 3166-1 alpha-2 country code
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress implements BotApiObject {
    private static final String COUNTRY_CODE_FIELD = "country_code";
    private static final String STATE_FIELD = "state";
    private static final String CITY_FIELD = "city";
    private static final String STREET_LINE1_FIELD = "street_line1";
    private static final String STREET_LINE2_FIELD = "street_line2";
    private static final String POST_CODE_FIELD = "post_code";

    /**
     * Two-letter ISO 3166-1 alpha-2 country code
     */
    @JsonProperty(COUNTRY_CODE_FIELD)
    private String countryCode;
    @JsonProperty(STATE_FIELD)
    private String state; ///< State, if applicable
    @JsonProperty(CITY_FIELD)
    private String city; ///< City
    @JsonProperty(STREET_LINE1_FIELD)
    private String streetLine1; ///< First line for the address
    @JsonProperty(STREET_LINE2_FIELD)
    private String streetLine2; ///< Second line for the address
    @JsonProperty(POST_CODE_FIELD)
    private String postCode; ///< Address post code
}
