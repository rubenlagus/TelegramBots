package org.telegram.telegrambots.api.objects.inlinequery;



import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a result of an inline query that was chosen by the user and sent to their chat
 * partner.
 * @date 01 of January of 2016
 */
public class ChosenInlineQuery implements BotApiObject {
    private static final String RESULTID_FIELD = "result_id";
    private static final String FROM_FIELD = "from";
    private static final String LOCATION_FIELD = "location";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String QUERY_FIELD = "query";


    private String result_id; ///< The unique identifier for the result that was chosen.

    private User from; ///< The user that chose the result.

    private Location location; ///< Optional. Sender location, only for bots that require user location
    /**
     * Optional.
     * Identifier of the sent inline message.
     * Available only if there is an inline keyboard attached to the message.
     * Will be also received in callback queries and can be used to edit the message.
     */

    private String inline_message_id;

    private String query; ///< The query that was used to obtain the result.

    public ChosenInlineQuery() {
        super();
    }

    public String getResultId() {
        return result_id;
    }

    public User getFrom() {
        return from;
    }

    public Location getLocation() {
        return location;
    }

    public String getInlineMessageId() {
        return inline_message_id;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "ChosenInlineQuery{" +
                "resultId='" + result_id + '\'' +
                ", from=" + from +
                ", location=" + location +
                ", inlineMessageId=" + result_id +
                ", query='" + query + '\'' +
                '}';
    }
}
