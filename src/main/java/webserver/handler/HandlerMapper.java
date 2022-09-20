package webserver.handler;

import webserver.handler.file.FileHandler;
import webserver.handler.user.UserCreateHandler;
import webserver.handler.user.UserLoginHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    Map<String, Handler> handlerMap = new HashMap<>();

    public HandlerMapper() {
        handlerMap.put("/user/create", new UserCreateHandler()); // 회원 가입
        handlerMap.put("/user/login", new UserLoginHandler()); // 로그인
    }

    public Handler handlerMapping(String path){
        if(handlerMap.containsKey(path)){
            return handlerMap.get(path);
        }
        return new FileHandler();
    }
}
