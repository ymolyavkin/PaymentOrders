package readingPP.core;

import java.io.*;

public class ReportReader {
    public String readContent(Reader myInput) throws IOException {
        final BufferedReader reader = new BufferedReader(myInput);
        final StringBuilder contentBuilder = new StringBuilder();
        while (reader.ready()) {
            contentBuilder.append(reader.readLine());
        }
        return contentBuilder.toString();
    }
    public void writeContent(String output, Writer writer) throws IOException {
        final BufferedWriter bufferedWriter=new BufferedWriter(writer);
        for (String l : output.split("\n")) {
            bufferedWriter.write(l);
        }
    }
}
