package multiendpoint;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelloHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        System.out.println("Началась обработка " + method + " /hello запроса от клиента.");

        String response;
        switch (method) {
            case "POST":
                //BufferedReader bufferedReader = new BufferedReader()
              //  bufferedReader.readLine();
                InputStream inputStream = httpExchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                System.out.println("Тело запроса:\n" + body);
                //response = "Вы использовали метод POST!";
                Headers requestHeaders = httpExchange.getRequestHeaders();
                /*System.out.println("Заголовки запроса: " + requestHeaders.entrySet());

                var requestHeadersSet = requestHeaders.entrySet();
                //Set<Entry<String, List<String>>>
                System.out.println(requestHeadersSet);


                for (Map.Entry<String, List<String>> stringListEntry : requestHeadersSet) {
                    String key = "";
                    List<String> value = new ArrayList<>();

                    Map.Entry<String, List<String>>  listFromHeader = new AbstractMap.SimpleImmutableEntry<>(key, value);

                    Map.Entry<String, List<String>> tempMap = stringListEntry;
                    System.out.println(tempMap.getKey() + " -> " + tempMap.getValue());
                    System.out.println(listFromHeader.getKey() + " -> " + listFromHeader.getValue());



                }

                *//**
                 * возвращается ответ вида «{приветствие}, {профессия} {имя}! Хорошего дня!».
                 *//*

                String headers = requestHeaders.entrySet().toString();
                //System.out.println(headers);
*/
                String path = httpExchange.getRequestURI().getPath();
                String job = path.split("/")[2];
                String name = path.split("/")[3];
                System.out.println("Имя: " + name);
                System.out.println("Профессия: " + job);

                /**
                 * Получить HTTP-метод можно с помощью httpExchange.getRequestMethod().
                 * Так как нужно тело запроса из одной строки, можно воспользоваться методом bufferedReader.readLine();.
                 * Получить профессию и имя из запроса можно с помощью следующего кода.
                 *   String path = httpExchange.getRequestURI().getPath();
                 *   String[] splitStrings = path.split("/");
                 *   String profession = splitStrings[2];
                 *   String name = splitStrings[3];
                 *
                 * Проверить нужный заголовок поможет этот код.
                 *   List<String> wishGoodDay = requestHeaders.get("X-Wish-Good-Day");
                 *   if ((wishGoodDay != null) && (wishGoodDay.contains("true"))) {
                 *       ...
                 *   }
                 *
                 */

                List<String> wishGoodDay = requestHeaders.get("X-Wish-Good-Day");
                if ((wishGoodDay != null) && (wishGoodDay.contains("true"))) {
                    response = "Привет, " + job + ", " + name + "!" + "Хорошего дня!";
                } else {
                    // {приветствие}, {профессия} {имя}!».
                    response = "Привет, " + job + " " + name + "!";
                }
                break;
            case "GET":
                //response = "Вы использовали метод GET!";
                response = "Здравствуйте!";
                break;
            default:
                response = "Вы использовали какой-то другой метод!";
        }
/**
 * Для метода POST /hello/{профессия}/{имя}:
 * Тело запроса считается приветствием.
 * Если передан заголовок X-Wish-Good-Day=true, возвращается ответ вида «{приветствие}, {профессия} {имя}! Хорошего дня!».
 * Например, на запрос /hello/программист/Егор, заголовок wishGoodDay=true и тело запроса Доброе утро корректный ответ — «Доброе утро, программист Егор! Хорошего дня!».
 * Если заголовок отсутствует, возвращается ответ вида «{приветствие}, {профессия} {имя}!».
 *
 * String path = httpExchange.getRequestURI().getPath();
 *         String name = path.split("/")[2];
 *         System.out.println("Имя: " + name);
 *
 *         String response = "Привет, " + name + "!";
 *         httpExchange.sendResponseHeaders(200, 0);
 */
        // httpExchange.sendResponseHeaders(200, 0);




        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
