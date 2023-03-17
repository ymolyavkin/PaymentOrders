package createAPI;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HelloJobHandler implements HttpHandler {
    //private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;

        // извлеките метод из запроса
        String method = httpExchange.getRequestMethod();

        switch (method) {
            // сформируйте ответ в случае, если был вызван POST-метод
            case "POST":
                // извлеките тело запроса
                InputStream inputStream = httpExchange.getRequestBody();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String body = bufferedReader.readLine();
               // String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

                //String body = ... bufferedReader.readLine();

                // извлеките path из запроса
                String path = httpExchange.getRequestURI().getPath();
                String[] splitStrings = path.split("/");
                String profession = splitStrings[2];
                String name = splitStrings[3];
                // String path = ...
                // а из path — профессию и имя
                //  String profession = ...
                //    String name = ...

                // извлеките заголовок
                Headers requestHeaders = httpExchange.getRequestHeaders();
                // List<String> wishGoodDay = ...
                List<String> wishGoodDay = requestHeaders.get("X-Wish-Good-Day");
                if ((wishGoodDay != null) && (wishGoodDay.contains("true"))) {
                    response = body + profession + ", " + name + "!" + "Хорошего дня!";
                } else {
                    // {приветствие}, {профессия} {имя}!».
                    response = "Привет, " + profession + " " + name + "!";
                }

                // соберите ответ
                //  response = ...
                // не забудьте про ответ для остальных методов
                break;
            case "GET":
                //response = "Вы использовали метод GET!";
                response = "Здравствуйте!";
                break;
            default:
                response = "Некорректный метод!";
        }


        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
