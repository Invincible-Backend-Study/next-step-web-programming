package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HeaderReader {
    private static BufferedReader bufferedReader;

    public HeaderReader(final InputStream in) {
        bufferedReader = new BufferedReader(new InputStreamReader(in));
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }
}
