package financeadvisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.google.gson.JsonParser.parseString;

public class Converter {

    private final HttpClient client = HttpClient.newHttpClient();


    public void convert(double rubles, int currency) {
        if (currency == 1) {
            getRate("USD");
            System.out.println("Ваши сбережения в долларах: " + rubles * getRate("USD"));
        } else if (currency == 2) {
            System.out.println("Ваши сбережения в евро: " + rubles * getRate("EUR"));
        } else if (currency == 3) {
            System.out.println("Ваши сбережения в иенах: " + rubles * getRate("JPY"));
        } else {
            System.out.println("Неизвестная валюта");
        }
    }

    private double getRate(String currency) {
        Double rate = -1.0;
        String requestCurrency = "https://api.exchangerate.host/latest?base=RUB&symbols=";
        switch (currency) {
            case "USD":
                requestCurrency += "USD";
                break;
            case "EUR":
                requestCurrency += "EUR";
                break;
            case "JPY":
                requestCurrency += "JPY";
                break;
            default:
                System.out.println("Неизвестная валюта");
        }

        URI url = URI.create(requestCurrency);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // проверяем, успешно ли обработан запрос
            if (response.statusCode() == 200) {
                JsonElement jsonElement = parseString(response.body());
                if (!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                    System.out.println("Ответ от сервера не соответствует ожидаемому.");
                    return -1;
                }
                // преобразуем результат разбора текста в JSON-объект
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonObject currenciesObject = jsonObject.get("rates").getAsJsonObject();
                String rateCurrency = currenciesObject.get(currency).getAsString();
                try {
                    rate = Double.parseDouble(rateCurrency.trim());
                    return rate;
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                }
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return rate;
    }
}
//Решение прошло все проверки!  (＾ｖ＾)