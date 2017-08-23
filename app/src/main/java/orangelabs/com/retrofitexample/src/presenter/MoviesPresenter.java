package orangelabs.com.retrofitexample.src.presenter;

import java.util.ArrayList;
import java.util.List;

import orangelabs.com.retrofitexample.src.data.service.BaseMoviesService;
import orangelabs.com.retrofitexample.src.model.Movie;
import orangelabs.com.retrofitexample.src.presenter.view.MoviesView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by khalifa on 21/08/17.
 */

public class MoviesPresenter {

    public static final int ERROR_GETTING_MOVIES = 100;
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    private MoviesView mMoviesView;
    private BaseMoviesService mMoviesService;
    private CompositeSubscription mCompositeSubscription;

    public MoviesPresenter(MoviesView moviesView, BaseMoviesService moviesService) {
        mMoviesView = moviesView;
        mMoviesService = moviesService;
        mCompositeSubscription = new CompositeSubscription();
    }

    public void loadMovies() {
        if (mMoviesService != null) {
            final List<Movie> allMovies = new ArrayList<>();
            Observable<Movie.MoviesList> mostPopularMoviesObservable =
                    mMoviesService.newRetrieveMoviesObservable(MOST_POPULAR)
                            .flatMap(new Func1<Movie.MoviesList, Observable<Movie.MoviesList>>() {
                                @Override
                                public Observable<Movie.MoviesList>
                                call(Movie.MoviesList moviesList) {
                                    allMovies.addAll(moviesList.getMovies());
                                    return mMoviesService.newRetrieveMoviesObservable(TOP_RATED);
                                }
                            });
            Subscription mostPopularMoviesSubscription =
                    mMoviesService.newRetrieveMoviesSubscription(
                            mostPopularMoviesObservable,
                            new BaseMoviesService.GetMoviesCallback() {
                                @Override
                                public void onMoviesRetrieved(List<Movie> movies) {
                                    if (movies != null) {
                                        allMovies.addAll(movies);
                                        if (mMoviesView != null) {
                                            mMoviesView.onMoviesRetrieved(allMovies);
                                        }
                                    }
                                }

                                @Override
                                public void onError(int errorCode, String errorMessage) {
                                    if (mMoviesView != null) {
                                        mMoviesView.onError(errorCode, errorMessage);
                                    }
                                }
                            }
                    );
            mCompositeSubscription.add(mostPopularMoviesSubscription);
        }
        else if (mMoviesView != null) {
            mMoviesView.onError(ERROR_GETTING_MOVIES, "");
        }
    }

    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }
}
