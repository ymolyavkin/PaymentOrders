package endpointcomment;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import socialnetwork.LastLikeInfo;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static endpointcomment.Practicum.posts;

public class PostsHandler implements HttpHandler {
    private static final Gson gson = new Gson();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_POSTS: {
                handleGetPosts(exchange);
                break;
            }
            case GET_COMMENTS: {
                handleGetComments(exchange);
                break;
            }
            case POST_COMMENT: {
                handlePostComments(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    private void handlePostComments(HttpExchange exchange) throws IOException {
        // реализуйте обработку добавления комментария
        /*Comment newComment = new Comment("I am", "I will be fine");
        // сериализуйте объект
        String commentSerialized = gson.toJson(newComment);

        System.out.println("Serialized comment: " + commentSerialized);*/
        // извлеките идентификатор поста и обработайте исключительные ситуации
        Optional<Integer> postIdOpt = getPostId(exchange);
        String response;
        if (postIdOpt.isPresent()) {
            int postId = postIdOpt.get();
            boolean postFound = false;
            for (Post post : posts) {
                if (postId == post.getId()) {
                    postFound = true;
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

                    // Чтобы десериализовать JSON в объект класса Comment,
                    // используйте метод fromJson объекта класса Gson.
                    // Если в запросе был передан некорректный JSON, то при его конвертации будет выброшено
                    // исключение JsonSyntaxException. Обработайте его так, как указано в задании.
                    try {
                        Comment comment = gson.fromJson(body, Comment.class);
                        if (comment.getText().isBlank() || comment.getUser().isBlank()) {
                            response="Поля комментария не могут быть пустыми";
                            writeResponse(exchange, response,400);
                        }
                        post.addComment(comment);
                        response="Комментарий добавлен";
                        writeResponse(exchange, response,201);
                    } catch (JsonSyntaxException exception) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                    }
                }
            }
            if (!postFound) {
                response = "Пост с идентификатором " + postId + " не найден";
                writeResponse(exchange, response,404);
            }
        } else {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
        }


            /* Получите тело запроса в виде текста в формате JSON и преобразуйте его в объект Comment.
            Учтите, что может быть передан некоректный JSON — эту ситуацию нужно обработать.
            Подумайте, какие ещё ситуации требуют обработки. */


        // найдите пост с указанным идентификатором и добавьте в него комментарий
    }

    /* Чтобы получить тело запроса, используйте метод getRequestBody класса HttpExchange.
    Он возвращает объект InputStream, у которого есть метод readAllBytes.
    Используйте его для преобразования тела запроса в строку.
    Чтобы десериализовать JSON в объект класса Comment, используйте метод fromJson объекта класса Gson.
    Если в запросе был передан некорректный JSON, то при его конвертации будет выброшено исключение
    JsonSyntaxException. Обработайте его так, как указано в задании.

     */
    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("posts")) {
            return Endpoint.GET_POSTS;
        }
        if (pathParts.length == 4 && pathParts[1].equals("posts") && pathParts[3].equals("comments")) {
            if (requestMethod.equals("GET")) {
                return Endpoint.GET_COMMENTS;
            }
            if (requestMethod.equals("POST")) {
                return Endpoint.POST_COMMENT;
            }
        }
        return Endpoint.UNKNOWN;
    }

    private void handleGetPosts(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(posts), 200);
    }

    private void handleGetComments(HttpExchange exchange) throws IOException {
        Optional<Integer> postIdOpt = getPostId(exchange);
        if (postIdOpt.isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
            return;
        }
        int postId = postIdOpt.get();

        for (Post post : posts) {
            if (post.getId() == postId) {
                String commentsJson = gson.toJson(post.getCommentaries());
                writeResponse(exchange, commentsJson, 200);
                return;
            }
        }

        writeResponse(exchange, "Пост с идентификатором " + postId + " не найден", 404);
    }

    private Optional<Integer> getPostId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    enum Endpoint {GET_POSTS, GET_COMMENTS, POST_COMMENT, UNKNOWN}
}
