package networkrequests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreateHTTPClient {
    public static void main(String[] args) {
        String url = "https://www.ya.ru/";

        // добавьте отлов и обработку исключений вокруг кода ниже
        URI uri = URI.create(url);

        // создаём запрос
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        // создаём HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // отправляем запрос
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // выводим код состояния и тело ответа
        System.out.println("Код состояния: " + response.statusCode());
        System.out.println("Тело ответа: " + response.body());
    }
}
