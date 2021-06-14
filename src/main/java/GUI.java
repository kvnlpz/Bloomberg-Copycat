import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GUI implements MouseListener {

    JFrame frame;
    DefaultListModel model;
    private JList list;
    JTabbedPane tabbedPane;
    JComponent chartTab;
    JComponent filingInfoTab;
    int width = 800;
    int height = 600;
    static JPanel leftSideBar;
    RSSManager rssManager;
    Border borderRed;
    Border borderBlue;
    Border borderYellow;
    Border borderGreen;
    Border borderPurple;
    Border borderPink;

    public GUI(RSSManager rssManager) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.rssManager = rssManager;
        frame = new JFrame("Bloomberg Copycat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);


        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(107, 97, 97));
        ImageIcon icon = createImageIcon("icon.png");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(borderPink);
        borderGreen = new LineBorder(Color.GREEN, 4, true);
        borderPink = new LineBorder(Color.pink, 4, true);
        borderPurple = new LineBorder(Color.MAGENTA, 4, true);
        borderBlue = new LineBorder(Color.BLUE, 4, true);
        borderRed = new LineBorder(Color.RED, 4, true);
        borderYellow = new LineBorder(Color.yellow, 4, true);
        tabbedPane.setBackground(new Color(107, 97, 97));
        tabbedPane.setPreferredSize(new Dimension(width * 2 / 3, height / 2));
        JTabbedPane tabbedPane3 = new JTabbedPane();
        chartTab = new JPanel();
        chartTab.setBorder(borderYellow);
        chartTab.setBackground(new Color(107, 97, 97));
        filingInfoTab = new JPanel();
        tabbedPane.addTab("Chart", icon, chartTab, "Chart");
        filingInfoTab.setBorder(borderBlue);
        tabbedPane.addTab("Filing Info", icon, filingInfoTab, "See Filing information");
        JComponent panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        Stock stock;
        try {
            stock = YahooFinance.get("AAPL");
            panel2.add(new JLabel(stock.getName()));
            panel2.add(new JLabel(stock.getStockExchange()));
            panel2.add(new JLabel(stock.getStats().toString()));
            panel2.add(new JLabel(stock.getCurrency()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tabbedPane.addTab("Stock Info", icon, panel2, "See Stock Info");
        tabbedPane.setBorder(borderPink);
        JComponent panel3 = makeTextPanel("Panel #1");
        tabbedPane3.addTab("Tab 3", icon, panel3, "Does nothing");
        frame.getContentPane().setBackground(new Color(107, 97, 97));
        leftSideBar = new JPanel();
        leftSideBar.setBorder(borderGreen);
        leftSideBar.setOpaque(true);
        leftSideBar.setBackground(new Color(107, 97, 97));
        leftSideBar.setForeground(Color.white);
        leftSideBar.setPreferredSize(new Dimension(width / 10, height / 2));
        JPanel rightSideBar = new JPanel();
        rightSideBar.setBorder(borderGreen);
        rightSideBar.setBackground(new Color(107, 97, 97));
        rightSideBar.setPreferredSize(new Dimension(width / 10, height / 2));

        for (int i = 0; i < 15; i++) {
            JLabel b = new JLabel("filler text");
            b.setForeground(Color.white);
            rightSideBar.add(b);
        }

        frame.add(leftSideBar, BorderLayout.WEST);
        populateLeftSidebar();
        frame.add(rightSideBar, BorderLayout.EAST);
        startTerminal();
        frame.add(tabbedPane, BorderLayout.CENTER);
        LoadWebPage();
        frame.setVisible(true);


    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
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

    private void startTerminal() {
        model = new DefaultListModel();
        list = new JList(model);
        list.setBorder(borderPurple);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setBackground(new Color(107, 97, 97));
        list.setForeground(Color.white);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.getVerticalScrollBar().setBackground(new Color(107, 97, 97));

        listScroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(66, 64, 64, 255);
            }
        });
        listScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        listScroller.setPreferredSize(new Dimension(width, height / 2));
        frame.add(listScroller, BorderLayout.SOUTH);
    }

    public void LoadWebPage() {
        System.out.println("chart dimensions: " + chartTab.getSize().toString());
        JFXPanel jfxPanel = new JFXPanel();
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
        model.addElement(textBefore);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void populateLeftSidebar() {
        List<JLabel> leftSideBarLabels = rssManager.getMatchingLinks();
        for (JLabel j : leftSideBarLabels) {
            j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            j.setForeground(Color.white);
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Yay you clicked me");
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
                        jfxPanel.setScene(new Scene(webView));
                        System.out.println("filing tab alignment: " + filingInfoTab.getAlignmentY());
                        System.out.println("jfxpanel alignment: " + jfxPanel.getAlignmentY());
                        jfxPanel.setAlignmentY(filingInfoTab.getAlignmentY());
                        Dimension d = filingInfoTab.getPreferredSize();
                        webView.getEngine().load(RSSManager.hyperlinks.get(j));
                    });
                    jfxPanel.revalidate();
                    jfxPanel.setVisible(true);
                    tabbedPane.setSelectedComponent(filingInfoTab);
                }
            });
            leftSideBar.add(j);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!!!");
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
