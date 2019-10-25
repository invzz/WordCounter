//da java 9  in poi per modularita`

module server {
	
	requires transitive shared;
	requires javafx.base;
	requires transitive java.rmi;
	requires java.desktop;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.media;
	requires javafx.swing;
	//requires javafx.swt;
	requires javafx.web;
	requires java.base;
	opens fx;
	exports fx;
	exports models;
	exports swing;
		
}