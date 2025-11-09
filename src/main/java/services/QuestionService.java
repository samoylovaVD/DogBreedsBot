package services;

import models.UserProfile;
import models.DogSize;
import models.DogRole;
import keyboards.ReplyKeyboardMaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class QuestionService {
    private final BreedService breedService;
    private final UserSessionService sessionService;

    public QuestionService(BreedService breedService, UserSessionService sessionService) {
        this.breedService = breedService;
        this.sessionService = sessionService;
    }

    // УДАЛЯЕМ старый метод startQuestionnaire()
    // public void startQuestionnaire() { ... }

    public SendMessage processAnswer(Long userId, String messageText) {
        UserProfile userProfile = sessionService.getOrCreateUserProfile(userId);
        int currentQuestion = sessionService.getCurrentQuestion(userId);

        if (currentQuestion > 0) {
            boolean success = processUserAnswer(userId, messageText, currentQuestion);
            if (!success) {
                return createQuestion(userId, currentQuestion, "Пожалуйста, выберите вариант из предложенных:");
            }
        }

        int nextQuestion = currentQuestion + 1;
        sessionService.setCurrentQuestion(userId, nextQuestion);

        return createQuestion(userId, nextQuestion, null);
    }

    private boolean processUserAnswer(Long userId, String messageText, int questionNumber) {
        UserProfile userProfile = sessionService.getOrCreateUserProfile(userId);

        try {
            switch (questionNumber) {
                case 1:
                    if (messageText.equals("Активный образ жизни")) {
                        userProfile.setActivityPreference(1);
                    } else if (messageText.equals("Домашний образ жизни")) {
                        userProfile.setActivityPreference(2);
                    } else {
                        return false;
                    }
                    break;

                case 2:
                    if (messageText.equals("Да")) {
                        userProfile.setHasAllergy(true);
                    } else if (messageText.equals("Нет")) {
                        userProfile.setHasAllergy(false);
                    } else {
                        return false;
                    }
                    break;

                case 3:
                    if (messageText.equals("Да")) {
                        userProfile.setHasChildren(true);
                    } else if (messageText.equals("Нет")) {
                        userProfile.setHasChildren(false);
                    } else {
                        return false;
                    }
                    break;

                case 4:
                    if (messageText.equals("Квартира")) {
                        userProfile.setLivingSpace("apartment");
                    } else if (messageText.equals("Дом с участком")) {
                        userProfile.setLivingSpace("house");
                    } else {
                        return false;
                    }
                    break;

                case 5:
                    if (messageText.equals("Друг")) {
                        userProfile.setPreferredDogRole(DogRole.COMPANION);
                    } else if (messageText.equals("Охранник")) {
                        userProfile.setPreferredDogRole(DogRole.GUARD);
                    } else {
                        return false;
                    }
                    break;

                case 6:
                    if (messageText.equals("Маленькая")) {
                        userProfile.setPreferredDogSize(DogSize.SMALL);
                    } else if (messageText.equals("Средняя")) {
                        userProfile.setPreferredDogSize(DogSize.MEDIUM);
                    } else if (messageText.equals("Большая")) {
                        userProfile.setPreferredDogSize(DogSize.LARGE);
                    } else {
                        return false;
                    }
                    break;

                case 7:
                    if (messageText.equals("Да")) {
                        userProfile.setWillingToTrain(true);
                    } else if (messageText.equals("Нет")) {
                        userProfile.setWillingToTrain(false);
                    } else {
                        return false;
                    }
                    break;

                case 8:
                    if (messageText.equals("Да")) {
                        userProfile.setNeedGoodWithAnimals(true);
                    } else if (messageText.equals("Нет")) {
                        userProfile.setNeedGoodWithAnimals(false);
                    } else {
                        return false;
                    }
                    break;

                case 9:
                    if (messageText.equals("Редко")) {
                        userProfile.setGroomingFrequency(1);
                    } else if (messageText.equals("Иногда")) {
                        userProfile.setGroomingFrequency(2);
                    } else if (messageText.equals("Часто")) {
                        userProfile.setGroomingFrequency(3);
                    } else {
                        return false;
                    }
                    break;

                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SendMessage createQuestion(Long userId, int questionNumber, String errorMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setParseMode("Markdown");

        String questionText = getQuestionText(questionNumber);
        if (errorMessage != null) {
            questionText = String.format("%s\n\n%s", errorMessage, questionText);
        }
        message.setText(questionText);

        ReplyKeyboard keyboard = (ReplyKeyboard) getKeyboardForQuestion(questionNumber);
        message.setReplyMarkup(keyboard);

        return message;
    }

    private String getQuestionText(int questionNumber) {
        switch (questionNumber) {
            case 1:
                return String.format(
                        "%s%s%s%s",
                        MessageHelper.formatTitle("Подбор идеальной породы собаки"),
                        MessageHelper.formatQuestion(1, "Что вам ближе?"),
                        "\n",
                        String.format("%s\n%s",
                                MessageHelper.formatOption("Активный образ жизни - любите много гулять"),
                                MessageHelper.formatOption("Домашний образ жизни - предпочитаете домашний уют")
                        )
                );

            case 2:
                return MessageHelper.formatQuestion(2, "У вас есть аллергия на шерсть?");

            case 3:
                return MessageHelper.formatQuestion(3, "Есть ли у вас дети?");

            case 4:
                return MessageHelper.formatQuestion(4, "Вы живёте в?");

            case 5:
                return MessageHelper.formatQuestion(5, "Вам ближе:");

            case 6:
                return MessageHelper.formatQuestion(6, "Какую собаку вы бы хотели?");

            case 7:
                return MessageHelper.formatQuestion(7, "Готовы ли вы уделять время дрессировке?");

            case 8:
                return MessageHelper.formatQuestion(8, "Ваша собака должна ладить с другими животными?");

            case 9:
                return String.format(
                        "%s%s%s%s%s",
                        MessageHelper.formatQuestion(9, "Насколько часто вы готовы ухаживать за шерстью вашего питомца?"),
                        "\n",
                        MessageHelper.formatOption("Редко (1-2 раза в месяц)"),
                        "\n",
                        String.format("%s\n%s",
                                MessageHelper.formatOption("Иногда (1 раз в неделю)"),
                                MessageHelper.formatOption("Часто (ежедневно или через день)")
                        )
                );

            case 10:
                return String.format(
                        "%s%s",
                        MessageHelper.formatSuccess("Тест завершен!"),
                        "\n\nВсе ответы сохранены. Теперь вы можете посмотреть подходящие породы с помощью команды /result"
                );

            default:
                return "Тест завершен";
        }
    }

    private Object getKeyboardForQuestion(int questionNumber) {
        switch (questionNumber) {
            case 1:
                return ReplyKeyboardMaker.getActivityKeyboard();
            case 2:
            case 3:
            case 7:
            case 8:
                return ReplyKeyboardMaker.getYesNoKeyboard();
            case 4:
                return ReplyKeyboardMaker.getLivingSpaceKeyboard();
            case 5:
                return ReplyKeyboardMaker.getRoleKeyboard();
            case 6:
                return ReplyKeyboardMaker.getSizeKeyboard();
            case 9:
                return ReplyKeyboardMaker.getGroomingKeyboard();
            case 10:
                return ReplyKeyboardMaker.getMainMenuKeyboard();
            default:
                return ReplyKeyboardMaker.removeKeyboard();
        }
    }

    public SendMessage startTest(Long userId) {
        sessionService.resetUserSession(userId);
        sessionService.setCurrentQuestion(userId, 1);
        return createQuestion(userId, 1, null);
    }

    public SendMessage stopTest(Long userId) {
        sessionService.resetUserSession(userId);

        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Тест прерван. Все ваши ответы удалены.\n\n" +
                "Чтобы начать заново, используйте /start");
        message.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());

        return message;
    }
}