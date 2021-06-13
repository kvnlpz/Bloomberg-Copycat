import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
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
        System.out.println(xml);
        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
        for (Element e : doc.select("link")) {
            System.out.println(e.attr("href"));
            if(e.attr("href").contains("https:")){
                SECLinks.add(e.attr("href").toString());
            }
        }
        System.out.println(SECLinks.toString());
        getMatchingLinks(SECLinks);
    }

    private void getMatchingLinks(List<String> secLinks) {
        int i = 1;
        for(String link : secLinks){
            try {
                Document html = Jsoup.connect(link).get();
                for(Element e : html.select("tr > td")){
                    if (e.text().contains("CURRENT REPORT")){
                        Element o = e.nextElementSibling().selectFirst("a");
                        System.out.println(o.attr("href"));
                        String url = "https://www.sec.gov" + o.attr("href");
                        System.out.println("Current Report Link: " + url);
                        JLabel j = new JLabel();
                        j.setText("Filing " + i++);
                        j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        j.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.out.println("Yay you clicked me");
                                String url = RSSManager.hyperlinks.get(j);
                                try {
                                    Desktop.getDesktop().browse(new URI(url));
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (URISyntaxException uriSyntaxException) {
                                    uriSyntaxException.printStackTrace();
                                }
                            }

                        });
                        GUI.leftSideBar.add(j);
                        hyperlinks.put(j,url);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRSS(){

    }
}
