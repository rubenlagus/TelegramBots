package org.telegram.telegrambots.webhook;


import io.javalin.json.PipedStreamExecutor;
import kotlin.Unit;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * Adapts Jackson 3's {@link JsonMapper} to Javalin's {@link io.javalin.json.JsonMapper} interface.
 * <p>
 * Implementation is inspired by {@link io.javalin.json.JavalinJackson}.
 */
@RequiredArgsConstructor
public class Jackson3Mapper implements io.javalin.json.JsonMapper {
    private final JsonMapper mapper;
    private final PipedStreamExecutor pipedStreamExecutor;

    public Jackson3Mapper() {
        this(new JsonMapper(), false);
    }

    public Jackson3Mapper(@NotNull JsonMapper jsonMapper, boolean useVirtualThreads) {
        this.mapper = jsonMapper;
        this.pipedStreamExecutor = new PipedStreamExecutor(useVirtualThreads);
    }

    @Override
    public @NotNull <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
        return mapper.readValue(json, mapper.constructType(targetType));
    }

    @Override
    public @NotNull String toJsonString(@NotNull Object obj, @NotNull Type type) {
        return mapper.writeValueAsString(obj);
    }

    @Override
    public @NotNull <T> T fromJsonStream(@NotNull InputStream json, @NotNull Type targetType) {
        return mapper.readValue(json, mapper.constructType(targetType));
    }

    @Override
    public @NotNull InputStream toJsonStream(@NotNull Object obj, @NotNull Type type) {
        if (obj instanceof String s) {
            return new ByteArrayInputStream(s.getBytes());
        }

        return pipedStreamExecutor.getInputStream(pipedOutputStream -> {
            mapper.writeValue(pipedOutputStream, obj);

            return Unit.INSTANCE;
        });
    }

    @Override
    public void writeToOutputStream(@NotNull Stream<?> stream, @NotNull OutputStream outputStream) {
        try (var sequenceWriter = mapper.writer().writeValuesAsArray(outputStream)) {
            stream.forEach(sequenceWriter::write);
        }
    }
}
