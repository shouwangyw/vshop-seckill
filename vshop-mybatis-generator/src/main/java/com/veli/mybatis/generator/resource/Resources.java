package com.veli.mybatis.generator.resource;

import java.io.InputStream;

/**
 * @author yangwei
 */
public class Resources {
    public static InputStream getResourceAsStream(String resource) {
        if (resource == null || "".equals(resource)) {
            return null;
        }

        return Resources.class.getClassLoader().getResourceAsStream(resource);
    }
}