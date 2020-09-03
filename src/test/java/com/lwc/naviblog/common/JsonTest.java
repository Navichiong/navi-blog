package com.lwc.naviblog.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwc.naviblog.model.Blog;
import org.junit.Test;


public class JsonTest {

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    public void test() throws JsonProcessingException {
        String json = "{\n" +
                "  \"id\": 1,\n" +
                "  \"content\": \"Hello World\",\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Java\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Spring\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"type\": {\n" +
                "    \"id:\": \"1\",\n" +
                "    \"typeName\": \"Java后端\"\n" +
                "  }\n" +
                "}";
        Blog blog = JSON.parseObject(json, Blog.class);
        System.out.println(blog);
    }
}
