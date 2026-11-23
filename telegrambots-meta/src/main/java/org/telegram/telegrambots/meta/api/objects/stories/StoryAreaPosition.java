package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.AreaPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

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
    private static final long serialVersionUID = -7956681739742451307L;
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("xPercentage", Float.class),
            new ObjectStreamField("yPercentage", Float.class),
            new ObjectStreamField("widthPercentage", Float.class),
            new ObjectStreamField("heightPercentage", Float.class),
            new ObjectStreamField("rotationAngle", Float.class),
            new ObjectStreamField("cornerRadiusPercentage", Float.class)
    };
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

    private void writeObject(ObjectOutputStream output) throws IOException {
        ObjectOutputStream.PutField fields = output.putFields();
        fields.put("xPercentage", getXPercentage());
        fields.put("yPercentage", getYPercentage());
        fields.put("widthPercentage", getWidthPercentage());
        fields.put("heightPercentage", getHeightPercentage());
        fields.put("rotationAngle", getRotationAngle());
        fields.put("cornerRadiusPercentage", getCornerRadiusPercentage());
        output.writeFields();
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = input.readFields();
        setXPercentage((Float) fields.get("xPercentage", null));
        setYPercentage((Float) fields.get("yPercentage", null));
        setWidthPercentage((Float) fields.get("widthPercentage", null));
        setHeightPercentage((Float) fields.get("heightPercentage", null));
        setRotationAngle((Float) fields.get("rotationAngle", null));
        setCornerRadiusPercentage((Float) fields.get("cornerRadiusPercentage", null));
    }
}
