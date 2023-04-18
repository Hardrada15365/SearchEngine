package searchengine.services;

import searchengine.model.Site;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public  class PageJoiner extends Thread{
    private volatile Set<String> AllPages = new HashSet<>();
    private String url;

    public PageJoiner(String url){
        this.url = url;
    }
    @Override
    public  void run(){
        AllPages.addAll(new ForkJoinPool().invoke(new SiteTasks(new Site(url))));
    }

    public Set<String> getAllPages() {
        return AllPages;
    }
}
