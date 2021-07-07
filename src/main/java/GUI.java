import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GUI implements MouseListener {

    static JPanel leftSideBar;
    JFrame frame;
    DefaultListModel model;
    DefaultListModel redditModel;
    DefaultListModel twitterModel;
    DefaultListModel webullModel;
    JFXPanel jfxPanel;
    JTabbedPane tabbedPane;
    JComponent chartTab;
    JComponent filingInfoTab;
    int width;
    int height;
    //    RSSManager rssManager;
    Border borderRed;
    Border borderBlue;
    Border borderCyan;
    Border borderYellow;
    Border borderGreen;
    Border borderPurple;
    Border borderPink;
    Border borderWhite;
    List<RSSManager> rssManagerList;
    JPanel socialFeedPanel;
    JPanel socialFeedReddit;
    JPanel socialFeedTwitter;
    JPanel socialFeedWebull;
    JScrollPane socialFeedRedditScrollPane;
    JScrollPane socialFeedTwitterScrollPane;
    JScrollPane socialFeedWebullScrollPane;
    //    JScrollPane socialFeedRedditScrollPane;
    JScrollPane listScroller;
    JScrollPane leftSidebarScrollPane;
    int internalWidth = 50;
    private JPanel list;
    private JList redditList;
    private JList twitterList;
    private JList webullList;

    //    JTextPane filingInfoTab;
    public GUI(RSSManager... rssManagers) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        // width will store the width of the screen
        width = (int) size.getWidth();

        // height will store the height of the screen
        height = (int) size.getHeight();


//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.put("TabbedPane.background", Color.RED);
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
        UIManager.put("TabbedPane.selected", Color.red);
        UIManager.put("TextField.highlight", Color.red);

//        this.rssManager = rssManager;
        rssManagerList = new ArrayList<>();
        rssManagerList.addAll(Arrays.asList(rssManagers));
//        listScroller = new JScrollPane(leftSideBar);

        frame = new JFrame("Bloomberg Copycat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setPreferredSize(new Dimension(width, height));
        frame.setSize(width, height);


        frame.setResizable(false);

        // redditModel = new DefaultListModel();
        // redditList = new JList(redditModel);

        //twitterModel = new DefaultListModel();
        // twitterList = new JList(twitterModel);

        //webullModel = new DefaultListModel();
        // webullList = new JList(webullModel);


//        GridLayout gridLayout = new GridLayout(2, 1);
//        gridLayout.setHgap(10);
//        gridLayout.setVgap(10);


        frame.setLayout(new BorderLayout());
//        frame.setLayout(new GridBagLayout());


        frame.getContentPane().setBackground(Color.black);
        ImageIcon icon = createImageIcon("/iconsss.png");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(borderPink);


        borderGreen = new LineBorder(Color.GREEN, 4, true);
        borderWhite = new LineBorder(Color.white, 4, true);
        borderPink = new LineBorder(Color.pink, 4, true);
        borderPurple = new LineBorder(Color.MAGENTA, 4, true);
        borderBlue = new LineBorder(Color.BLUE, 4, true);
        borderCyan = new LineBorder(Color.cyan, 4, true);
        borderRed = new LineBorder(Color.RED, 4, true);
        borderYellow = new LineBorder(Color.yellow, 4, true);
        tabbedPane.setBackground(Color.black);
        tabbedPane.setPreferredSize(new Dimension(width * 2 / 3, height / 2));
        JTabbedPane tabbedPane3 = new JTabbedPane();
        JTabbedPane tabbedPane4 = new JTabbedPane();
        chartTab = new JPanel();
        chartTab.setOpaque(true);
        chartTab.setBorder(borderYellow);
        chartTab.setBackground(Color.black);
        filingInfoTab = new JPanel();
//
//        filingInfoTab = new JTextPane();
//        filingInfoTab.setContentType( "text/html" );
//        filingInfoTab.setEditable(false);


        tabbedPane.setUI(new BasicTabbedPaneUI() {
            private final Insets borderInsets = new Insets(0, 0, 0, 0);

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected Insets getContentBorderInsets(int tabPlacement) {
                return borderInsets;
            }
        });


        tabbedPane.addTab("Chart", icon, chartTab, "Chart");
        filingInfoTab.setBorder(borderBlue);
//        filingInfoTab.setBackground(Color.black);
        filingInfoTab.setOpaque(true);
        tabbedPane.addTab("Filing Info", icon, filingInfoTab, "See Filing information");
        JComponent stockInfoPanel = new JPanel();
        stockInfoPanel.setOpaque(true);
        stockInfoPanel.setBorder(borderYellow);
        stockInfoPanel.setBackground(Color.black);
        stockInfoPanel.setForeground(Color.white);
        stockInfoPanel.setLayout(new BoxLayout(stockInfoPanel, BoxLayout.Y_AXIS));
        Stock stock;
        try {
            stock = YahooFinance.get("AAPL");
            JLabel name = new JLabel(stock.getName());
            name.setForeground(Color.white);
            JLabel exchange = new JLabel(stock.getStockExchange());
            exchange.setForeground(Color.white);
            JLabel stats = new JLabel(stock.getStats().toString());
            stats.setForeground(Color.white);
            JLabel currency = new JLabel(stock.getCurrency());
            currency.setForeground(Color.white);
            stockInfoPanel.add(name);
            stockInfoPanel.add(exchange);
            stockInfoPanel.add(stats);
            stockInfoPanel.add(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tabbedPane.addTab("Stock Info", icon, stockInfoPanel, "See Stock Info");
        tabbedPane.setBorder(borderPink);


        socialFeedPanel = new JPanel();

//        socialFeedReddit = new JPanel(new GridLayout(50, 1));
//        socialFeedTwitter = new JPanel(new GridLayout(50, 1));
//        socialFeedWebull = new JPanel(new GridLayout(50, 1));

        socialFeedReddit = new JPanel();
        socialFeedReddit.setLayout(new FlowLayout());
//        socialFeedReddit.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        socialFeedTwitter = new JPanel();
        socialFeedTwitter.setLayout(new FlowLayout());
//        socialFeedTwitter.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        socialFeedWebull = new JPanel();
        socialFeedWebull.setLayout(new FlowLayout());
//        socialFeedWebull.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        socialFeedRedditScrollPane = new JScrollPane(socialFeedReddit);
        socialFeedTwitterScrollPane = new JScrollPane(socialFeedTwitter);
        socialFeedWebullScrollPane = new JScrollPane(socialFeedWebull);

        socialFeedRedditScrollPane.setPreferredSize(new Dimension(width / 5, height / 10));
        socialFeedTwitterScrollPane.setPreferredSize(new Dimension(width / 5, height / 10));
        socialFeedWebullScrollPane.setPreferredSize(new Dimension(width / 5, height / 10));

        socialFeedRedditScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        socialFeedTwitterScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        socialFeedWebullScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));


        socialFeedReddit.setPreferredSize(new Dimension(socialFeedRedditScrollPane.getWidth() / 2, height / 2));
        socialFeedTwitter.setPreferredSize(new Dimension(socialFeedTwitterScrollPane.getWidth() / 2, height / 2));
        socialFeedWebull.setPreferredSize(new Dimension(socialFeedWebullScrollPane.getWidth() / 2, height / 2));


        socialFeedReddit.setBackground(Color.red);
        socialFeedWebull.setBackground(Color.green);
        socialFeedTwitter.setBackground(Color.pink);

        socialFeedPanel.setLayout(new GridLayout());
        socialFeedPanel.setOpaque(true);
        socialFeedPanel.setPreferredSize(new Dimension(width / 2, height / 2));
        socialFeedPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

//        socialFeedPanel.add(socialFeedReddit);
//        socialFeedPanel.add(socialFeedWebull);
//        socialFeedPanel.add(socialFeedTwitter);

        socialFeedPanel.add(socialFeedRedditScrollPane);
        socialFeedPanel.add(socialFeedTwitterScrollPane);
        socialFeedPanel.add(socialFeedWebullScrollPane);


        tabbedPane.addTab("Social Feed", icon, socialFeedPanel, "TWITTER/REDDIT/WEBULL");
        tabbedPane.setOpaque(true);
//        tabbedPane.setBackgroundAt(0, Color.yellow);
//        tabbedPane.setBackgroundAt(1, Color.blue);
//        tabbedPane.setBackgroundAt(2, Color.pink);
//        tabbedPane.setBackgroundAt(3, Color.orange);
        tabbedPane.setBackgroundAt(0, Color.black);
        tabbedPane.setBackgroundAt(1, Color.black);
        tabbedPane.setBackgroundAt(2, Color.black);
        tabbedPane.setBackgroundAt(3, Color.black);

        tabbedPane.setForegroundAt(0, Color.white);
        tabbedPane.setForegroundAt(1, Color.white);
        tabbedPane.setForegroundAt(2, Color.white);
        tabbedPane.setForegroundAt(3, Color.white);


        frame.getContentPane().setBackground(Color.black);


        leftSideBar = new JPanel();

        leftSideBar.setLayout(new FlowLayout(FlowLayout.LEFT));

//        leftSideBar.setBorder(borderGreen);
        leftSideBar.setOpaque(true);
        leftSideBar.setBackground(Color.black);
        leftSideBar.setForeground(Color.white);
        leftSideBar.setPreferredSize(new Dimension(width / 10, height / 2));

        JScrollPane leftSideBarScrollPane = new JScrollPane(leftSideBar);
        leftSideBarScrollPane.setBorder(borderGreen);
        leftSideBarScrollPane.setPreferredSize(new Dimension(width / 10, height / 2));
        leftSideBarScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        leftSideBarScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));


        JPanel rightSideBar = new JPanel();
        rightSideBar.setBorder(borderGreen);
        rightSideBar.setBackground(Color.black);
        rightSideBar.setPreferredSize(new Dimension(width / 10, height / 2));

        for (int i = 0; i < 15; i++) {
            JLabel b = new JLabel("filler text");
            b.setForeground(Color.white);
            rightSideBar.add(b);
        }


        TitledBorder socialFeedRedditBorderTitle = BorderFactory.createTitledBorder(borderRed, "Reddit");
        socialFeedRedditBorderTitle.setTitleColor(Color.red);
        TitledBorder socialFeedTwitterBorderTitle = BorderFactory.createTitledBorder(borderCyan, "Twitter");
        socialFeedTwitterBorderTitle.setTitleColor(Color.cyan);
        TitledBorder socialFeedWebullBorderTitle = BorderFactory.createTitledBorder(borderWhite, "Webull");
        socialFeedWebullBorderTitle.setTitleColor(Color.white);
        socialFeedRedditScrollPane.setBorder(socialFeedRedditBorderTitle);
        socialFeedRedditScrollPane.setBackground(Color.black);
        socialFeedTwitterScrollPane.setBackground(Color.black);
        socialFeedWebullScrollPane.setBackground(Color.black);
        socialFeedTwitterScrollPane.setBorder(socialFeedTwitterBorderTitle);
        socialFeedWebullScrollPane.setBorder(socialFeedWebullBorderTitle);
//        socialFeedReddit.setOpaque(true);
        socialFeedReddit.setBackground(Color.black);
        socialFeedReddit.setForeground(Color.white);
        socialFeedReddit.setPreferredSize(new Dimension(width / 10, height / 2));


//        socialFeedTwitter.setOpaque(true);
        socialFeedTwitter.setBackground(Color.black);
        socialFeedTwitter.setForeground(Color.white);
        socialFeedTwitter.setPreferredSize(new Dimension(width / 10, height / 2));


//        socialFeedWebull.setOpaque(true);
        socialFeedWebull.setBackground(Color.black);
        socialFeedWebull.setForeground(Color.white);
        socialFeedWebull.setPreferredSize(new Dimension(width / 10, height / 2));

        socialFeedReddit.setLayout(new BoxLayout(socialFeedReddit, BoxLayout.Y_AXIS));
        socialFeedWebull.setLayout(new BoxLayout(socialFeedWebull, BoxLayout.Y_AXIS));
        socialFeedTwitter.setLayout(new BoxLayout(socialFeedTwitter, BoxLayout.Y_AXIS));

        populateRSSFeeds();
        frame.add(leftSideBarScrollPane, BorderLayout.WEST);
        //populateLeftSidebar();
        frame.add(rightSideBar, BorderLayout.EAST);
        startTerminal();
        frame.add(tabbedPane, BorderLayout.CENTER);
        LoadWebPage();
//        frame.setBackground(Color.black);
        frame.setVisible(true);

        new Thread(() -> {
            try {
                while(true){
                    populateRSSFeeds();
//                    System.out.println("Sleeping for 5000 ms");
                    frame.revalidate();
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();



//        frame.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                System.out.println("Resized to " + e.getComponent().getSize());
////                width = e.getComponent().getWidth();
////                height = e.getComponent().getHeight();
////                frame.setSize(width, height);
////                tabbedPane.setSize(new Dimension(width * 2 / 3, height / 2));
////                socialFeedRedditScrollPane.setSize(new Dimension(width / 5, height / 10));
////                socialFeedTwitterScrollPane.setSize(new Dimension(width / 5, height / 10));
////                socialFeedWebullScrollPane.setSize(new Dimension(width / 5, height / 10));
////
////                socialFeedRedditScrollPane.getVerticalScrollBar().setSize(new Dimension(0, 0));
////                socialFeedTwitterScrollPane.getVerticalScrollBar().setSize(new Dimension(0, 0));
////                socialFeedWebullScrollPane.getVerticalScrollBar().setSize(new Dimension(0, 0));
////
////                socialFeedReddit.setSize(new Dimension(width / 5, height / 2));
////                socialFeedTwitter.setSize(new Dimension(width / 5, height / 2));
////                socialFeedWebull.setSize(new Dimension(width / 5, height / 2));
////                socialFeedPanel.setSize(new Dimension(width / 2, height / 2));
////                leftSideBar.setSize(new Dimension(width / 10, height / 2));
////                rightSideBar.setSize(new Dimension(width / 10, height / 2));
////                socialFeedReddit.setSize(new Dimension(width / 10, height / 2));
////                socialFeedTwitter.setSize(new Dimension(width / 10, height / 2));
////                socialFeedWebull.setSize(new Dimension(width / 10, height / 2));
////                listScroller.setSize(new Dimension(width, height / 2));
////                jfxPanel.setSize(new Dimension(chartTab.getWidth() - 10, chartTab.getHeight() - 10));
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent e) {
//                System.out.println("Moved to " + e.getComponent().getLocation());
//            }
//        });

    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    private void startTerminal() {
        // model = new DefaultListModel();
//        list = new JList(model);
//        list = new JList();
//
//        list.setBorder(borderPurple);
//        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        list.setPreferredSize(new Dimension(width, height/2));
//        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        list.setVisibleRowCount(-1);
//        list.setBackground(Color.black);
//        list.setForeground(Color.white);
//        list = new JPanel(new BoxLayout(list, BoxLayout.PAGE_AXIS));
        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
//        BoxLayout boxLayout = new BoxLayout(list);
//        list .setLayout(new FlowLayout(FlowLayout.LEFT));

        list.setOpaque(true);
        list.setBackground(Color.black);
        list.setForeground(Color.white);
        list.setPreferredSize(new Dimension(width / 10, height / 2));
//        list.setLayout(new FlowLayout(FlowLayout.LEFT));


        listScroller = new JScrollPane(list);
        listScroller.setBorder(borderGreen);
        listScroller.getVerticalScrollBar().setBackground(new Color(107, 97, 97));
        listScroller.setPreferredSize(new Dimension(width, height / 2));
        listScroller.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));


//        listScroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
//            @Override
//            protected void configureScrollBarColors() {
//                this.thumbColor = new Color(66, 64, 64, 255);
//            }
//        });


        listScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getValue());
            }
        });

        listScroller.setPreferredSize(new Dimension(width, height / 2));
        frame.add(listScroller, BorderLayout.SOUTH);
    }

    public void LoadWebPage() {
        System.out.println("chart dimensions: " + chartTab.getSize().toString());
        jfxPanel = new JFXPanel();
        Platform.runLater(() -> {

            jfxPanel.setBackground(Color.black);
            jfxPanel.setBorder(borderRed);
            System.out.println("jfxpanel dimensions: " + jfxPanel.getPreferredSize().toString());
            jfxPanel.setPreferredSize(new Dimension(chartTab.getWidth() - 10, chartTab.getHeight() - 10));
            WebView webView = new WebView();
            System.out.println("chart dimensions: " + chartTab.getSize().toString());
            System.out.println("Webview dimensions: " + webView.getPrefWidth() + " x " + webView.getPrefHeight());
            webView.setContextMenuEnabled(false);
            jfxPanel.setScene(new Scene(webView));

            jfxPanel.setBackground(new Color(107, 97, 97));

            webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36");
            webView.getEngine().load("https://kevn.wtf/Tradingview-embed/");
        });

        jfxPanel.setAlignmentY(0);
        jfxPanel.setAlignmentX(0);
        chartTab.add(jfxPanel);

    }

    public void updateText(String textBefore) {
//        model.addElement(textBefore);
        JLabel label = new JLabel("<html><div style='text-align: left;'>" + textBefore + "</div></html>");
        label.setFocusable(true);
        label.setPreferredSize(new Dimension(width, 10));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        System.out.println(textBefore);
        label.setForeground(Color.white);
        list.add(label);
//        try {
////            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void populateLeftSidebar() {

    }

    public void updateFeeds() {

    }

    public void populateRSSFeeds() throws InterruptedException {
        for (RSSManager r : rssManagerList) {
            r.updateRSS();

            List<RSSItem> rssItems = r.getRssItems();


            if (r.getURL().contains("sec")) {
                List<RSSItem> rssItemList = r.rssItems;
                for (RSSItem rssItem : rssItemList) {
                    String s = rssItem.title.split("\\p{Ps}", 2)[0];
                    JLabel j = new JLabel("<html><div style='text-align: left;'>" + s + "</div></html>");

                    j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    j.setForeground(Color.white);
                    j.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            String url = RSSManager.hyperlinks.get(j);

                            JFXPanel jfxPanel = new JFXPanel();
                            jfxPanel.setBackground(Color.black);
                            jfxPanel.setPreferredSize(filingInfoTab.getSize());
                            filingInfoTab.removeAll();
                            filingInfoTab.add(jfxPanel);

                            Platform.runLater(() -> {
                                WebView webView = new WebView();
                                webView.setMinSize(filingInfoTab.getWidth(), filingInfoTab.getHeight());
                                webView.setPrefSize(filingInfoTab.getWidth(), filingInfoTab.getHeight());
                                webView.resize(filingInfoTab.getWidth(), filingInfoTab.getHeight());
                                Scene scene = new Scene(webView);
//                                scene.getRoot().setStyle("-fx-base:black");


//                                jfxPanel.setScene(new Scene(webView));
                                jfxPanel.setScene(scene);
                                System.out.println("filing tab alignment: " + filingInfoTab.getAlignmentY());
                                System.out.println("jfxpanel alignment: " + jfxPanel.getAlignmentY());
                                jfxPanel.setAlignmentY(filingInfoTab.getAlignmentY());
                                Dimension d = filingInfoTab.getPreferredSize();
                                webView.getEngine().load(rssItem.link);
                            });
                            jfxPanel.revalidate();
                            jfxPanel.setVisible(true);
//                            try {
//                                HTMLDocument document = (HTMLDocument)filingInfoTab.getDocument();
//                                HTMLEditorKit editorKit = (HTMLEditorKit)filingInfoTab.getEditorKit();
////                                document.
//                                String htmlText = Jsoup.connect(url).ignoreContentType(true).get().toString();
//                                System.out.println(htmlText);
//                                String text = "your HTML here";
//                                editorKit.insertHTML(document, document.getLength(), htmlText, 0, 0, null);
//
//                            } catch (IOException ioException) {
//                                ioException.printStackTrace();
//                            } catch (BadLocationException badLocationException) {
//                                badLocationException.printStackTrace();
//                            }


//                            filingInfoTab.revalidate();
                            tabbedPane.setSelectedComponent(filingInfoTab);
                        }
                    });
                    leftSideBar.add(j);
                }
            }

            if (r.getURL().contains("reddit")) {
//                System.out.println("REDDIT IS TRUE");
                for (RSSItem rssItem : rssItems) {


                    JTextArea textArea = new JTextArea(1, 1);
                    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    textArea.setSize(socialFeedRedditScrollPane.getWidth(), height / 30);
                    textArea.setText(rssItem.title);
//                    textArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
//                    textArea.setLineWrap(true);
//                    textArea.setWrapStyleWord(true);
//                    textArea.setLineWrap(true);
                    textArea.setOpaque(false);
                    textArea.setEditable(false);
                    textArea.setFocusable(true);
                    textArea.setForeground(Color.white);
                    socialFeedReddit.add(textArea);
                    socialFeedReddit.revalidate();
//                    System.out.println(rssItem.title);

//                    JLabel b = new JLabel(rssItem.title);
//                    b.setForeground(Color.white);
//                    socialFeedReddit.add(b);

                }
            }
            if (r.getURL().contains("https://kevn.wtf/RSSFeed/feed.twitter.xml")) {
//                System.out.println("TWITTER IS TRUE");
                for (RSSItem rssItem : rssItems) {
                    JTextArea textArea = new JTextArea(1, 1);
                    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    textArea.setSize(socialFeedRedditScrollPane.getWidth(), height / 30);

                    textArea.setText(rssItem.title + rssItem.description);
//                    textArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
                    textArea.setLineWrap(true);

//                    textArea.setWrapStyleWord(true);
//                    textArea.setLineWrap(true);
                    textArea.setOpaque(false);
                    textArea.setEditable(false);
                    textArea.setFocusable(true);
                    textArea.setForeground(Color.white);
                    socialFeedTwitter.add(textArea);
                    socialFeedTwitter.revalidate();
//                    System.out.println(rssItem.title);
//                    JLabel b = new JLabel(rssItem.title);
//                    b.setForeground(Color.white);
//                    socialFeedTwitter.add(b);
//                    System.out.println(rssItem.title);
                }
            }
            if (r.getURL().contains("https://kevn.wtf/RSSFeed/feed.webull.xml")) {
//                System.out.println("WEBULL IS TRUE");
                for (RSSItem rssItem : rssItems) {

                    String description = rssItem.description;
                    JTextArea textArea = new JTextArea(1, 1);
                    textArea.setLineWrap(true);
                    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    textArea.setSize(socialFeedRedditScrollPane.getWidth(), height / 30);
                    textArea.setText(description);
//                    textArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
                    textArea.setToolTipText(description);
//                    textArea.setWrapStyleWord(true);
//                    textArea.setLineWrap(true);
                    textArea.setOpaque(false);
                    textArea.setEditable(false);
                    textArea.setFocusable(true);
                    textArea.setForeground(Color.white);
                    socialFeedWebull.add(textArea);
                    socialFeedWebull.revalidate();

//                    System.out.println(description);
//                    JLabel b = new JLabel(rssItem.title);
//                    b.setForeground(Color.white);
//                    socialFeedWebull.add(b);
//                    System.out.println(rssItem.title);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            String url = RSSManager.hyperlinks.get(e.getSource());
            Desktop.getDesktop().browse(new URI(url));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
