package webserver.handler.user;

import constant.Path;
import lombok.extern.slf4j.Slf4j;
import model.User;
import service.UserService;
import util.ByteArrayUtils;
import util.FileUtils;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class UserListHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.isLogin()) {
            Map<String, String> header = new HashMap<>();

            List<User> users = userService.list();

            header.put("mime", request.getMime().getValue());
            byte[] body = makeUserListHtml(users);
            return new HttpResponse(HttpStatusCode.OK, header, body);
        }
        return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.LOGIN));
    }

    private byte[] makeUserListHtml(List<User> users) {
        byte[] header = FileUtils.getContents(Path.USER_LIST_HEADER);
        byte[] footer = FileUtils.getContents(Path.USER_LIST_FOOTER);
        byte[] content = ByteArrayUtils.stringToByteArray(generateUserTable(users));

        return ByteArrayUtils.concat(header, content, footer);
    }

    private String generateUserTable(List<User> users) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, users.size()).forEach(i -> {
            User u = users.get(i);
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(i + 1).append("</th>");
            sb.append("<td>").append(u.getUserId()).append("</td>");
            sb.append("<td>").append(u.getName()).append("</td>");
            sb.append("<td>").append(u.getEmail()).append("</td>");
            sb.append("</tr>");
        });
        return sb.toString();
    }
}
