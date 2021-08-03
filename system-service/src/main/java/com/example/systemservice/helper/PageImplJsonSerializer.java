package com.example.systemservice.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.io.IOException;

@JsonComponent
public class PageImplJsonSerializer extends JsonSerializer<PageImpl<?>> {

    @Override
    public void serialize(PageImpl page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("content", page.getContent());
        jsonGenerator.writeBooleanField("first", page.isFirst());
        jsonGenerator.writeBooleanField("last", page.isLast());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("numberOfElements", page.getNumberOfElements());
        jsonGenerator.writeNumberField("pageSize", page.getSize());
        jsonGenerator.writeNumberField("pageNumber", page.getNumber());
        Sort sort = page.getSort();
        jsonGenerator.writeArrayFieldStart("sort");
        for (Sort.Order order : sort) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("property", order.getProperty());
            jsonGenerator.writeStringField("direction", order.getDirection().name());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}