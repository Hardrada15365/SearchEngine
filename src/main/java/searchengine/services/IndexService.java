package searchengine.services;

import searchengine.response.Response;

import java.io.IOException;

public interface IndexService {

    Response startIndexing() throws Exception;

    Response stopIndexing();

    Response indexPage(String urlPage) throws IOException;




}
