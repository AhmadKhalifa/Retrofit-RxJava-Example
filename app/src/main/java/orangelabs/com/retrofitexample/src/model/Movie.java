package orangelabs.com.retrofitexample.src.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khalifa on 21/08/17.
 */

public class Movie {

    public class MoviesList {
        @SerializedName("results")
        List<Movie> mMovies;

        public List<Movie> getMovies() {
            return mMovies;
        }
    }

    @SerializedName("title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDate;

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
