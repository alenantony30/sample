package com.example.moviebox.viewmodel;

import com.example.moviebox.MainActivity;
import com.example.moviebox.model.MovieResults;
import com.example.moviebox.request.MovieRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PopularMoviesViewModel extends ViewModel {

    private MutableLiveData<MovieResults> mutableLiveData;
    private MovieRepository movieRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        movieRepository = MovieRepository.getInstance();


    }

    public LiveData<MovieResults> getMoviesRepository() {
        mutableLiveData = movieRepository.getMovies(MainActivity.CATEGORY2,MainActivity.PAGE);
        return mutableLiveData;
    }
}
