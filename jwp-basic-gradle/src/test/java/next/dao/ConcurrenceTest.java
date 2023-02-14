package next.dao;

import core.jdbc.ConnectionManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

public class ConcurrenceTest {



    @Test
    void 테스트1() {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            Runnable worker = new WorkerThread("http://localhost:8080/user/create");
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println(ConnectionManager.connectionCodes);
    }

    class WorkerThread implements Runnable {
        private final String url;

        public WorkerThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(this.url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);


                String builder = "userId=" + URLEncoder.encode(String.valueOf(Math.round(Math.random() * 100000000)), StandardCharsets.UTF_8)
                        + "&password=" + URLEncoder.encode("value2", StandardCharsets.UTF_8)
                        + "&name=" + URLEncoder.encode("value2", StandardCharsets.UTF_8)
                        + "&email=" + URLEncoder.encode("value2", StandardCharsets.UTF_8);

                // form 데이터를 Output Stream에 쓰기
                OutputStream os = con.getOutputStream();
                os.write(builder.getBytes());
                os.flush();
                int responseCode = con.getResponseCode();
                //System.out.println("Sending 'GET' request to URL : " + this.url + "Response Code : " + responseCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}