package searchengine.services.indexing.lemma;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class Lemmatizator {

    private String url;
    private final LuceneMorphology luceneMorph = new RussianLuceneMorphology();


    private static final String [] functional = new String[]{"СОЮЗ","МЕЖД", "ПРЕДЛ","ПРЕДК","Н"};

    private static final String notRussianWords = "[^а-яА-Я ]";
    public Lemmatizator(String url) throws IOException {
        this.url = url;
    }
    public String [] russianWords() throws IOException {
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com").get();

        String text = document.text();
        text = text.replaceAll(notRussianWords,"")
                   .replaceAll("\\s+", " ")
                   .toLowerCase();
        return  text.split(" ");
    }
    public boolean isWordFunctional(String word) throws IOException {

        for (String functional:functional){
            if (word.contains(functional)){
                return true;
            }
        }
        return false;
    }
    public List<String> getNoFunctionalWords() throws IOException {
       List<String> noFunctionalWords = new ArrayList<>();
        for (String word : russianWords()){
            if (!isWordFunctional(word)){
                noFunctionalWords.add(word);
            }
        }
        return  noFunctionalWords;
    }

    public List<String> getNormalForms() throws IOException {
        List<String> normalForms = new ArrayList<>();
        List <String> wordBaseForms = new ArrayList<>();
        for (String word:getNoFunctionalWords()){
            wordBaseForms = luceneMorph.getNormalForms(word);
            normalForms.addAll(wordBaseForms);
        }
        return normalForms;
    }

    public HashMap<String,Integer> getLemmas() throws IOException {
        HashMap<String,Integer> lemmas = new HashMap<>();
        int count = 1;
        for (String word:getNormalForms()){
            if (lemmas.containsKey(word)){
                count++;
                lemmas.put(word,count);

            }else {
                lemmas.put(word,count);
            }
        }
        return lemmas;
    }







}
