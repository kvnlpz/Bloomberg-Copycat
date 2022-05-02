// package com.example.bloombergcopycatredesigned
module com.example.bloombergcopycatredesigned {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    opens com.example.bloombergcopycatredesigned to javafx.fxml;
    exports com.example.bloombergcopycatredesigned;
}