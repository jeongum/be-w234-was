package webserver.handler.memo;

import constant.Path;
import model.Memo;
import service.MemoService;
import util.ByteArrayUtils;
import util.FileUtils;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoListHandler implements Handler {
    private MemoService memoService = MemoService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();

        List<Memo> memos = memoService.list();

        header.put("mime", request.getMime().getValue());
        byte[] body = makeMemoListHtml(memos);
        return new HttpResponse(HttpStatusCode.OK, header, body);
    }

    private byte[] makeMemoListHtml(List<Memo> memos) {
        byte[] header = FileUtils.getContents(Path.MEMO_LIST_HEADER);
        byte[] content = ByteArrayUtils.stringToByteArray(generateMemoTable(memos));
        byte[] footer = FileUtils.getContents(Path.MEMO_LIST_FOOTER);

        return ByteArrayUtils.concat(header, content, footer);
    }

    private String generateMemoTable(List<Memo> memos) {
        StringBuilder sb = new StringBuilder();
        memos.stream().forEach(memo -> {
            sb.append("<tr>");
            sb.append("<td>").append(memo.getCreateDate()).append("</td>");
            sb.append("<td>").append(memo.getAuthor()).append("</td>");
            sb.append("<td>").append(memo.getContents()).append("</td>");
            sb.append("</tr>");
        });
        return sb.toString();
    }
}
