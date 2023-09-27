package app;

import bot.api.Api;

import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException {

        Api.getUpdates();
        Api.printChatIds();
        Api.sendMessage(123456,"I am still alive");

    }
}
