package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public abstract class SiteParser {
    public static Set<String> getLinks (String page) throws IOException, InterruptedException {
        Document document = Jsoup.connect(page).ignoreHttpErrors(true).get();
        Elements links = document.select("a[href]");
        String stringLinks = links.toString();
        String[] stringsLinks = stringLinks.split("\n");
        Set<String> pageList = new TreeSet<>();

        for (String stringsLink : stringsLinks) {
            if (stringsLink.contains("href")
                    && (!stringsLink.contains("#"))
                    && (!stringsLink.contains("+"))
                    && (!stringsLink.contains(".pdf"))
                    && (!stringsLink.contains(".jpg"))
                    && (!stringsLink.contains(".mp3"))
                    && (!stringsLink.contains("?"))
                    && (!stringsLink.contains(".png"))
                    && (!stringsLink.contains(":"))
                    && (!stringsLink.contains("@"))) {
                int start = stringsLink.indexOf("href=") + "href=".length();
                int ofSet = stringsLink.indexOf("/");
                int end = stringsLink.indexOf("\"", ofSet);


                try {
                    String subString = stringsLink.substring(start, end).replace("\"", "");
                    int end2 = page.indexOf(".ru") + 3;

                    String subpage = page.substring(0, end2);
                    pageList.add(subpage + subString);

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage() + "-----------------------------");
                }


            }
        }
        return pageList;
    }


}
