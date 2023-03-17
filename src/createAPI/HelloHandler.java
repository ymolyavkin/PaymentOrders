package createAPI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class HelloHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // System.out.println("Началась обработка /hello запроса от клиента.");

        String method = httpExchange.getRequestMethod();
        System.out.println("Началась обработка " + method + " /hello запроса от клиента.");

        String path = httpExchange.getRequestURI().getPath();
        String name = path.split("/")[2];
        System.out.println("Имя: " + name);

        String response = "Привет, " + name + "!";
        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }

        /*InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        System.out.println("Тело запроса:\n" + body);

        String response = "Hello! Рады видеть на нашем сервере.";
        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }*/

        /**
         * @Override
         *         public void handle(HttpExchange httpExchange) throws IOException {
         *
         *             String method = httpExchange.getRequestMethod();
         *             System.out.println("Началась обработка " + method + " /hello запроса от клиента.");
         *
         *             String path = httpExchange.getRequestURI().getPath();
         *             String name = path.split("/")[2];
         *             System.out.println("Имя: " + name);
         *
         *             String response = "Привет, " + name + "!";
         *             httpExchange.sendResponseHeaders(200, 0);
         *
         *             try (OutputStream os = httpExchange.getResponseBody()) {
         *                 os.write(response.getBytes());
         *             }
         */
    }
}
