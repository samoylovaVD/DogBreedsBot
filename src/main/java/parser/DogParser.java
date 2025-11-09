package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import java.io.IOException;
import models.DogBreed;

public class DogParser{
  private static final String base_url = "https://www.purina.ru";

public DogBreed parseDog(String url){
  try{
    Document doc = Jsoup.connect(url).get();
      models.DogBreed breed = new models.DogBreed();

      Element nameDog = doc.selectFirst("#block-ttt-mainpagecontent > div > div.breed-details > div.component-wrapper > div > div > div > div > div.hero-small--content-area-wrapper > div.hero-small--content-area.hero-small--breed.hero-small--breed__dog > div > h1");
    if (nameDog != null){
      String name = nameDog.text();
        breed.setName(name);
    }else{
      System.out.println("Не удалось найти название породы((");}

  
    Element descriptionDog = doc.selectFirst("#block-ttt-mainpagecontent > div > div.breed-details > div.component-wrapper > div > div > div > div > div.hero-small--content-area-wrapper > div.hero-small--content-area.hero-small--breed.hero-small--breed__dog > div > div.hero-small--content-area-intro > div > p");
    if (descriptionDog != null){
      String description = descriptionDog.text();
        breed.setDescript(description);
    }else{
      System.out.println("Не удалось найти описание породы((");}


    Element picture = doc.selectFirst("#block-ttt-mainpagecontent > div > div.breed-details > div.component-wrapper > div > div > div > div > div.hero-small--image > div > picture > img");
    if (picture != null){
      breed.setPict(base_url + picture.attr("src"));
    }else{
      System.out.println("Не удалось найти картиночку породы((");}
    return breed;

  }catch(IOException e){
    e.printStackTrace();
    return null;
    }
  }
}
