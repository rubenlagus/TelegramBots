package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.telegram.telegrambots.meta.api.objects.stories.StoryAreaPosition;

/**
 * @version 10.4
 * Position of a rectangular area on a story or TON wallet card.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaPosition extends StoryAreaPosition {
    public AreaPosition() {
        super();
    }

    public AreaPosition(Float xPercentage, Float yPercentage, Float widthPercentage, Float heightPercentage,
                        Float rotationAngle, Float cornerRadiusPercentage) {
        super(xPercentage, yPercentage, widthPercentage, heightPercentage, rotationAngle, cornerRadiusPercentage);
    }

    public static AreaPositionBuilder builder() {
        return new AreaPositionBuilder();
    }

    public static class AreaPositionBuilder extends StoryAreaPositionBuilder {
        @Override
        public AreaPositionBuilder xPercentage(Float value) { super.xPercentage(value); return this; }
        @Override
        public AreaPositionBuilder yPercentage(Float value) { super.yPercentage(value); return this; }
        @Override
        public AreaPositionBuilder widthPercentage(Float value) { super.widthPercentage(value); return this; }
        @Override
        public AreaPositionBuilder heightPercentage(Float value) { super.heightPercentage(value); return this; }
        @Override
        public AreaPositionBuilder rotationAngle(Float value) { super.rotationAngle(value); return this; }
        @Override
        public AreaPositionBuilder cornerRadiusPercentage(Float value) { super.cornerRadiusPercentage(value); return this; }

        @Override
        public AreaPosition build() {
            StoryAreaPosition position = super.build();
            return new AreaPosition(position.getXPercentage(), position.getYPercentage(), position.getWidthPercentage(),
                    position.getHeightPercentage(), position.getRotationAngle(), position.getCornerRadiusPercentage());
        }
    }
}