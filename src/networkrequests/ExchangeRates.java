package networkrequests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
Напишите HTTP-клиент, который будет работать с API api.exchangerate.host и получать от него курсы валют.
Клиент должен отправлять GET-запрос по адресу https://api.exchangerate.host/convert.
Необходимо добавить два параметра:
base — параметр, указывающий базовую валюту для конвертации;
symbols — параметр, в котором через запятую перечислены все необходимые валюты для конвертации.
Параметр base должен быть равен RUB, а параметр symbols — USD,EUR. Ответ от сервера нужен в формате JSON.
 */
public class ExchangeRates {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        // укажите URL запроса, включая его параметры
        URI url = URI.create("https://api.exchangerate.host/convert/?base=RUB&symbols=USD,EUR");

        // создайте объект, описывающий запрос с необходимой информацией
        HttpRequest request = HttpRequest.newBuilder().GET().uri(url).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Код статуса: " + response.statusCode());
            System.out.println("Ответ: " + response.body());
        } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
            System.out.println("Во время выполнения запроса ресурса по URL-адресу: '" + url + "' возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }
}
//Решение прошло все проверки!  (＾ｖ＾)