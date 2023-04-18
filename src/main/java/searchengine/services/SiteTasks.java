package searchengine.services;

import searchengine.model.Site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class SiteTasks extends RecursiveTask<Set<String>> {
    private final Site site;
    private static Set<String> linkedSites = new HashSet<>();
    private  Set<String> stringSet = new HashSet<>();
    public SiteTasks(Site site) {
        this.site = site;
    }


    @Override
    protected Set<String> compute() {
        List<SiteTasks> taskList = new ArrayList<>();
        linkedSites.add(site.getUrl());
        try {
            Thread.sleep(550);
            for (Site child : site.getPages()) {
                if (!linkedSites.contains(child.getUrl())) {
                    SiteTasks task = new SiteTasks(child);
                    linkedSites.add(child.getUrl());
                    System.out.println(child.getUrl());
                    task.fork();
                    taskList.add(task);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (SiteTasks task : taskList) {
            task.join();
        }
        return linkedSites;
    }
}
