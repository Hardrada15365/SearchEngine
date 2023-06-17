package searchengine.searchTool;


import lombok.RequiredArgsConstructor;
import searchengine.config.SitesList;
import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.io.IOException;
import java.security.KeyStore;
import java.util.*;

@RequiredArgsConstructor
public class Search {

    private final double redLine = 10;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;

    private final SitesList sitesList;

    private final HashMap<String, Double> stringDoubleHashMap = new HashMap<>();

    private final String query;
    private Set<String> queryLemmaList;

    private Lemmatizator lemmatizator;


    public HashMap<String, Double> filterLemmasByFrequency(Site site) throws IOException {

        lemmatizator = new Lemmatizator(query);
        queryLemmaList = lemmatizator.getLemmas().keySet();
        Lemma lemma;
        int pageCountBySiteId = pageRepository.pageCountBySiteId(site.getId());
        double percent;
        for (String searchLemma : queryLemmaList) {
            lemma = lemmaRepository.findByLemmaAndSiteId(searchLemma, site);
            percent = lemma.getFrequency() / pageCountBySiteId;
            if (percent < redLine) {
                stringDoubleHashMap.put(searchLemma, percent);
            }
        }
        return stringDoubleHashMap;
    }

    public HashMap<String, Double> filterLemmasByFrequency() throws IOException {
        lemmatizator = new Lemmatizator(query);
        queryLemmaList = lemmatizator.getLemmas().keySet();
        int pageCountBySiteId = pageRepository.pageCount();
        double percent;
        Integer frequencyOfAllSites;
        for (String searchLemma : queryLemmaList) {
            frequencyOfAllSites = lemmaRepository.frequencyOnAllSites(searchLemma);
            if (frequencyOfAllSites == null) {
                frequencyOfAllSites = 0;
            }
            percent = frequencyOfAllSites * 100 / pageCountBySiteId;
            if (percent < redLine) {
                stringDoubleHashMap.put(searchLemma, percent);
            }
        }
        return stringDoubleHashMap;
    }

    public Map<String, Double> sortByPercent() {
        List<Map.Entry<String, Double>> list = new ArrayList<>(stringDoubleHashMap.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }

    public List<Page> findPagesByLemma() {
        List<Page> basePages;
        List<Page> pages;
        Page page;
        List<String> lemmasSet = new ArrayList<>(sortByPercent().keySet());
        String lemma  = lemmasSet.get(0);
        lemmasSet.remove(lemma);

        List<Integer> listPageId = pageRepository.pagesIdByLemma(lemma);
        List<Integer> otherPagesId = new ArrayList<>();

        for (String lemma1:lemmasSet){
            listPageId = pageRepository.pagesIdByLemma(lemma1);
            otherPagesId.addAll(listPageId);
        }

        basePages = getPagesById(listPageId);
        pages = getPagesById(otherPagesId);

        pages.removeAll(basePages);
        return pages;
    }

    public void relevanceCalculation(){
        List<Page> pages = findPagesByLemma();
        HashMap<Page,Double> pageWithAbsRel = new HashMap<>();
        HashMap<Page,Double> pageWithRel = new HashMap<>();
        double sumRank = 0;
        double maxRank = 0;

        for (Page page : pages){
            sumRank = indexRepository.sumRank(page.getId());
            pageWithAbsRel.put(page,sumRank);
            if (maxRank < sumRank){
                maxRank = sumRank;
            }
        }

        for (Map.Entry<Page, Double> entry : pageWithAbsRel.entrySet()) {
            pageWithRel.put(entry.getKey(),entry.getValue()/maxRank);


        }

        for (Map.Entry<Page, Double> entry : pageWithAbsRel.entrySet()){
            System.out.println(entry.getKey().getPath() + " " + entry.getValue());
        }

    }

    public List<Page> getPagesById(List<Integer> listId){

        List<Page> pages = new ArrayList<>();
        Page page;
        for (int pageId:listId){
            page = pageRepository.findById(pageId);
            pages.add(page);
        }

        return pages;
    }
}
