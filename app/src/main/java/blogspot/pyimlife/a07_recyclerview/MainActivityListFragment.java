package blogspot.pyimlife.a07_recyclerview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

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
                Movie movie = movieList.get(position);
                Toast.makeText(getContext(), movie.getTitle() + " long clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupList();
    }

    private void setupList() {
        Movie movie = new Movie("Song o day song", "Society", "2000");
        movieList.add(movie);

        movie = new Movie("Ha Noi 12 ngay dem", "War", "1991");
        movieList.add(movie);

        movie = new Movie("Toi thay hoa vang tren co xanh", "Family", "2016");
        movieList.add(movie);

        movie = new Movie("Ong ngoai tuoi 30", "Family", "2018");
        movieList.add(movie);

        movie = new Movie("Nguoi phan xu", "Society", "2017");
        movieList.add(movie);

        movie = new Movie("Co gai dai duong", "Action & Adventure", "2002");
        movieList.add(movie);

        movie = new Movie("18 banh xe cong ly", "Action & Adventure", "1998");
        movieList.add(movie);

        movie = new Movie("Hercules", "Animation", "2004");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Avengers", "Science Fiction & Fantasy", "2004");
        movieList.add(movie);

        mMoviesAdapter.notifyDataSetChanged();
    }

}
