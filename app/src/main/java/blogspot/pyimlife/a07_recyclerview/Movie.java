package blogspot.pyimlife.a07_recyclerview;

public class Movie {

    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String title;
    private String genre;
    private String year;
    private String timestamp;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_GENRE + " TEXT,"
                    + COLUMN_YEAR + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Movie() {
    }

    public Movie(int id, String title, String genre, String year, String timestamp) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.timestamp = timestamp;
    }

    public Movie(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
