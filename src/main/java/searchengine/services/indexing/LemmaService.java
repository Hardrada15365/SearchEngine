package searchengine.services.indexing;

import searchengine.response.Response;

import java.io.IOException;

public interface LemmaService {
    Response indexPage(String urlPage) throws IOException;
}
