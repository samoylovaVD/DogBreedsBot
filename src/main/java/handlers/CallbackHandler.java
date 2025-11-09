package handlers;

import services.QuestionService;
import services.UserSessionService;
import keyboards.ReplyKeyboardMaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import services.BreedService;
import parser.DogBreed;




public class CallbackHandler {
    private final QuestionService questionService;
    private final UserSessionService sessionService;
    private final BreedService breedService;

    public CallbackHandler(QuestionService questionService, UserSessionService sessionService, BreedService breedService) {
        this.questionService = questionService;
        this.sessionService = sessionService;
        this.breedService = breedService;
    }

    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        String callbackData = callbackQuery.getData();

        if (callbackData.startsWith("breed_")){
            String name = callbackData.substring(6);
            return detailDog(userId, name);}

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
    private SendMessage detailDog(Long userId, String name) {
            SendMessage message = new SendMessage();
            message.setChatId(userId.toString());
            message.setParseMode("Markdown");
            
            try {
            DogBreed detailBreed = breedService.detailDog(name);
            
                if (detailBreed != null && detailBreed.getName() != null) {
                    String info = detInfo(detailBreed);
                    message.setText(info);
                }else{
                    message.setText("Не удалось загрузить информацию о породе " + name + ":(");
                }
            }catch(Exception e){
                message.setText("Произошла ошибка при загрузке информации о породе");
            }
              return message;
    }
    private String detInfo(DogBreed breed){
        StringBuilder sb = new StringBuilder();
        sb.append("^^ *").append(breed.getName()).append("*\n\n"); 

        if (breed.getDescript() != null){
            sb.append().append(breed.getDescript()).append("\n\n");
        }
        if (breed.getPict())!=null){
            sb.append("\n [Посмотреть фотки](").append(breed.getPict()).append(")");
        }
        return sb.toString();}
}
