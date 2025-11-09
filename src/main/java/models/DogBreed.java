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
    private String picture;


    public DogBreed(String name, DogSize size, ActivityLevel activityLevel, boolean hypoallergenic,
                    boolean goodWithKids, boolean suitableForApartment, DogRole role,
                    int trainingDifficulty, boolean goodWithOtherAnimals,
                    int groomingNeeds, String description, String picture) {
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
        this.picture= picture;
    }

    public DogBreed() {

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
    public String getDescript() { return description; }
    public String getPict() { return picture; }

    public void setName(String name) { this.name = name; }
    public void setDescript(String description) { this.description = description; }
    public void setPict(String picture) { this.picture= picture; }
}