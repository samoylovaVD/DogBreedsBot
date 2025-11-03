package handlers;

import services.QuestionService;
import services.UserSessionService;
import keyboards.ReplyKeyboardMaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackHandler {
    private final QuestionService questionService;
    private final UserSessionService sessionService;

    public CallbackHandler(QuestionService questionService, UserSessionService sessionService) {
        this.questionService = questionService;
        this.sessionService = sessionService;
    }

    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        String callbackData = callbackQuery.getData();

        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setParseMode("Markdown");

        switch (callbackData) {
            case "start_test":
                return questionService.startTest(userId);

            case "show_results":
                message.setText("Для просмотра результатов используйте команду /result");
                message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                return message;

            case "command_start":
                return questionService.startTest(userId);

            case "command_help":
                message.setText("🐕 *Бот для подбора пород собак*\n\n" +
                        "Просто начните тест с помощью /start и отвечайте на вопросы!\n\n" +
                        "Бот задаст вам 9 вопросов о ваших предпочтениях и условиях содержания собаки.");
                message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                return message;

            case "command_results":
                message.setText("Чтобы посмотреть результаты, сначала пройдите тест с помощью /start");
                message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                return message;

            case "command_stop":
                return questionService.stopTest(userId);

            default:
                message.setText("Неизвестная команда");
                message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                return message;
        }
    }
}