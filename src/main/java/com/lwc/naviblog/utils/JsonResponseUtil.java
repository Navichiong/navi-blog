package com.lwc.naviblog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponseUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonResponseUtil() {

    }

    public static void writeObject(Object object, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(MAPPER.writeValueAsString(object));
        out.flush();
        out.close();
    }
}
