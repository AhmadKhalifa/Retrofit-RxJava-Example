package orangelabs.com.retrofitexample.src.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import orangelabs.com.retrofitexample.R;
import orangelabs.com.retrofitexample.src.data.service.RemoteMoviesService;
import orangelabs.com.retrofitexample.src.model.Movie;
import orangelabs.com.retrofitexample.src.presenter.MoviesPresenter;
import orangelabs.com.retrofitexample.src.presenter.view.MoviesView;
import orangelabs.com.retrofitexample.src.ui.recycler.MoviesAdapter;

public class MainActivity extends AppCompatActivity implements MoviesView {

    @BindView(R.id.movies_recycler_view)
    RecyclerView mRecyclerView;

    @BindString(R.string.api_key)
    String mApiKey;

    @BindString(R.string.error_getting_movies)
    String mErrorGettingMoviesMessage;

    private MoviesAdapter mMoviesAdapter;
    private MoviesPresenter mMoviesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        ButterKnife.bind(this);
        mMoviesAdapter = new MoviesAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMoviesAdapter);
        mMoviesPresenter = new MoviesPresenter(this, new RemoteMoviesService());
        mMoviesPresenter.loadMovies();
    }


    @Override
    public void onMoviesRetrieved(List<Movie> movieList) {
        mMoviesAdapter.setMovies(movieList);
    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        switch (errorCode) {
            case MoviesPresenter.ERROR_GETTING_MOVIES:
                Toast.makeText(this, mErrorGettingMoviesMessage + " " + errorMessage

                        , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mMoviesPresenter.onDestroy();
        super.onDestroy();
    }
}
