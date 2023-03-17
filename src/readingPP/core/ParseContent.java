package readingPP.core;

import java.util.ArrayList;
import java.util.List;

public class ParseContent {
    //private List<String> fieldsPayment = new ArrayList<>();

   public static String[] createListPayment(String content) {
        String[] parts = content.split("\r\n");
        return parts;
    }

}
