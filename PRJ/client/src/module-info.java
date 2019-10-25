module client {
	requires shared;
	requires java.rmi;
	requires java.desktop;
	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	opens fx;
	exports fx;
}