package greadings.com.greadings;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class LibraryView {
    private BookManager bookManager = new BookManager(); // Initialized
    private VBox bookshelf;
    private TextField searchField;
    private Label bookCountLabel;
    private ListView<Book> bookListView;
    private Stage primaryStage;

    public void show(Stage stage) {
        primaryStage = stage;
        stage.setTitle("My Book Library");

        bookshelf = new VBox(10);
        searchField = new TextField();
        bookCountLabel = new Label();

        Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> addBook());

        Button loadButton = new Button("Load CSV");
        loadButton.setOnAction(e -> loadCSV(stage));

        HBox topBar = new HBox(10, searchField, addButton, loadButton);
        VBox layout = new VBox(10, topBar, bookCountLabel, bookshelf);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void loadCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            bookManager.loadBooks(file.getAbsolutePath());
            displayBooks(bookManager.getBooks());
        }
    }

    private void displayBooks(List<Book> books) {
        bookshelf.getChildren().clear();
        for (Book book : books) {
            HBox bookDisplay = new HBox(10);
            ImageView coverImage = new ImageView(new Image(new File(book.getCoverImagePath()).toURI().toString()));
            coverImage.setFitWidth(100);
            coverImage.setPreserveRatio(true);

            Label titleLabel = new Label(book.getTitle());
            Label authorLabel = new Label(book.getAuthor());

            bookDisplay.getChildren().addAll(coverImage, titleLabel, authorLabel);
            bookDisplay.setOnMouseClicked(e -> highlightBook(book));
            bookshelf.getChildren().add(bookDisplay);
        }
        updateBookCount();
    }

    private void highlightBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book Details");
        alert.setHeaderText(book.getTitle() + " by " + book.getAuthor());
        alert.setContentText("This book has been read: " + (book.isRead() ? "Read" : "Unread") + "\n" +
                "This book is in my physical collection: " + (book.isOwned() ? "Owned" : "Unowned"));

        ButtonType updateButton = new ButtonType("Update Info");
        ButtonType removeButton = new ButtonType("Remove Book");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(updateButton, removeButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == updateButton) {
                // Implement updating logic here
            } else if (result.get() == removeButton) {
                bookManager.removeBook(book);
                displayBooks(bookManager.getBooks());
            }
        }
    }

    private void addBook() {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");

        TextField titleInput = new TextField();
        TextField authorInput = new TextField();
        CheckBox readCheck = new CheckBox("Read?");
        CheckBox ownedCheck = new CheckBox("Owned?");
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        VBox dialogPane = new VBox(10, new Label("Title:"), titleInput, new Label("Author:"), authorInput, readCheck, ownedCheck);
        dialog.getDialogPane().setContent(dialogPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String coverImagePath = "";
                return new Book(titleInput.getText(), authorInput.getText(), readCheck.isSelected(), ownedCheck.isSelected(), coverImagePath);
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(book -> {
            bookManager.addBook(book);
            displayBooks(bookManager.getBooks());
        });
    }

    private void updateBookCount() {
        long readCount = bookManager.getBooks().stream().filter(Book::isRead).count();
        long ownedCount = bookManager.getBooks().stream().filter(Book::isOwned).count();
        bookCountLabel.setText("Read: " + readCount + " | Owned: " + ownedCount);
    }
}
