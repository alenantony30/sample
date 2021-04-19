package com.example.moviebox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviebox.MainActivity;
import com.example.moviebox.model.MovieResults;
import com.example.moviebox.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMovieAdapter  extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {

    private Context mContext;
    private List<MovieResults.ResultsBean> mData;

//    public PopularMovieAdapter(Context mContext, List<MoviePopular.ResultsBean> mData) {
//        this.mContext = mContext;
//        this.mData = mData;
//        Toast.makeText(mContext, "on constructor "+mData.size(), Toast.LENGTH_SHORT).show();
//    }

    public PopularMovieAdapter(MainActivity mContext, List<MovieResults.ResultsBean> listOfPopular) {
        this.mContext = mContext;
        this.mData = listOfPopular;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.most_popular_movies_rows,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getOriginal_title());
        holder.date.setText(mData.get(position).getRelease_date());
        //holder.image.

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mData.get(position).getPoster_path())
                .into(holder.popularImage);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView popularImage;
        TextView date;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImage=itemView.findViewById(R.id.popualarMovieImage);
            date=itemView.findViewById(R.id.popularMovieReleaseDate);
            name=itemView.findViewById(R.id.popularMovieName);
        }
    }
}
