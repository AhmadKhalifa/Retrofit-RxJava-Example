package orangelabs.com.retrofitexample.src.data.service;

import orangelabs.com.retrofitexample.src.model.Movie;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by khalifa on 21/08/17.
 */

public class RemoteMoviesService extends BaseMoviesService {

    public Observable<Movie.MoviesList> newRetrieveMoviesObservable(String queryType) {
        MoviesClientService topRatedService = ServiceFactory.createRetrofitService(
                MoviesClientService.class,
                MoviesClientService.SERVICE_ENDPOINT
        );
        return topRatedService.getMoviesObservable(queryType);
    }

    private interface MoviesClientService {

       String SERVICE_ENDPOINT = "http://api.themoviedb.org";

        @GET("/3/movie/{queryType}?api_key=be1f8a35b0e7e4b3be7c1ce2b1e8cab8")
        Observable<Movie.MoviesList> getMoviesObservable(@Path("queryType") String queryType);
    }

}
