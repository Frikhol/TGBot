package ru.shil.tgbot;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class App 
{
    public static void main( String[] args ) throws URISyntaxException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uri = new URI("https://pl193.plemiona.pl/game.php?village=13718&screen=market&mode=exchange");
            HttpUriRequest request = RequestBuilder.get()
                    .setUri(uri)
                    .build();
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
