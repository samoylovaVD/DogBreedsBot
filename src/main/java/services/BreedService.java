package services;

import parser.DogBreedParser;
import models.DogBreed;
import models.UserProfile;
import models.DogSize;
import models.ActivityLevel;
import models.DogRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class BreedService {
    private List<DogBreed> dogBreeds;
    private Map<String, String> breedUrl;
    private DogBreedParser dogParser;

    public BreedService() {
        initializeBreeds();
        initializeBreedUrl();
        this.dogParser = new DogBreedParser();
    }

    public DogBreed detailDog(String name){
        String url = fullUrl(name);
        if (url != null){
            return dogParser.parseDog(url);}
        return null;}

    private void initializeBreedUrl(){
        breedUrl = new HashMap<>();

        breedUrl.put("Йоркширский терьер", "yorkshirskiy-terer");
        breedUrl.put("Чихуахуа", "gladkosherstnyy-chikhuakhua");
        breedUrl.put("Такса", "taksa-gladkosherstnaya");
        breedUrl.put("Бульдог", "bulldog");
        breedUrl.put("Бигль", "bigl");
        breedUrl.put("Лабрадор-ретривер", "labrador-retriver");
        breedUrl.put("Немецкая овчарка", "german-shepherd");
        breedUrl.put("Золотистый ретривер", "zolotistyy-retriver");
        breedUrl.put("Доберман", "dobermann");}

    public String getBreedUrl(String name){
        return breedUrl.get(name);}

    public String fullUrl(String name){
        String partUrl = breedUrl.get(name);
        if (partUrl != null){
            return "https://www.purina.ru/find-a-pet/dog-breeds/" + partUrl;}
        return null;}


    private void initializeBreeds() {
        dogBreeds = new ArrayList<>();

        dogBreeds.add(new DogBreed(
                "Йоркширский терьер", DogSize.SMALL, ActivityLevel.MEDIUM, true, true, true,
                DogRole.COMPANION, 3, true, 3, "Маленькая декоративная порода, подходит для квартиры", null
        ));

        dogBreeds.add(new DogBreed(
                "Чихуахуа", DogSize.SMALL, ActivityLevel.LOW, false, false, true,
                DogRole.COMPANION, 2, false, 1, "Самая маленькая порода собак, требует бережного отношения" , null
        ));

        dogBreeds.add(new DogBreed(
                "Такса", DogSize.SMALL, ActivityLevel.MEDIUM, false, true, true,
                DogRole.COMPANION, 3, true, 2, "Охотничья порода, активная и умная", null
        ));

        dogBreeds.add(new DogBreed(
                "Бульдог", DogSize.MEDIUM, ActivityLevel.LOW, false, true, true,
                DogRole.COMPANION, 3, true, 2, "Спокойная и дружелюбная порода, хороший компаньон", null
        ));

        dogBreeds.add(new DogBreed(
                "Бигль", DogSize.MEDIUM, ActivityLevel.HIGH, false, true, false,
                DogRole.COMPANION, 4, true, 2, "Активная охотничья порода, требует много движения", null
        ));

        dogBreeds.add(new DogBreed(
                "Лабрадор-ретривер", DogSize.LARGE, ActivityLevel.HIGH, false, true, false,
                DogRole.COMPANION, 2, true, 3, "Популярная семейная порода, очень дружелюбная и умная", null
        ));

        dogBreeds.add(new DogBreed(
                "Немецкая овчарка", DogSize.LARGE, ActivityLevel.HIGH, false, true, false,
                DogRole.GUARD, 2, true, 3, "Универсальная служебная порода, умная и преданная", null
        ));

        dogBreeds.add(new DogBreed(
                "Золотистый ретривер", DogSize.LARGE, ActivityLevel.HIGH, false, true, false,
                DogRole.COMPANION, 2, true, 3, "Очень дружелюбная и терпеливая порода, идеальна для семьи", null
        ));

        dogBreeds.add(new DogBreed(
                "Доберман", DogSize.LARGE, ActivityLevel.HIGH, false, true, true,
                DogRole.GUARD, 3, false, 1, "Элегантная и умная порода, хороший сторож", null
        ));
    }
    public List<DogBreed> findMatchingBreeds(UserProfile userProfile) {
        return dogBreeds.stream()
                .filter(breed -> isBreedSuitable(breed, userProfile))
                .collect(Collectors.toList());
    }

    private boolean isBreedSuitable(DogBreed breed, UserProfile userProfile) {
        if (breed.getSize() != userProfile.getPreferredDogSize()) {
            return false;
        }

        int userActivity = userProfile.getActivityPreference();
        int breedActivity = breed.getActivityLevel().getLevel();

        if (userActivity == 1 && breedActivity == 3) {
            return false;
        }
        if (userActivity == 3 && breedActivity == 1) {
            return false;
        }

        if (userProfile.hasAllergy() && !breed.isHypoallergenic()) {
            return false;
        }

        if (userProfile.hasChildren() && !breed.isGoodWithKids()) {
            return false;
        }

        if ("apartment".equals(userProfile.getLivingSpace()) && !breed.isSuitableForApartment()) {
            return false;
        }

        if (breed.getRole() != userProfile.getPreferredDogRole()) {
            return false;
        }

        if (!userProfile.isWillingToTrain() && breed.getTrainingDifficulty() > 3) {
            return false;
        }

        if (userProfile.needGoodWithAnimals() && !breed.isGoodWithOtherAnimals()) {
            return false;
        }

        int userGrooming = userProfile.getGroomingFrequency();
        if (userGrooming == 1 && breed.getGroomingNeeds() > 2) {
            return false;
        }
        if (userGrooming == 3 && breed.getGroomingNeeds() < 3) {
            return false;
        }

        return true;
    }

    public String formatResultsForTelegram(List<DogBreed> matchingBreeds) {
        if (matchingBreeds.isEmpty()) {
            return MessageHelper.formatError("По вашим критериям не найдено подходящих пород.") +
                    "\n\n" + MessageHelper.formatInfo("Попробуйте изменить некоторые параметры поиска и пройти тест заново.");
        }

        StringBuilder result = new StringBuilder();
        String title = MessageHelper.formatTitle("Результаты подбора пород");
        String countMessage = String.format("Найдено %d подходящих пород:\n\n", matchingBreeds.size());

        result.append(title).append(countMessage);

        for (int i = 0; i < matchingBreeds.size(); i++) {
            DogBreed breed = matchingBreeds.get(i);

            String breedInfo = String.format(
                    "%d. *%s*\n" +
                            "   📏 Размер: %s\n" +
                            "   🏃 Активность: %s\n" +
                            "   🎓 Дрессировка: %s\n" +
                            "   ✨ Уход: %s\n" +
                            "   📝 %s\n\n",
                    i + 1,
                    MessageHelper.escapeMarkdown(breed.getName()),
                    breed.getSize().getRussianName(),
                    breed.getActivityLevel().getRussianName(),
                    getTrainingDifficultyInRussian(breed.getTrainingDifficulty()),
                    getGroomingNeedsInRussian(breed.getGroomingNeeds()),
                    MessageHelper.escapeMarkdown(breed.getDescript())
            );

            result.append(breedInfo);
        }
        result.append("\n🎯 *Нажмите на кнопку ниже для получения подробной информации*");

        return result.toString();
    }


    private String getTrainingDifficultyInRussian(int difficulty) {
        switch (difficulty) {
            case 1: case 2: return "Легко обучаема";
            case 3: return "Средняя сложность";
            case 4: case 5: return "Требует опыта";
            default: return "Неизвестно";
        }
    }

    private String getGroomingNeedsInRussian(int needs) {
        switch (needs) {
            case 1: case 2: return "Минимальный";
            case 3: return "Умеренный";
            case 4: case 5: return "Интенсивный";
            default: return "Неизвестно";
        }
    }
}
