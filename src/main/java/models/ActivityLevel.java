package models;

public enum ActivityLevel {
    VERY_LOW(1, "Очень низкая"),
    LOW(2, "Низкая"),
    MEDIUM(3, "Средняя"),
    HIGH(4, "Высокая"),
    VERY_HIGH(5, "Очень высокая");

    private final int level;
    private final String russianName;

    ActivityLevel(int level, String russianName) {
        this.level = level;
        this.russianName = russianName;
    }

    public int getLevel() {
        return level;
    }

    public String getRussianName() {
        return russianName;
    }

    public static ActivityLevel fromLevel(int level) {
        for (ActivityLevel activity : values()) {
            if (activity.level == level) {
                return activity;
            }
        }
        throw new IllegalArgumentException("Неизвестный уровень активности: " + level);
    }
}