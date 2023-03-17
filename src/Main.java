import readingPP.core.ParseContent;
import readingPP.files.ReadFile;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String content = ReadFile.fromFile();
        String[] contentByString = ParseContent.createListPayment(content);

String s="ХХХХХ";
        char firstChar = s.charAt(0);


        int firstCharInt = (int) firstChar;
        System.out.println("firstCharInt = " + firstCharInt);
        System.out.println("-------");

        System.out.println(getCharType(firstChar));

        System.out.println(contentByString);

        Scanner scanner = new Scanner(System.in);

        double sum = 0;
        int count = 0;

        while (count != 4) {
            System.out.print("Введите число: ");
            String input = scanner.nextLine();

            try {
                double number = Double.parseDouble(input);
                sum += number;
                count++;
            } catch (NumberFormatException e) {
                System.out.println("То, что вы ввели, не похоже на число");
            }
        }

        double result = sum / 4;
        System.out.println("Результат: " + result);
    }
    private static String getCharType(char ch) {
        if (Character.isDigit(ch)) {
            return "D";
        } else if (Character.isAlphabetic(ch)) {
            if (Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.CYRILLIC)) {
                return "CYRILLIC";
            } else if (Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.BASIC_LATIN)){
                return "BASIC_LATIN";
            }
        }
        return "U";
    }
}

