package readingPP.files;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {
    private static String filePath = "75303274201.PP3";

    private static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path), Charset.forName("windows-1251"));
    }

    public static String fromFile() {
        String content = null;
        try {
            content = readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}