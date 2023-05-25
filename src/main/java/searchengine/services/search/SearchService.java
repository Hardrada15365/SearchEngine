package searchengine.services.search;

import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.response.Response;

import java.io.IOException;

public interface SearchService {

    Response search(String userQuery) throws IOException;

}
