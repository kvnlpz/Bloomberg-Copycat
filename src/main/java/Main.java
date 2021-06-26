public class Main {
    static GUI gui;

    public static void main(String[] args) throws Exception {
        String url1 = "https://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&CIK=&type=8-k&company=&dateb=&owner=include&start=0&count=100&output=atom";
        String url2 = "https://kevn.wtf/RSSFeed/feed.reddit.xml";
        String url3 = "https://kevn.wtf/RSSFeed/feed.twitter.xml";
        String url4 = "https://kevn.wtf/RSSFeed/feed.webull.xml";

        System.out.println("Starting program");
        RSSManager rssManager = new RSSManager(url1);
        RSSManager redditManager = new RSSManager(url2);
        RSSManager twitterManager = new RSSManager(url3);
        RSSManager webullManager = new RSSManager(url4);
        gui = new GUI(twitterManager, redditManager, webullManager);
        SymbolManager symbolManager = new SymbolManager();
        MailHandler mailHandler = new MailHandler(args[0], args[1]);
//        symbolManager.similaritySearch("Benzinga: BZ: Sorrento Therapeutics Reports Receives Authorization From UK Regulatory Agency To Conduct Phase 2 Trial For COVI-DROPS In Outpatient Setting");
    }
}
