package practicumserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class DayHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /day запроса от клиента.");
        String[] week = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        Random random = new Random();
        int index = random.nextInt(7);
        String response = week[index];
        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
