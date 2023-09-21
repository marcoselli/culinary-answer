package br.dev.marco.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class JsonUtil<T> {
    private final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private Class<T> className;

    @Inject
    ObjectMapper mapper;

    public JsonUtil(Class<T> className) {
        this.className = className;
    }

    public Optional<T> jsonStringToObject(String jsonString) {
        try {
            return Optional.of(mapper.readValue(jsonString,className));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error processing json to object");
            return Optional.empty();
        }
    }







}
