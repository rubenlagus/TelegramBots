package org.telegram.telegrambots.api.objects.inlinequery;



import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an incoming inline query. When the user sends an empty query, your
 * bot could return some default or trending results.
 * @date 01 of January of 2016
 */
public class InlineQuery implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String LOCATION_FIELD = "location";
    private static final String QUERY_FIELD = "query";
    private static final String OFFSET_FIELD = "offset";


    private String id; ///< Unique identifier for this query

    private User from; ///< Sender

    private Location location; ///< Optional. Sender location, only for bots that request user location

    private String query; ///< Text of the query

    private String offset; ///< Offset of the results to be returned, can be controlled by the bot

    public InlineQuery() {
        super();
    }

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public Location getLocation() {
        return location;
    }

    public String getQuery() {
        return query;
    }

    public String getOffset() {
        return offset;
    }

    public boolean hasQuery() {
        return query != null && !query.isEmpty();
    }

    public boolean hasLocation() {
        return location != null;
    }

    @Override
    public String toString() {
        return "InlineQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", location=" + location +
                ", query='" + query + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}

