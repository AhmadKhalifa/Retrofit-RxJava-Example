package orangelabs.com.retrofitexample.src.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import orangelabs.com.retrofitexample.R;
import orangelabs.com.retrofitexample.src.model.Movie;

/**
 * Created by khalifa on 21/08/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> mMovies;

    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.movie_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.setContent(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_list_item_title)
        TextView mTitleTextView;

        @BindView(R.id.movie_list_item_release_date)
        TextView mReleaseDateTextView;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setContent(Movie movie) {
            mTitleTextView.setText(movie.getTitle());
            mReleaseDateTextView.setText(movie.getReleaseDate());
        }
    }
}
