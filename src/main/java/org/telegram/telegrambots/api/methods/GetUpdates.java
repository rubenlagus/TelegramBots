package org.telegram.telegrambots.api.methods;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IToJson;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to receive incoming updates using long polling (wiki).
 * An Array of Update objects is returned.
 * @date 20 of June of 2015
 */
public class GetUpdates implements IToJson {
    public static final String PATH = "getupdates";

    public static final String OFFSET_FIELD = "offset";
    /**
     * Optional	Identifier of the first update to be returned.
     * Must be greater by one than the highest among the identifiers of previously received updates.
     * By default, updates starting with the earliest unconfirmed update are returned.
     */
    private Integer offset;
    public static final String LIMIT_FIELD = "limit";
    /**
     * Optional	Limits the number of updates to be retrieved.
     * Values between 1—100 are accepted. Defaults to 100
     */
    private Integer limit;
    public static final String TIMEOUT_FIELD = "timeout";
    /**
     * Optional	Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     */
    private Integer timeout;

    public GetUpdates() {
        super();
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        if (offset != null) {
            jsonObject.put(OFFSET_FIELD, offset);
        }
        if (limit != null) {
            jsonObject.put(LIMIT_FIELD, limit);
        }
        if (timeout != null) {
            jsonObject.put(TIMEOUT_FIELD, timeout);
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "GetUpdates{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", timeout=" + timeout +
                '}';
    }
}
