package searchengine.dto.indexing;

import searchengine.model.Status;

import java.util.Date;

public record SiteDto(String name, Status status,Date statusTime, String lastError, String url) {
}
