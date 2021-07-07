import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RSSManager implements Runnable {

    String lastModified = "";
    String eTag = "";
    String URL = "";
    String xml;
    Boolean isFirstRequest = true;
    List<RSSItem> rssItems;
    List<String> SECLinks = new ArrayList<>();



    public List<RSSItem> getRssItems() {
        return rssItems;
    }
    static Map<JLabel, String> hyperlinks = new HashMap<>();

    public String getURL() {
        return URL;
    }

    public RSSManager(String url) throws IOException {
        this.URL = url;
        this.rssItems = new ArrayList<>();

    }

//    public List<JLabel> getMatchingLinks() {
//        int i = 1;
//        System.out.println("inside getMacthingLinks");
//        System.out.println(SECLinks.toString());
//        List<JLabel> jLabels = new ArrayList<>();
//        for (RSSItem rssItem : rssItems) {
//            try {
//                Document html = Jsoup.connect(link).get();
//                for (Element e : html.select("tr > td")) {
//                    if (e.text().contains("CURRENT REPORT")) {
//                        Element o = e.nextElementSibling().selectFirst("a");
//                        String url = "https://www.sec.gov" + o.attr("href");
//
//                        JLabel j = new JLabel();
//                        j.setText("Filing " + i++);
//                        jLabels.add(j);
//                        hyperlinks.put(j, url);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return jLabels;
//    }

    public void updateRSS() {
        //if it is not the first request and it is not

        try {
//            Connection.Response response = Jsoup.connect(URL).response();
//            Connection rep = Jsoup.connect(URL);
//            Jsoup.connect(URL).response().toString();
//            System.out.println("here is the response");
//            System.out.println(rep.text());
//            xml = rep.toString();
            xml = Jsoup.connect(URL).ignoreContentType(true).get().toString();
//            Connection.Response response = Jsoup
//                    .connect(URL)
//                    .ignoreContentType(true)
//                    .method(Connection.Method.GET)
//                    .followRedirects(false)
//                    .execute();
//                String json = Jsoup.connect(URL).ignoreContentType(true).execute().body();

//                System.out.println(json);
//            System.out.println("This was last modified: " + response.header("Last-Modified"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());

//        System.out.println("last_modified: " + lastModified);
//        System.out.println(URL);
        if (!lastModified.equals(doc.select("updated").first().text())) {
            //System.out.println("it was modified so we are getting posts");
            if (URL.contains("sec")) {
                //System.out.println("RSS MANAGER CONTAINED SEC");
                for (Element e : doc.select("entry")) {
//            if(e.attr("href").contains("https:")){
//                SECLinks.add(e.attr("href").toString());
//            }
                    String title = e.select("title").text();
                    String link = e.select("link").attr("href").toString();
                    SECLinks.add(link);
                    RSSItem rssItem = new RSSItem(title, link);
                    rssItems.add(rssItem);
//                System.out.println(rssItem.toString());

                }

            } else {
                for (Element e : doc.select("item")) {
//            if(e.attr("href").contains("https:")){
//                SECLinks.add(e.attr("href").toString());
//            }
                    String title = e.select("title").text();
                    String description = e.select("description").text();
                    description = Jsoup.parse(description).text();
                    description = description.replaceAll("\\<.*?\\>", "");
//                    System.out.println(description);
                    RSSItem rssItem = new RSSItem(title, "", description);
                    rssItems.add(rssItem);
//                System.out.println(rssItem.toString());
                }


            }
            lastModified = doc.select("updated").first().text();
        } else {
            System.out.println("The RSS feed was not modified so we are not grabbing the results");
        }


        /*

        When you receive the RSS file from the webserver,
        check the response header for two fields: Last-Modified and ETag.
        You don't have to care what is in these headers,
        you just have to store them somewhere with the RSS file.

        Next time you request the RSS file,
        include two headers in your request..
        Your If-Modified-Since header should contain the value you snagged from the Last-Modified header earlier.
        The If-None-Match header should contain the value you snagged from the ETag header.

        */


    }

    public void printRSSList() {
        for (RSSItem rssItem : rssItems) {
//            System.out.println(rssItem);
        }
    }

    @Override
    public void run() {

    }
}
