package webserver.handler;

import webserver.handler.file.FileHandler;
import webserver.handler.memo.MemoListHandler;
import webserver.handler.user.UserCreateHandler;
import webserver.handler.user.UserListHandler;
import webserver.handler.user.UserLoginHandler;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    Map<String, Handler> handlerMap = new HashMap<>();

    public HandlerMapper() {
        handlerMap.put("/user/create", new UserCreateHandler()); // 회원 가입
        handlerMap.put("/user/login", new UserLoginHandler()); // 로그인
        handlerMap.put("/user/list", new UserListHandler()); // 사용자 목록
        handlerMap.put("/memo/list", new MemoListHandler()); // 사용자 목록
    }

    public Handler handlerMapping(String path){
        if(handlerMap.containsKey(path)){
            return handlerMap.get(path);
        }
        return new FileHandler();
    }
}
