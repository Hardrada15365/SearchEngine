package searchengine.indexingTools.site;

import lombok.RequiredArgsConstructor;
import searchengine.dto.indexing.PageDto;
import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.model.*;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.indexingTools.page.PageIndexer;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public class SiteIndexer implements Runnable {
    private final searchengine.config.Site csite;
    private  final SiteRepository siteRepository;
    private  final PageRepository pageRepository;

    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;

    private PageIndexer pageIndexer;
    private Site site = new Site();
    private static boolean isIndexingRun;

    @Override
    public void run(){
        isIndexingRun = true;
        site.setName(csite.getName());
        site.setStatus(Status.INDEXING);
        site.setStatusTime(new Date());
        site.setUrl(csite.getUrl());
        siteRepository.save(site);

        pageIndexer = new PageIndexer(new ArrayList<>(),site.getUrl(),new ArrayList<>());
        List<PageDto> pages = new ForkJoinPool().invoke(pageIndexer);

        List<Page> pageList = new ArrayList<>();

        Lemmatizator lemmatizator;
        Page page;
        if (pageIndexer.getFlag()){
        site.setStatus(Status.INDEXED);
        site.setStatusTime(new Date());
        site.setLastError("Ошибок нет");
        siteRepository.save(site);


        for (PageDto pageDto:pages){
            page = new Page(site,pageDto.path(),pageDto.code(),pageDto.content());
            pageList.add(page);
            try {
                indexPage(page.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        pageRepository.saveAll(pageList);
        }else if(!pageIndexer.getFlag()){
            site.setStatus(Status.FAILED);
            site.setStatusTime(new Date());
            site.setLastError("Остановлено пользователем");
            siteRepository.save(site);

            for (PageDto pageDto:pages){
                page = new Page(site,pageDto.path(),pageDto.code(),pageDto.content());
                pageList.add(page);
                indexPage(page.getPath());
        }
        }
        isIndexingRun = false;
    }

    public void stopIndexing(){
        pageIndexer.setFlag(false);
    }

    public boolean isIndexingRun() {
        return isIndexingRun;
    }

    public void indexPage(String urlPage) throws IOException {
        Lemmatizator lemmatizator = new Lemmatizator(urlPage);

        if (pageRepository.existsByPath(urlPage)) {
            Page oldPage = pageRepository.findByPath(urlPage);
            searchengine.model.Site site = siteRepository.findById(oldPage.getSiteId().getId());
            pageRepository.delete(oldPage);

            Page page = new Page(site, urlPage, lemmatizator.getCode(), lemmatizator.getContent());
            pageRepository.save(page);

            HashMap<String, Integer> hashLemmas = lemmatizator.getLemmas();

            Lemma lemma;
            Lemma oldLemma;
            Index index;
            for (String key : hashLemmas.keySet()) {
                int rank = hashLemmas.get(key);

                if (lemmaRepository.existsByLemmaAndSiteId(key,site)){
                    oldLemma = lemmaRepository.findByLemmaAndSiteId(key,site);
                    int frequency = oldLemma.getFrequency();

                    lemma = new Lemma(key,site,frequency + 1);
                    index = new Index(page,rank,lemma);

                    lemmaRepository.delete(oldLemma);

                }else {
                lemma = new Lemma(key,site,1);
                index = new Index(page,rank,lemma);

                }
                lemmaRepository.save(lemma);
                indexRepository.save(index);

            }
    }
    }
}



