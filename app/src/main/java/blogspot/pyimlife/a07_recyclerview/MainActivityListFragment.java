package blogspot.pyimlife.a07_recyclerview;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityListFragment extends Fragment {

    private static final String TAG = "ViewsTutorApp";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesAdapter mMoviesAdapter;

    // List of Movies
    private List<Movie> movieList;

    // SQLite DB
    MovieDatabaseHelper movies_db;
    TextView noMoviesView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);


        // SQLite DB
        movies_db = new MovieDatabaseHelper(getContext());
        movies_db.createDefaultMoviesIfNeed();
        noMoviesView = view.findViewById(R.id.empty_movies_view);

        // RecyclerView
        mRecyclerView = view.findViewById(R.id.rvMovie);
        mLayoutManager = new LinearLayoutManager(getContext());
        // HORIZONTAL scrolling
        // mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        movieList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(movieList);
        mRecyclerView.setAdapter(mMoviesAdapter);

        mRecyclerView.addOnItemTouchListener(new MovieTouchListener(getContext(), mRecyclerView, new MovieTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
                TextView textView = (TextView) view.findViewById(R.id.year);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Single Click on YEAR! " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {
                //Movie movie = movieList.get(position);
                //Toast.makeText(getContext(), movie.getTitle() + " long clicked", Toast.LENGTH_SHORT).show();
                showActionDialog(position);
            }
        }));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setupList();
        movieList.addAll(movies_db.getAllMovies());
        mMoviesAdapter.notifyDataSetChanged();
        toggleEmptyMovies();

    }

    private void showActionDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showMovieDialog(true, movieList.get(position), position);
                } else {
                    deleteMovie(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Inserting new movie in db
     * and refreshing the list
     */
    private void createMovie(Movie movie) {
        // inserting note in db and getting
        // newly inserted note id
        long id = movies_db.insertMovie(movie);

        // get the newly inserted note from db
        Movie m = movies_db.getMovie(id);

        if (m != null) {
            // adding new note to array list at 0 position
            movieList.add(0, m);

            // refreshing the list
            mMoviesAdapter.notifyDataSetChanged();

            toggleEmptyMovies();
        }
    }

    /**
     * Updating movie in db and updating
     * item in the list by its position
     */
    private void updateMovie(Movie movie, int position) {
        Movie m = movieList.get(position);
        // updating movie text
        m.setTitle(movie.getTitle());
        m.setGenre(movie.getGenre());
        m.setYear(movie.getYear());

        // updating movie in db
        movies_db.updateMovie(m);

        // refreshing the list
        movieList.set(position, m);
        mMoviesAdapter.notifyItemChanged(position);

        toggleEmptyMovies();
    }

    /**
     * Deleting movie from SQLite and removing the
     * item from the list by its position
     */
    private void deleteMovie(int position) {
        // deleting the movie from db
        movies_db.deleteMovie(movieList.get(position));

        // removing the movie from the list
        movieList.remove(position);
        mMoviesAdapter.notifyItemRemoved(position);

        toggleEmptyMovies();
    }

    private void toggleEmptyMovies() {
        // movieList.size() > 0 or
        if (movies_db.getMoviesCount() > 0) {
            noMoviesView.setVisibility(View.GONE);
        } else {
            noMoviesView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows alert dialog with EditText options to enter / edit a movie.
     * when shouldUpdate=true, it automatically displays old movie and changes the
     * button text to UPDATE
     */
    public void showMovieDialog(final boolean shouldUpdate, final Movie movie, final int position) {
        Log.d(TAG, "showMovieDialog");

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.movie_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText edtTitle = view.findViewById(R.id.edtTitle);
        final EditText edtGenre= view.findViewById(R.id.edtGenre);
        final EditText edtYear = view.findViewById(R.id.edtYear);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_movie_title) : getString(R.string.lbl_edit_movie_title));

        if (shouldUpdate && movie != null) {
            edtTitle.setText(movie.getTitle());
            edtGenre.setText(movie.getGenre());
            edtYear.setText(movie.getYear());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(edtTitle.getText().toString())) {
                    Toast.makeText(getContext(), "Enter Movie!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                Movie m = new Movie();
                m.setTitle(edtTitle.getText().toString());
                m.setGenre(edtGenre.getText().toString());
                m.setYear(edtYear.getText().toString());

                // check if user updating note
                if (shouldUpdate && movie != null) {
                    // update note by it's id
                    updateMovie(m, position);
                } else {
                    // create new note
                    createMovie(m);
                }
            }
        });
    }

}
