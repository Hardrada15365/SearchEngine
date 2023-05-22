package searchengine.response;

import lombok.Data;

@Data
public class ErrorResponse extends Response {
    private boolean result;
    private String error;

    public ErrorResponse(String error){
        this.error = error;
        result = false;}
}
