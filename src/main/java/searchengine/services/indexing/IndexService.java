package searchengine.services.indexing;

import searchengine.response.Response;

public interface IndexService {

    Response startIndexing() throws Exception;

    Response stopIndexing();




}
