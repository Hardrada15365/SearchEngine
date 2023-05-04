package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.repository.LemmaRepository;
import searchengine.response.Response;
import searchengine.services.indexing.lemma.Lemmatizator;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService{
    private final LemmaRepository lemmaRepository;
    @Override
    public Response indexPage( String urlPage) throws IOException {
        Lemmatizator lemmatizator = new Lemmatizator(urlPage);
        return null;
    }
}
