package bot.api;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import bot.components.Chat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;

public class Api {
    final static String url = "https://api.telegram.org/bot";
    final static String token = "6680407275:AAHnsMio4Vgy496451eoYk8BWVVVwAz3FzQ";
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Map<Long, Chat> chats = new HashMap<>();

    public static Map<Long,Chat> getChats(){
        return chats;
    }

    public static void printChatIds() {
        for(Chat chat : chats.values())
            System.out.println(chat.getChat_id());
    }

    public Api() {
    }

    public static void getUpdates() throws URISyntaxException {
        try {
            CloseableHttpResponse response = createPostRequest("/getUpdates");
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
            for (Object obj : new JSONArray(jsonObject.get("result").toString())) {
                JSONObject result = new JSONObject(obj.toString());
                if(result.has("message")) {
                    long chatId = Long.parseLong(new JSONObject(new JSONObject(result.get("message").toString()).get("chat").toString()).get("id").toString());
                    String type = new JSONObject(new JSONObject(result.get("message").toString()).get("chat").toString()).get("type").toString();
                    chats.put(chatId,new Chat(chatId,type));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getMessages() {

    }

    public static void sendMessage(long chat_id, String message) throws URISyntaxException {
        if(!chats.containsKey(chat_id)) {
            System.out.println("Error: not valid chat_id");
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("chat_id", String.valueOf(chat_id));
        params.put("text",message);
        try {
            System.out.println(EntityUtils.toString(createPostRequest("/sendMessage",params).getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static CloseableHttpResponse createPostRequest(String method, Map<String, String> params) throws IOException, URISyntaxException {
        //generating uri
        URI uri = new URI(url + token + method); //TODO uri builder
        //initiation post request
        HttpPost httpPost = new HttpPost(uri);
        //Adding addition parameters
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : params.entrySet())
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        //executing and return response
        return httpClient.execute(httpPost);
    }

    static CloseableHttpResponse createPostRequest(String method) throws IOException, URISyntaxException {
        //generating uri
        URI uri = new URI(url + token + method); //TODO uri builder
        //initiation post request
        HttpPost httpPost = new HttpPost(uri);
        //executing and return response
        return httpClient.execute(httpPost);
    }

}