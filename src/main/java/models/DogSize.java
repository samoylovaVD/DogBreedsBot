package models;

public enum DogSize {
    SMALL("Маленькая"),
    MEDIUM("Средняя"),
    LARGE("Большая");

    private final String russianName;

    DogSize(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}