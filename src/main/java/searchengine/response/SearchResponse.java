package searchengine.response;

import java.util.List;

public class SearchResponse extends Response {
    private boolean result ;
    private int count;
    private List<String> data;

    public SearchResponse(List<String> data){
        this.data = data;
        this.count = data.size();

    }


}
