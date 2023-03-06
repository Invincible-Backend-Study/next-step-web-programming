package core.rc;

import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public enum Primitive {
    INT("int", Integer.class) {
        @SneakyThrows
        @Override
        public void setField(Object instance, Field field, String value) {
            field.setAccessible(true);
            field.setInt(instance, parseInt(value));
        }

        private int parseInt(String value) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return 0;
            }
        }
    },
    LONG("long", Long.class) {
        @SneakyThrows
        @Override
        public void setField(Object instance, Field field, String value) {
            field.setAccessible(true);
            try {
                field.setLong(field, Long.parseLong(value));
            } catch (IllegalAccessException e) {
                field.setLong(field, 0L);
            }
        }
    },
    CHAR("char", Character.class) {
        @SneakyThrows
        @Override
        public void setField(Object instance, Field field, String value) {
            field.setAccessible(true);
            field.setChar(instance, value.charAt(0));
        }
    },
    STRING("string", String.class) {
        @SneakyThrows
        @Override
        public void setField(Object instance, Field field, String value) {
            field.setAccessible(true);
            field.set(instance, value);
        }
    };
    private final String name;
    private final Class<?> type;

    public static Primitive of(String name) {
        return Arrays.stream(values()).filter(primitive -> primitive.name.equals(name)).findAny()
                .orElse(null);
    }

    public static Primitive of(Class<?> type) {
        final var previousFind = of(type.getName());

        if (previousFind != null) {
            return previousFind;
        }

        return Arrays.stream(values()).filter(primitive -> primitive.type.equals(type)).findAny()
                .orElse(null);
    }

    public abstract void setField(Object instance, Field field, String value);

}
