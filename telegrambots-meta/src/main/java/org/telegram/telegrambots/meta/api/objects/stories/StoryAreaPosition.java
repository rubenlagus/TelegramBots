package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.AreaPosition;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes the position of a clickable area within a story.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class StoryAreaPosition extends AreaPosition {
    public StoryAreaPosition(Float xPercentage, Float yPercentage, Float widthPercentage, Float heightPercentage,
                             Float rotationAngle, Float cornerRadiusPercentage) {
        super(xPercentage, yPercentage, widthPercentage, heightPercentage, rotationAngle, cornerRadiusPercentage);
    }

    public static class StoryAreaPositionBuilder extends AreaPosition.AreaPositionBuilder {
        @Override
        public StoryAreaPositionBuilder xPercentage(Float value) { super.xPercentage(value); return this; }
        @Override
        public StoryAreaPositionBuilder yPercentage(Float value) { super.yPercentage(value); return this; }
        @Override
        public StoryAreaPositionBuilder widthPercentage(Float value) { super.widthPercentage(value); return this; }
        @Override
        public StoryAreaPositionBuilder heightPercentage(Float value) { super.heightPercentage(value); return this; }
        @Override
        public StoryAreaPositionBuilder rotationAngle(Float value) { super.rotationAngle(value); return this; }
        @Override
        public StoryAreaPositionBuilder cornerRadiusPercentage(Float value) { super.cornerRadiusPercentage(value); return this; }

        @Override
        public StoryAreaPosition build() {
            AreaPosition position = super.build();
            return new StoryAreaPosition(position.getXPercentage(), position.getYPercentage(), position.getWidthPercentage(),
                    position.getHeightPercentage(), position.getRotationAngle(), position.getCornerRadiusPercentage());
        }
    }

    public static StoryAreaPositionBuilder builder() {
        return new StoryAreaPositionBuilder();
    }
}
