package com.vitran.backend.dto;

import java.lang.reflect.Field;
import java.util.Map;

public class GenericMapper {

    public static <T> void patchEntity(GenericDTO<T> dto, T entity) {
        Map<String, Object> fields = dto.getFields();

        Class<?> clazz = entity.getClass();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            try {
                Field field = clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(entity, entry.getValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to set field: " + entry.getKey(), e);
            }
        }
    }
}

