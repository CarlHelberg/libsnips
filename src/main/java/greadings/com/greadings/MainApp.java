package greadings.com.greadings;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        LibraryView libraryView = new LibraryView();
        libraryView.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
