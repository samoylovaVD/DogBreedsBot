package models;

public class UserProfile {
    private int activityPreference;
    private boolean hasAllergy;
    private boolean hasChildren;
    private DogSize preferredDogSize;
    private DogRole preferredDogRole;
    private String livingSpace;
    private boolean willingToTrain;
    private boolean needGoodWithAnimals;
    private int groomingFrequency;

    public UserProfile() {
        reset();
    }

    public void reset() {
        this.activityPreference = 0;
        this.hasAllergy = false;
        this.hasChildren = false;
        this.preferredDogSize = null;
        this.preferredDogRole = null;
        this.livingSpace = "";
        this.willingToTrain = false;
        this.needGoodWithAnimals = false;
        this.groomingFrequency = 0;
    }

    public boolean isComplete() {
        return activityPreference != 0 &&
                livingSpace != null && !livingSpace.isEmpty() &&
                preferredDogRole != null &&
                preferredDogSize != null &&
                groomingFrequency != 0;
    }

    public int getActivityPreference() { return activityPreference; }
    public void setActivityPreference(int activityPreference) {
        this.activityPreference = activityPreference;
    }

    public boolean hasAllergy() { return hasAllergy; }
    public void setHasAllergy(boolean hasAllergy) {
        this.hasAllergy = hasAllergy;
    }

    public boolean hasChildren() { return hasChildren; }
    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public DogSize getPreferredDogSize() { return preferredDogSize; }
    public void setPreferredDogSize(DogSize preferredDogSize) {
        this.preferredDogSize = preferredDogSize;
    }

    public DogRole getPreferredDogRole() { return preferredDogRole; }
    public void setPreferredDogRole(DogRole preferredDogRole) {
        this.preferredDogRole = preferredDogRole;
    }

    public String getLivingSpace() { return livingSpace; }
    public void setLivingSpace(String livingSpace) {
        this.livingSpace = livingSpace;
    }

    public boolean isWillingToTrain() { return willingToTrain; }
    public void setWillingToTrain(boolean willingToTrain) {
        this.willingToTrain = willingToTrain;
    }

    public boolean needGoodWithAnimals() { return needGoodWithAnimals; }
    public void setNeedGoodWithAnimals(boolean needGoodWithAnimals) {
        this.needGoodWithAnimals = needGoodWithAnimals;
    }

    public int getGroomingFrequency() { return groomingFrequency; }
    public void setGroomingFrequency(int groomingFrequency) {
        this.groomingFrequency = groomingFrequency;
    }
}