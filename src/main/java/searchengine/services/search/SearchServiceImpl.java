package searchengine.services.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.response.Response;
import searchengine.response.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Override
    public Response search(String userQuery) throws IOException {

        Lemmatizator lemmatizator = new Lemmatizator(userQuery);

        List<String> list = new ArrayList<>(lemmatizator.getLemmas().keySet());

        System.out.println(list);

        return new SearchResponse(list);
    }
}
