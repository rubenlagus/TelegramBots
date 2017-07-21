package org.telegram.telegrambots.api.objects.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 3.2
 * This object describes the position on faces where a mask should be placed by default.
 */
public class MaskPosition implements InputBotApiObject, Validable {
    private static final String POINT_FIELD = "point";
    private static final String XSHIFT_FIELD = "x_shift";
    private static final String YSHIFT_FIELD = "y_shift";
    private static final String SCALE_FIELD = "scale";

    @JsonProperty(POINT_FIELD)
    private String point; ///< The part of the face relative to which the mask should be placed. One of “forehead”, “eyes”, “mouth”, or “chin”.
    @JsonProperty(XSHIFT_FIELD)
    private Float xShift; ///< Shift by X-axis measured in widths of the mask scaled to the face size, from left to right. For example, choosing -1.0 will place mask just to the left of the default mask position.
    @JsonProperty(YSHIFT_FIELD)
    private Float yShift; ///< Shift by Y-axis measured in heights of the mask scaled to the face size, from top to bottom. For example, 1.0 will place the mask just below the default mask position.
    @JsonProperty(SCALE_FIELD)
    private Float scale; ///< Mask scaling coefficient. For example, 2.0 means double size.

    public MaskPosition() {
        super();
    }

    public String getPoint() {
        return point;
    }

    public Float getxShift() {
        return xShift;
    }

    public Float getyShift() {
        return yShift;
    }

    public Float getScale() {
        return scale;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setxShift(Float xShift) {
        this.xShift = xShift;
    }

    public void setyShift(Float yShift) {
        this.yShift = yShift;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "MaskPosition{" +
                "point='" + point + '\'' +
                ", xShift=" + xShift +
                ", yShift=" + yShift +
                ", scale=" + scale +
                '}';
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (point == null || point.isEmpty()) {
            throw new TelegramApiValidationException("point can't be empty", this);
        }
        if (xShift == null) {
            throw new TelegramApiValidationException("xShift can't be empty", this);
        }
        if (yShift == null) {
            throw new TelegramApiValidationException("yShift can't be empty", this);
        }
        if (scale == null) {
            throw new TelegramApiValidationException("scale can't be empty", this);
        }
    }
}
