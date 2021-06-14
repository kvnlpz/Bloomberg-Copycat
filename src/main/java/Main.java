import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    static GUI gui;
    public static void main( String[] args ) throws Exception {
        System.out.println("Starting program");
        RSSManager rssManager = new RSSManager();
        gui = new GUI(rssManager);
        SymbolManager symbolManager = new SymbolManager();
        MailHandler mailHandler = new MailHandler(args[0], args[1]);
//        symbolManager.similaritySearch("Benzinga: BZ: Sorrento Therapeutics Reports Receives Authorization From UK Regulatory Agency To Conduct Phase 2 Trial For COVI-DROPS In Outpatient Setting");
    }
}
