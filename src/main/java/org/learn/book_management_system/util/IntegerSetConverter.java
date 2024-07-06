package org.learn.book_management_system.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Converter
@RequiredArgsConstructor
public class IntegerSetConverter implements AttributeConverter<Set<Integer>, String> {
    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Set<Integer> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (Exception ex) {
            return Collections.emptySet();
        }
    }
}
