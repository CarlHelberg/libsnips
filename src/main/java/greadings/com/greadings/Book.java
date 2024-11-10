package greadings.com.greadings;

public class Book {
    private String title;
    private String author;
    private boolean read;
    private boolean owned;
    private String coverImagePath;

    public Book(String title, String author, boolean read, boolean owned, String coverImagePath) {
        this.title = title;
        this.author = author;
        this.read = read;
        this.owned = owned;
        this.coverImagePath = coverImagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }
}
