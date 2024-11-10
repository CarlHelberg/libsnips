package greadings.com.greadings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private String filePath;

    public void loadBooks(String filePath) {
        this.filePath = filePath;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String title = data[0];
                    String author = data[1];
                    boolean read = Boolean.parseBoolean(data[2]);
                    boolean owned = Boolean.parseBoolean(data[3]);
                    String coverImagePath = data[4];
                    books.add(new Book(title, author, read, owned, coverImagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBooks() {
        if (filePath == null) return;
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                pw.println(String.join(",", book.getTitle(), book.getAuthor(),
                        String.valueOf(book.isRead()), String.valueOf(book.isOwned()), book.getCoverImagePath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public void removeBook(Book book) {
        books.remove(book);
        saveBooks();
    }
}
