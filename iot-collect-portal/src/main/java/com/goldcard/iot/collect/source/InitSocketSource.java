package com.goldcard.iot.collect.source;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcard.iot.collect.configure.SocketServerConfigure;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InitSocketSource {
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = ResourceUtils.getFile("classpath:server/SocketServerInfo.json");
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
                    SocketServerConfigure.class);
            List<SocketServerConfigure> list = mapper.readValue(file,
                    javaType);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
