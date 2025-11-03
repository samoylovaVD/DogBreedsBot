package models;

public class DogBreed {
    private String name;
    private DogSize size;
    private ActivityLevel activityLevel;
    private boolean hypoallergenic;
    private boolean goodWithKids;
    private boolean suitableForApartment;
    private DogRole role;
    private int trainingDifficulty;
    private boolean goodWithOtherAnimals;
    private int groomingNeeds;
    private String description;

    public DogBreed(String name, DogSize size, ActivityLevel activityLevel, boolean hypoallergenic,
                    boolean goodWithKids, boolean suitableForApartment, DogRole role,
                    int trainingDifficulty, boolean goodWithOtherAnimals,
                    int groomingNeeds, String description) {
        this.name = name;
        this.size = size;
        this.activityLevel = activityLevel;
        this.hypoallergenic = hypoallergenic;
        this.goodWithKids = goodWithKids;
        this.suitableForApartment = suitableForApartment;
        this.role = role;
        this.trainingDifficulty = trainingDifficulty;
        this.goodWithOtherAnimals = goodWithOtherAnimals;
        this.groomingNeeds = groomingNeeds;
        this.description = description;
    }

    public String getName() { return name; }
    public DogSize getSize() { return size; }
    public ActivityLevel getActivityLevel() { return activityLevel; }
    public boolean isHypoallergenic() { return hypoallergenic; }
    public boolean isGoodWithKids() { return goodWithKids; }
    public boolean isSuitableForApartment() { return suitableForApartment; }
    public DogRole getRole() { return role; }
    public int getTrainingDifficulty() { return trainingDifficulty; }
    public boolean isGoodWithOtherAnimals() { return goodWithOtherAnimals; }
    public int getGroomingNeeds() { return groomingNeeds; }
    public String getDescription() { return description; }
}