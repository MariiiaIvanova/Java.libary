package ru.library.My_library_project.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class TestClient {
    public static void main(String[] args) {


        try {
            URL url = new URL("http://localhost:8080/?a=3&b=5");
            URLConnection urlConnection = url.openConnection();
           // InputStream inputStream = url.openConnection().getInputStream();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          //  InputStream inputStream = new URL("http://localhost:8080/").openConnection().getInputStream();
           // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = reader.readLine();
            System.out.println(s);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }
}
/*public class TestClient {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080/TestService";

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        TestService service = (TestService) factory.create(TestService.class, url);

        System.out.println(service.plus(9,5));
        System.out.println(service.minus(19,15));
        List<Book> allBooks = service.getAllBooks();
        for (Book horse: allHorses){
            System.out.println(book.getId()+"\t"+book.getName());
        }
    }
}*/