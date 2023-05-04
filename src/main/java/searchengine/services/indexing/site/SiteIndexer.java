package searchengine.services.indexing.site;

import lombok.RequiredArgsConstructor;
import searchengine.dto.indexing.PageDto;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.indexing.page.PageIndexer;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public class SiteIndexer implements Runnable {
    private final searchengine.config.Site csite;
    private  final SiteRepository siteRepository;
    private  final PageRepository pageRepository;

    private PageIndexer pageIndexer;
    private Site site = new Site();
    @Override
    public void run(){


        site.setName(csite.getName());
        site.setStatus(Status.INDEXING);
        site.setStatusTime(new Date());
        site.setUrl(csite.getUrl());
        siteRepository.save(site);

        pageIndexer = new PageIndexer(new ArrayList<>(),site.getUrl(),new ArrayList<>());
        List<PageDto> pages = new ForkJoinPool().invoke(pageIndexer);

        List<Page> pageList = new ArrayList<>();

        if (pageIndexer.getFlag()){
        site.setStatus(Status.INDEXED);
        site.setStatusTime(new Date());
        site.setLastError("Ошибок нет");
        siteRepository.save(site);

        for (PageDto pageDto:pages){
            pageList.add(new Page(site,pageDto.path(),pageDto.code(),pageDto.content()));
        }
        pageRepository.saveAll(pageList);
        }else if(!pageIndexer.getFlag()){
            site.setStatus(Status.FAILED);
            site.setStatusTime(new Date());
            site.setLastError("Остановлено пользователем");
            siteRepository.save(site);

            for (PageDto pageDto:pages){
                pageList.add(new Page(site,pageDto.path(),pageDto.code(),pageDto.content()));

        }}

    }

    public void clearRepositories(){
        siteRepository.deleteAll();
        pageRepository.deleteAll();
    }

    public void stopIndexing(){
        pageIndexer.setFlag(false);
    }

}



