package practicserversocial;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static practicserversocial.Practicum.posts;

/**
 * Продолжаем разрабатывать HTTP-сервер для социальной сети. На этом этапе нужно реализовать
 * обработку двух эндпоинтов:
 * GET /posts — для получения списка всех постов;
 * GET /posts/{postId}/comments — для получения комментариев к посту.
 * Для этого напишите следующие методы:
 * handleGetPosts — формирует HTTP-ответ, содержащий список постов в формате JSON.
 * getPostId — извлекает идентификатор поста из пути, указанного в запросе. Если идентификатор указан
 * некорректно — например, не является числом, то нужно вернуть пустой объект Optional.
 * handleGetComments — формирует HTTP-ответ, содержащий список комментариев указанного поста
 * в формате JSON. В этом методе нужно также предусмотреть обработку двух ситуаций:
 * Если был передан некорректный идентификатор поста, то ответ должен содержать текст
 * Некорректный идентификатор поста, а его код должен быть равен 400.
 * Если пост с указанным идентификатором не найден, то ответ должен содержать текст
 * Пост с идентификатором postId не найден, где вместо postId будет переданный идентификатор.
 * Код ответа должен быть равен 404.
 */

public class PostsHandler implements HttpHandler {
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

    /**
     * handleGetPosts — формирует HTTP-ответ, содержащий список постов в формате JSON.
     *
     * @param exchange
     * @throws IOException
     */
    private void handleGetPosts(HttpExchange exchange) throws IOException {
        // верните JSON, представляющий список постов. Код ответа должен быть 200.

        // создайте экземпляр Gson
        Gson gson = new Gson();
        // сериализуйте объект
        String postSerialized = gson.toJson(posts);
        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(postSerialized.getBytes());
        }
    }

    /**
     * handleGetComments — формирует HTTP-ответ, содержащий список комментариев указанного поста
     * в формате JSON. В этом методе нужно также предусмотреть обработку двух ситуаций:
     * Если был передан некорректный идентификатор поста, то ответ должен содержать текст
     * Некорректный идентификатор поста, а его код должен быть равен 400.
     * Если пост с указанным идентификатором не найден, то ответ должен содержать текст
     * Пост с идентификатором postId не найден, где вместо postId будет переданный идентификатор.
     * Код ответа должен быть равен 404.
     *
     * @param exchange
     * @throws IOException
     */
    private void handleGetComments(HttpExchange exchange) throws IOException {
        Optional<Integer> postIdOpt = getPostId(exchange);
            /* Верните комментарии к указанному посту. Код ответа должен быть 200.
            Если запрос был составлен неверно, верните сообщение об ошибке. */
        /*handleGetComments — формирует HTTP-ответ, содержащий список комментариев указанного поста
        в формате JSON. В этом методе нужно также предусмотреть обработку двух ситуаций:
Если был передан некорректный идентификатор поста, то ответ должен содержать текст Некорректный
идентификатор поста, а его код должен быть равен 400.
Если пост с указанным идентификатором не найден, то ответ должен содержать текст Пост с
идентификатором postId не найден, где вместо postId будет переданный идентификатор.
Код ответа должен быть равен 404.

         */
        // создайте экземпляр Gson
        Gson gson = new Gson();
        // сериализуйте объект

        String response = "";
        boolean postFound = false;
        if (postIdOpt.isPresent()) {
            for (Post post : posts) {
                if (postIdOpt.get() == post.getId()) {
                    postFound=true;

                    response = gson.toJson(post.getCommentaries());
                    writeResponse(exchange, response,200);
                    //return;
                }
            }
            if (!postFound) {
                response = "Пост с идентификатором " + postIdOpt.get() + " не найден";
                writeResponse(exchange, response,404);
            }
        } else {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
        }
    }

    private Optional<Integer> getPostId(HttpExchange exchange) {
        int postId;

        // извлеките path из запроса
        String path = exchange.getRequestURI().getPath();
        String[] splitStrings = path.split("/");
        String strPostId = splitStrings[2].trim();
        try {
            postId = Integer.parseInt(strPostId);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            return Optional.empty();
        }
        return Optional.of(postId);
    }

    private void handlePostComments(HttpExchange exchange) throws IOException {
        writeResponse(exchange, "Этот эндпоинт пока не реализован", 200);
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
