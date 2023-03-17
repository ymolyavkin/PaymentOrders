package practicumserver;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Practicum {
    private static final int PORT = 8080;

    // IOException могут сгенерировать методы create() и bind(...)
    public static void main(String[] args) throws IOException {

        UserPost post = new UserPost();
        post.setPhotoUrl("https://new-social-network.site/images/928476864.jpg");
        post.setUserId(97_748);
        post.setDescription("Классное фото!");
        post.setLikesQuantity(753);

        // сконвертируйте publicationDateString в экземпляр LocalDate
        String publicationDateString = "25--12--2020";
        //DateTimeFormatter и метода ofPattern("dd--MM--yyyy")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd--MM--yyyy");
        //LocalDateTime startTime = LocalDateTime.parse(parts[2], formatter);
        LocalDate publicationDate = LocalDate.parse(publicationDateString, formatter);
        post.setPublicationDate(publicationDate);

        // создайте экземпляр Gson
        Gson gson = new Gson();
        // сериализуйте объект
        String postSerialized = gson.toJson(post);
        // String postSerialized = ...;
        System.out.println("Serialized post: " + postSerialized);

        // десериализуйте объект
        // UserPost postDeserialized = ...;
        UserPost postDeserialized = gson.fromJson(postSerialized, UserPost.class);
        System.out.println("Deserialized post: " + postDeserialized);


        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/hello", new HelloHandler());
        httpServer.createContext("/day", new DayHandler());
        httpServer.start(); // запускаем сервер


        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        String[] week = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        Random random = new Random();
        int index = random.nextInt(7);
        System.out.println(index);
        String response = week[index];
        System.out.println(response);
    }
}
