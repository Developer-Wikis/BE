package com.developer.wiki.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringTrimModule extends SimpleModule {

  public StringTrimModule() {
    addDeserializer(String.class, new StdScalarDeserializer<>(String.class) {
      @Override
      public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
          throws IOException {
        return jsonParser.getValueAsString().trim();
      }
    });
  }
}