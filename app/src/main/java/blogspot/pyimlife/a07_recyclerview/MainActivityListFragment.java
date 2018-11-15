package blogspot.pyimlife.a07_recyclerview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivityListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesAdapter mMoviesAdapter;
    private List<Movie> movieList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        mRecyclerView = view.findViewById(R.id.rvMovie);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        movieList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(movieList);
        mRecyclerView.setAdapter(mMoviesAdapter);

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
