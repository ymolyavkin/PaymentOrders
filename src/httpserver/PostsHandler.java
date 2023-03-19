package httpserver;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import java.io.OutputStream;
import java.net.URI;



/**
 * GET /posts — для получения списка всех постов;
 * GET /posts/{postId}/comments — для получения комментариев к посту;
 * POST /posts/{postId}/comments — для добавления нового комментария к посту.
 * <p>
 * Чтобы сформировать нужный ответ, получите объект типа OutputStream с помощью
 * метода exchange.getResponseBody(). Обратите внимание: нужно использовать конструкцию
 * try-with-resources, чтобы OutputStream был закрыт после завершения работы с ним.
 * Помните: метод sendResponseHeaders должен быть вызван до метода getResponseBody.
 */
public class PostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        URI requestURI = exchange.getRequestURI();
        // Из экземпляра URI получить requestPath.
        String requestPath = requestURI.getPath();

        // получите информацию об эндпоинте, к которому был запрос
        Endpoint endpoint = getEndpoint(requestPath, method);

        switch (endpoint) {
            case GET_POSTS: {
                writeResponse(exchange, "Получен запрос на получение постов", 200);
                break;
            }
            case GET_COMMENTS: {
                writeResponse(exchange, "Получен запрос на получение комментариев", 200);
                break;
            }
            case POST_COMMENT: {
                writeResponse(exchange, "Получен запрос на добавление комментария", 200);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        // реализуйте этот метод

        String[] splitStrings = requestPath.split("/");
        String lastPart = splitStrings[splitStrings.length - 1];


        switch (requestMethod) {
            case "POST":
                if (lastPart.equals("comments")) {
                    return Endpoint.POST_COMMENT;
                }
                break;
            case "GET":
                //response = "Вы использовали метод GET!";
                if (lastPart.equals("comments")) {
                    return Endpoint.GET_COMMENTS;
                } else if (lastPart.equals("posts")) {
                    return Endpoint.GET_POSTS;
                }
                break;
            default:
                // "Вы использовали какой-то другой метод!";
                return Endpoint.UNKNOWN;
        }
        return Endpoint.UNKNOWN;
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
            /* Реализуйте отправку ответа, который содержит responseString в качестве тела ответа
            и responseCode в качестве кода ответа.
            Учтите, что если responseString — пустая строка, то её не нужно передавать в ответе. */
        exchange.sendResponseHeaders(responseCode, 0);

        if (!responseString.isEmpty()) {
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseString.getBytes());
            }
        }
    }

    enum Endpoint {GET_POSTS, GET_COMMENTS, POST_COMMENT, UNKNOWN}
}
/**
 * Чтобы определить, к каким эндпоинтам был сделан запрос,
 * проанализируйте путь, указанный в запросе (exchange.getRequestURI().getPath()),
 * и HTTP-метод запроса (exchange.getRequestMethod()).
 * Эти значения являются аргументами метода getEndpoint.
 * Чтобы отличить эндпоинт получения постов от эндпоинтов, связанных с комментариями,
 * проверьте, заканчивается ли путь на posts или содержит в себе ещё и comments.
 * Также проверьте в пути количество частей, разделённых слешем /.
 * Чтобы сформировать нужный ответ, получите объект типа OutputStream с помощью метода
 * exchange.getResponseBody(). Обратите внимание: нужно использовать конструкцию try-with-resources,
 * чтобы OutputStream был закрыт после завершения работы с ним.
 * Помните: метод sendResponseHeaders должен быть вызван до метода getResponseBody.
 */