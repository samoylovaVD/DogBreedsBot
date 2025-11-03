import config.BotConfig;
import handlers.MessageHandler;
import handlers.CallbackHandler;
import services.BreedService;
import services.QuestionService;
import services.UserSessionService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main extends TelegramLongPollingBot {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public Main(String botToken) {
        super(botToken);
        BreedService breedService = new BreedService();
        UserSessionService sessionService = new UserSessionService();
        QuestionService questionService = new QuestionService(breedService, sessionService);
        this.messageHandler = new MessageHandler(breedService, questionService, sessionService);
        this.callbackHandler = new CallbackHandler(questionService, sessionService);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage response = messageHandler.handleMessage(update.getMessage());
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasCallbackQuery()) {
            SendMessage response = callbackHandler.handleCallback(update.getCallbackQuery());
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Main(BotConfig.BOT_TOKEN));
            System.out.println("🐕 Бот для подбора пород собак запущен!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}