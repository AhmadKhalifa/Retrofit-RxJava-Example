package orangelabs.com.retrofitexample.src.data.service;

import java.util.List;

import orangelabs.com.retrofitexample.src.model.Movie;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static orangelabs.com.retrofitexample.src.presenter.MoviesPresenter.ERROR_GETTING_MOVIES;

/**
 * Created by khalifa on 21/08/17.
 */

public abstract class BaseMoviesService {

    public abstract Observable<Movie.MoviesList> newRetrieveMoviesObservable(String queryType);

    public Subscription newRetrieveMoviesSubscription(Observable<Movie.MoviesList> moviesListObservable,
                                               final GetMoviesCallback callback) {
        return moviesListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie.MoviesList>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.onError(ERROR_GETTING_MOVIES, e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Movie.MoviesList moviesList) {
                        if (callback != null) {
                            callback.onMoviesRetrieved(moviesList.getMovies());
                        }
                    }
                });
    }

    public interface GetMoviesCallback {
        void onMoviesRetrieved(List<Movie> movies);
        void onError(int errorCode, String errorMessage);
    }
}
