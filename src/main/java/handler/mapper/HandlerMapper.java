package handler.mapper;

import handler.FileHandler;
import handler.Handler;
import handler.UserHandler;
import webserver.http.request.HttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    Map<String, Handler> handlerMap = new HashMap<>();

    public HandlerMapper() {
        handlerMap.put("file handler", new FileHandler());
        handlerMap.put("/user/create", new UserHandler());
    }

    public Handler handlerMapping(String path){
        File file = new File("./webapp" + path);
        if(file.isFile()) return handlerMap.get("file handler");
        return handlerMap.get(path);
    }
}
