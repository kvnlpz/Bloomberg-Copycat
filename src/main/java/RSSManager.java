import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
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

public class RSSManager {

    String URL = "https://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&CIK=&type=8-k&company=&dateb=&owner=include&start=0&count=100&output=atom";
    String xml;
    List<String> SECLinks = new ArrayList<>();
    static Map<JLabel, String> hyperlinks = new HashMap<>();
    public RSSManager() throws IOException {
        xml = Jsoup.connect(URL).get().toString();
        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
        for (Element e : doc.select("link")) {
            if(e.attr("href").contains("https:")){
                SECLinks.add(e.attr("href").toString());
            }
        }
    }

    public List<JLabel> getMatchingLinks() {
        int i = 1;
        List<JLabel> jLabels = new ArrayList<>();
        for(String link : SECLinks){
            try {
                Document html = Jsoup.connect(link).get();
                for(Element e : html.select("tr > td")){
                    if (e.text().contains("CURRENT REPORT")){
                        Element o = e.nextElementSibling().selectFirst("a");
                        String url = "https://www.sec.gov" + o.attr("href");
                        JLabel j = new JLabel();
                        j.setText("Filing " + i++);

                        jLabels.add(j);
                        hyperlinks.put(j,url);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jLabels;
    }

    public void updateRSS(){

    }
}
