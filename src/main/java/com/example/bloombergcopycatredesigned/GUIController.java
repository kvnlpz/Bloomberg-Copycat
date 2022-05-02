package com.example.bloombergcopycatredesigned;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GUIController {

    @FXML
    private WebView tradingViewChart;

    @FXML
    private Tab SECInfoTab;

    @FXML
    private Tab StockInfoTab;

    @FXML
    private Tab chartTab;

    @FXML
    private ScrollPane leftSideBar;

    @FXML
    private ScrollPane rightSideBar;


    @FXML
    private void initialize(){
        tradingViewChart.setContextMenuEnabled(false);
        tradingViewChart.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36");
        // tradingViewChart.getEngine().load("https://kevn.wtf/Tradingview-embed/");
        WebEngine engine = tradingViewChart.getEngine();
        engine.load("https://kevn.wtf/Tradingview-embed/");
    }

    // public  HelloController(){
    //     // tradingViewChart = new WebView();
    //     tradingViewChart.setContextMenuEnabled(false);
    //     tradingViewChart.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36");
    //     // tradingViewChart.getEngine().load("https://kevn.wtf/Tradingview-embed/");
    //     tradingViewChart.getEngine().load("https://www.google.com/");
    //     tradingViewChart.setVisible(true);
    // }

}