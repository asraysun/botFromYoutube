import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        get("/", (req, res) -> "Hello heroku world");


        ApiContextInitializer.init();
        MyTestBot myTestBot = new MyTestBot();
        TelegramBotsApi botsApi = new TelegramBotsApi();


        try {
            botsApi.registerBot(myTestBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
