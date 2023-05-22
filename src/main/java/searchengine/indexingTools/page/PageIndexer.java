package searchengine.indexingTools.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.dto.indexing.PageDto;

import java.util.*;
import java.util.concurrent.RecursiveTask;

@Slf4j
@RequiredArgsConstructor
public class PageIndexer extends RecursiveTask<List<PageDto>> {
    private final List<String> urlList;
    private final String url;
    private final List<PageDto> pages;

    private static volatile boolean flag = true;
    @Override
    protected List<PageDto> compute() {
        try {

            Document document = null;
            try {
                Thread.sleep(200);
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();


            } catch (Exception e) {
                e.getMessage();
            }
            String content = document.outerHtml();
            Connection.Response response = document.connection().response();
            int code = response.statusCode();

            PageDto pageDto = new PageDto(url, code, content);
            pages.add(pageDto);
            Elements elements = document.select("body").select("a");
            List<PageIndexer> taskList = new ArrayList<>();
            String link;
            PageIndexer pageIndexer;
            for (Element element : elements) {
                link = element.attr("abs:href");
                if (getFlag()
                        && isWrongElements(link)
                        && link.startsWith(element.baseUri())
                        && !link.equals(element.baseUri())
                        && !link.contains("#")
                        && !link.contains("?")
                        && !urlList.contains(link)) {
                    urlList.add(link);
                    pageIndexer = new PageIndexer(urlList, link, pages);
                    pageIndexer.fork();
                    taskList.add(pageIndexer);

                }
            }
            for (PageIndexer task : taskList) {
                task.join();
                System.out.println(task.url);

            }
        } catch (Exception e) {
            PageDto pageDto = new PageDto(url, 500, "500");
            pages.add(pageDto);
        }
        return pages;
    }

    private boolean isWrongElements(String link) {
        List<String> WRONG_LINKS = Arrays.asList(
                "gif", "jpeg", "jpg", "pdf", "png", "ppt", "pptx", "svg", "svg", "tar", "zip", "mp4", "mp3");
        return !WRONG_LINKS.contains(link.substring(link.lastIndexOf(".") + 1));
    }


    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }



}
