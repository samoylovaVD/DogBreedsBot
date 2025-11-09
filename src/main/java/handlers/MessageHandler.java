package handlers;

import services.BreedService;
import services.QuestionService;
import services.UserSessionService;
import services.MessageHelper;
import keyboards.ReplyKeyboardMaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import keyboards.InlineKeyboardMaker;
import models.DogBreed;


public class MessageHandler {
    private final BreedService breedService;
    private final QuestionService questionService;
    private final UserSessionService sessionService;

    public MessageHandler(BreedService breedService, QuestionService questionService,
                          UserSessionService sessionService) {
        this.breedService = breedService;
        this.questionService = questionService;
        this.sessionService = sessionService;
    }

    public SendMessage handleMessage(Message message) {
        Long userId = message.getFrom().getId();
        String messageText = message.getText();

        if (messageText.startsWith("/")) {
            return handleCommand(userId, messageText);
        }

        if (sessionService.isTestActive(userId)) {
            return questionService.processAnswer(userId, messageText);
        }

        return createHelpMessage(userId);
    }

    private SendMessage handleCommand(Long userId, String command) {
        switch (command) {
            case "/start":
            case "/start@YourDogBot":
                return questionService.startTest(userId);

            case "/help":
            case "/help@YourDogBot":
                return createHelpMessage(userId);

            case "/stop":
            case "/stop@YourDogBot":
                return questionService.stopTest(userId);

            case "/result":
            case "/result@YourDogBot":
                return showResults(userId);

            default:
                SendMessage message = new SendMessage();
                message.setChatId(userId.toString());
                message.setText("Неизвестная команда. Используйте /help для списка команд.");
                message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                return message;
        }
    }

    private SendMessage showResults(Long userId) {
        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setParseMode("Markdown");

        if (!sessionService.getOrCreateUserProfile(userId).isComplete()) {
            String errorText = String.format(
                    "%s%s",
                    MessageHelper.formatError("Вы еще не прошли тест!"),
                    "\n\nИспользуйте /start чтобы начать тест по подбору породы собаки."
            );

            message.setText(errorText);
            message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
            return message;
        }

        java.util.List<DogBreed> matchingBreeds = breedService.findMatchingBreeds(
            sessionService.getOrCreateUserProfile(userId)
        );

        
        String results = breedService.formatResultsForTelegram(matchingBreeds);
        message.setText(results);

        
        message.setReplyMarkup(InlineKeyboardMaker.breedsBoard(matchingBreeds));
        return message;
    }

    private SendMessage createHelpMessage(Long userId) {
        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setParseMode("Markdown");

        String helpText = String.format(
                "%s%s%s%s%s%s%s",
                MessageHelper.formatTitle("Бот для подбора пород собак"),
                MessageHelper.formatSubtitle("Доступные команды:"),
                "*/start* - начать тест по подбору породы\n",
                "*/stop* - прервать текущий тест\n",
                "*/result* - показать подходящие породы\n",
                "*/help* - показать эту справку\n\n",
                MessageHelper.formatInfo("Просто следуйте инструкциям и отвечайте на вопросы!")
        );

        message.setText(helpText);
        message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
        return message;
    }
}
