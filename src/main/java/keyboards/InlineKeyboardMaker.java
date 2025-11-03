package keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMaker {

    public static InlineKeyboardMarkup getStartTestKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton startButton = new InlineKeyboardButton();
        startButton.setText("🐕 Начать тест");
        startButton.setCallbackData("start_test");
        row.add(startButton);

        keyboard.add(row);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup getResultsKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton resultsButton = new InlineKeyboardButton();
        resultsButton.setText("📊 Посмотреть результаты");
        resultsButton.setCallbackData("show_results");
        row.add(resultsButton);

        keyboard.add(row);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup getHelpKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton startButton = new InlineKeyboardButton();
        startButton.setText("🚀 Старт");
        startButton.setCallbackData("command_start");
        row1.add(startButton);

        InlineKeyboardButton helpButton = new InlineKeyboardButton();
        helpButton.setText("❓ Помощь");
        helpButton.setCallbackData("command_help");
        row1.add(helpButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton resultsButton = new InlineKeyboardButton();
        resultsButton.setText("📈 Результаты");
        resultsButton.setCallbackData("command_results");
        row2.add(resultsButton);

        InlineKeyboardButton stopButton = new InlineKeyboardButton();
        stopButton.setText("🛑 Стоп");
        stopButton.setCallbackData("command_stop");
        row2.add(stopButton);

        keyboard.add(row1);
        keyboard.add(row2);
        markup.setKeyboard(keyboard);
        return markup;
    }
}