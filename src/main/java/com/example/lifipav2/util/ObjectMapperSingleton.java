package com.example.lifipav2.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {

    private static ObjectMapper mapper;

    private ObjectMapperSingleton() {
        mapper = new ObjectMapper();
    }

    public static ObjectMapper getInstance() {
        if (mapper == null) mapper = new ObjectMapper();
        return mapper;
    }
}
