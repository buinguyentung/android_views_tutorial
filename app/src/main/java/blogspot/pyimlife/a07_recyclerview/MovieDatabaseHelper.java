package blogspot.pyimlife.a07_recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movies_db";

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create movies table
        sqLiteDatabase.execSQL(Movie.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Should carefully handle this function on real implementation
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movie.TABLE_NAME);

        // Create table again
        onCreate(sqLiteDatabase);
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public long insertMovie(Movie movie) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Movie.COLUMN_TITLE, movie.getTitle());
        values.put(Movie.COLUMN_GENRE, movie.getGenre());
        values.put(Movie.COLUMN_YEAR, movie.getYear());

        // insert row
        long id = db.insert(Movie.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, movie.getTitle());
        values.put(Movie.COLUMN_GENRE, movie.getGenre());
        values.put(Movie.COLUMN_YEAR, movie.getYear());

        // update row
        return db.update(Movie.TABLE_NAME, values, Movie.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Movie.TABLE_NAME, Movie.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }

    public Movie getMovie(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{Movie.COLUMN_ID, Movie.COLUMN_TITLE, Movie.COLUMN_GENRE, Movie.COLUMN_YEAR, Movie.COLUMN_TIMESTAMP},
                Movie.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare movie object
        Movie movie = new Movie(
                cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_GENRE)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_YEAR)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return movie;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Movie.TABLE_NAME + " ORDER BY " +
                Movie.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
                movie.setGenre(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_GENRE)));
                movie.setYear(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_YEAR)));
                movie.setTimestamp(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TIMESTAMP)));

                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return movies list
        return movies;
    }

    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + Movie.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void createDefaultMoviesIfNeed() {
        int count = this.getMoviesCount();
        if (count == 0) {
            Movie movie = new Movie("Song o day song", "Society", "2000");
            this.insertMovie(movie);

            movie = new Movie("Ha Noi 12 ngay dem", "War", "1991");
            this.insertMovie(movie);

            movie = new Movie("Toi thay hoa vang tren co xanh", "Family", "2016");
            this.insertMovie(movie);

            movie = new Movie("Ong ngoai tuoi 30", "Family", "2018");
            this.insertMovie(movie);

            movie = new Movie("Nguoi phan xu", "Society", "2017");
            this.insertMovie(movie);

            movie = new Movie("Co gai dai duong", "Action & Adventure", "2002");
            this.insertMovie(movie);

            movie = new Movie("18 banh xe cong ly", "Action & Adventure", "1998");
            this.insertMovie(movie);

            movie = new Movie("Hercules", "Animation", "2004");
            this.insertMovie(movie);

            movie = new Movie("Star Trek", "Science Fiction", "2009");
            this.insertMovie(movie);

            movie = new Movie("Up", "Animation", "2009");
            this.insertMovie(movie);

            movie = new Movie("Avengers", "Science Fiction & Fantasy", "2004");
            this.insertMovie(movie);
        }
    }
}
