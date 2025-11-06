package models;

public enum DogRole {
    COMPANION("Друг"),
    GUARD("Охранник");

    private final String russianName;

    DogRole(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}