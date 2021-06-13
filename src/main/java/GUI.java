import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GUI implements MouseListener {


    static JLabel field1;
    static JLabel field2;
    static JLabel field3;
    static JLabel field4;
    private static String value = "";
    final int maxGap = 20;
    JLabel[] jLabels = new JLabel[4];
    String[] test = new String[4];
    JFrame frame;
    DefaultListModel model;
    private JList list;
    JTabbedPane tabbedPane;
    JComponent panel1;
    int width = 800;
    int height = 600;
    static JPanel leftSideBar;
    public GUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("Bloomberg Copycat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);


        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        frame.setLayout(new BorderLayout());
//        frame.setBackground(Color.black);



        ImageIcon icon = createImageIcon("resources/icon.jpg");
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.black);
        JTabbedPane tabbedPane2 = new JTabbedPane();
        JTabbedPane tabbedPane3 = new JTabbedPane();
        panel1 = new JPanel();
        tabbedPane.addTab("Tab 1", icon, panel1, "Does nothing");
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

//        panel2.add(new JLabel(stock.getName()));
//        panel2.add(new JLabel(stock.getName()));
        tabbedPane.addTab("Stock Info", icon, panel2, "See Stock Info");
        tabbedPane.setBackground(Color.black);
        tabbedPane.setPreferredSize(new Dimension(width*2 / 3, height/2));
        panel1.setBackground(Color.black);
//        tabbedPane2.addTab("Tab 2", icon, panel2, "Does nothing");
        JComponent panel3 = makeTextPanel("Panel #1");
        tabbedPane3.addTab("Tab 3", icon, panel3, "Does nothing");

        frame.getContentPane().setBackground(Color.black);
        Button b1 = new Button("Test");
        leftSideBar = new JPanel();
        leftSideBar.setPreferredSize(new Dimension(width/10, height/2));
        JPanel rightSideBar = new JPanel();
        rightSideBar.setPreferredSize(new Dimension(width/10, height/2));
        for(int i = 0; i < 15; i++){
//            JLabel j = new JLabel("filler text");
            JLabel b = new JLabel("filler text");
//            j.setBackground(Color.black);
            b.setBackground(Color.black);
//            j.setForeground(Color.white);
//            leftSideBar.add(j);
            rightSideBar.add(b);
        }
        Button b2 = new Button("Test");
        Button b3 = new Button("Test");
        b1.setBackground(new Color(209,196, 0));
        b2.setBackground(new Color(209,196, 0));
        b3.setBackground(new Color(209,196, 0));
        frame.add(b1, BorderLayout.NORTH);
        frame.add(leftSideBar, BorderLayout.WEST);
        frame.add(rightSideBar, BorderLayout.EAST);
        startTerminal();
        frame.add(tabbedPane, BorderLayout.CENTER);
//        frame.add(new Button("East"), BorderLayout.EAST);
//        frame.add(new Button("West"), BorderLayout.WEST);
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
    /** Returns an ImageIcon, or null if the path was invalid. */
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
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.yellow);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        listScroller.setPreferredSize(new Dimension(width, height/2));
        frame.add(listScroller, BorderLayout.SOUTH);
    }

    public void LoadWebPage()
    {
        JFXPanel jfxPanel = new JFXPanel();

        jfxPanel.setBackground(Color.black);
//        jfxPanel.setPreferredSize(new Dimension(tabbedPane.getWidth(), tabbedPane.getHeight()));
        panel1.add(jfxPanel);
        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.setPrefSize(tabbedPane.getWidth(), tabbedPane.getHeight());
            webView.resize(tabbedPane.getWidth(), tabbedPane.getHeight());

            jfxPanel.setScene(new Scene(webView));
            Dimension d = tabbedPane.getPreferredSize();
            webView.getEngine().load("https://charting-library.tradingview.com/");
        });

    }

    public void updateText(String textBefore) {
        model.addElement(textBefore);
    }

    void mouseClicked(){

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!!!");
        try{
            String url = RSSManager.hyperlinks.get(e.getSource());
            Desktop.getDesktop().browse(new URI(url));

        }catch (Exception exception){
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
