package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @version 10.4
 * Position of a rectangular area on a story or TON wallet card.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AreaPosition implements BotApiObject, Validable {
    private static final String X_PERCENTAGE_FIELD = "x_percentage";
    private static final String Y_PERCENTAGE_FIELD = "y_percentage";
    private static final String WIDTH_PERCENTAGE_FIELD = "width_percentage";
    private static final String HEIGHT_PERCENTAGE_FIELD = "height_percentage";
    private static final String ROTATION_ANGLE_FIELD = "rotation_angle";
    private static final String CORNER_RADIUS_PERCENTAGE_FIELD = "corner_radius_percentage";

    /**
     * The abscissa of the area's center, as a percentage of the media width
     */
    @JsonProperty(X_PERCENTAGE_FIELD)
    @NonNull
    private Float xPercentage;

    /**
     * The ordinate of the area's center, as a percentage of the media height
     */
    @JsonProperty(Y_PERCENTAGE_FIELD)
    @NonNull
    private Float yPercentage;

    /**
     * The width of the area's rectangle, as a percentage of the media width
     */
    @JsonProperty(WIDTH_PERCENTAGE_FIELD)
    @NonNull
    private Float widthPercentage;

    /**
     * The height of the area's rectangle, as a percentage of the media height
     */
    @JsonProperty(HEIGHT_PERCENTAGE_FIELD)
    @NonNull
    private Float heightPercentage;

    /**
     * The clockwise rotation angle of the rectangle, in degrees; 0-360
     */
    @JsonProperty(ROTATION_ANGLE_FIELD)
    @NonNull
    private Float rotationAngle;

    /**
     * The radius of the rectangle corner rounding, as a percentage of the media width
     */
    @JsonProperty(CORNER_RADIUS_PERCENTAGE_FIELD)
    @NonNull
    private Float cornerRadiusPercentage;

    public static AreaPositionBuilder builder() {
        return new AreaPositionBuilder();
    }

    public static class AreaPositionBuilder {
        private Float xPercentage;
        private Float yPercentage;
        private Float widthPercentage;
        private Float heightPercentage;
        private Float rotationAngle;
        private Float cornerRadiusPercentage;

        public AreaPositionBuilder xPercentage(Float value) { this.xPercentage = value; return this; }
        public AreaPositionBuilder yPercentage(Float value) { this.yPercentage = value; return this; }
        public AreaPositionBuilder widthPercentage(Float value) { this.widthPercentage = value; return this; }
        public AreaPositionBuilder heightPercentage(Float value) { this.heightPercentage = value; return this; }
        public AreaPositionBuilder rotationAngle(Float value) { this.rotationAngle = value; return this; }
        public AreaPositionBuilder cornerRadiusPercentage(Float value) { this.cornerRadiusPercentage = value; return this; }

        public AreaPosition build() {
            return new AreaPosition(xPercentage, yPercentage, widthPercentage, heightPercentage, rotationAngle, cornerRadiusPercentage);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (xPercentage == null || yPercentage == null || widthPercentage == null || heightPercentage == null
                || rotationAngle == null || cornerRadiusPercentage == null) {
            throw new TelegramApiValidationException("All area position values must be provided", this);
        }
        if (!Float.isFinite(rotationAngle) || rotationAngle < 0 || rotationAngle > 360) {
            throw new TelegramApiValidationException("RotationAngle must be between 0 and 360 degrees", this);
        }
    }
}