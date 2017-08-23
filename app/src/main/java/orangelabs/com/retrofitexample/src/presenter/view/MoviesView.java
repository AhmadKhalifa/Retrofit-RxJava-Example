package orangelabs.com.retrofitexample.src.presenter.view;

import java.util.List;

import orangelabs.com.retrofitexample.src.model.Movie;

/**
 * Created by khalifa on 21/08/17.
 */

public interface MoviesView extends BaseView {
    void onMoviesRetrieved(List<Movie> movies);
}
