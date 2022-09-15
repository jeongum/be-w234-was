package handler;

import handler.file.FileHandler;
import handler.user.UserCreateHandler;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    Map<String, Handler> handlerMap = new HashMap<>();

    public HandlerMapper() {
        handlerMap.put("file handler", new FileHandler()); // 정적 파일
        handlerMap.put("/user/create", new UserCreateHandler()); // 회원 가입
    }

    public Handler handlerMapping(String path){
        File file = new File("./webapp" + path);
        if(file.isFile()) return handlerMap.get("file handler");
        return handlerMap.get(path);
    }
}
