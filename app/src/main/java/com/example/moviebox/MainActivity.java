 package com.example.moviebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviebox.adapter.PopularMovieAdapter;
import com.example.moviebox.api.Client;
import com.example.moviebox.api.Service;
import com.example.moviebox.model.MovieResults;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //now playing
    // https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=undefined&api_key=f847336cfad8c531603d08281a375f21

    //popular movie
    //https://api.themoviedb.org/3/movie/popular?api_key=f847336cfad8c531603d08281a375f21&language=en-US&page=1
    
    //image
    //https://image.tmdb.org/t/p/w500/

    RecyclerView recyclerView;
    PopularMovieAdapter popularMovieAdapter;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;



    List<MovieResults.ResultsBean> listOfMovies=new ArrayList<>();


    public static  String API_KEY="f847336cfad8c531603d08281a375f21";
    public static String LANGUAGE="en-US";
    public static String  PAGE="1";
    public static String CATEGORY1="now_playing";
    public static String CATEGORY2="popular";
    public static String LIMIT;
    //LinearLayout nowPlaying;
    LinearLayout nowPlayingMovies;
    LayoutInflater inflater;
    int pageIncremented;

    @Override
    protected void onResume() {
        super.onResume();
        PAGE="1";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // textView=findViewById(R.id.textView);

        //nowPlaying=findViewById(R.id.nowPlaying);

        recyclerView=findViewById(R.id.recyclerView);
        nestedScrollView=findViewById(R.id.nestedScrollView);
        progressBar=findViewById(R.id.progressBar3);



        nowPlayingMovies=findViewById(R.id.now_playing_movies_linear_layout);
        inflater=LayoutInflater.from(this);

        popularMovieAdapter=new PopularMovieAdapter(MainActivity.this,listOfMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(popularMovieAdapter);

        getDataFromTheMovieDB(CATEGORY1,"undefined");
        getDataFromTheMovieDB(CATEGORY2,"1");

     // Toast.makeText(this,LIMIT, Toast.LENGTH_SHORT).show();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                 //   Toast.makeText(MainActivity.this, "end of list", Toast.LENGTH_SHORT).show();
                    pageIncremented=Integer.parseInt(PAGE)+1;
                    if(pageIncremented<= Integer.parseInt(LIMIT)) {
                        PAGE = String.valueOf(pageIncremented);
                        // Toast.makeText(MainActivity.this, PAGE, Toast.LENGTH_SHORT).show();
                        getDataFromTheMovieDB(CATEGORY2, PAGE);
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    public void getDataFromTheMovieDB(String category,String page){


        Retrofit retrofit= Client.getClient();
        Service myInterface=retrofit.create(Service.class);
        Call<MovieResults>call=myInterface.listOfNowPlaying(category,API_KEY,LANGUAGE,page);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                if(response.isSuccessful()&&response!=null) {

                    MovieResults results = response.body();

                    LIMIT=results.getTotal_pages()+"";
                    List<MovieResults.ResultsBean> movies = results.getResults();

                    Log.i("pop", category + " " + page);
                    if (category == CATEGORY1)
                        nowShowingMovies(movies);
                    if (category == CATEGORY2)
                        popularMovies(movies);
                }
                else
                {
                    Log.i("error",response.code()+"");
                }



            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    private void popularMovies(List<MovieResults.ResultsBean> movies) {

        Log.i("pop",movies.size()+" ");
        if(movies.size()>0) {

            for (int i = 0; i < movies.size(); i++) {
                listOfMovies.add(movies.get(i));
               // Log.i("pop",movies.get(i).getTitle());
            }
            popularMovieAdapter.notifyDataSetChanged();

        }
    }

    private void nowShowingMovies(List<MovieResults.ResultsBean> movies) {
        //popularMoviesList=movies;
        if(movies.size()>0) {

            for (int i = 0; i < movies.size(); i++) {

                View view = inflater.inflate(R.layout.now_playing_item, nowPlayingMovies, false);
                ImageView imageView = view.findViewById(R.id.nowPlayingMovieImage);
                Glide.with(MainActivity.this)
                        .load("https://image.tmdb.org/t/p/w500/" + movies.get(i).getPoster_path())
                        .into(imageView);
                nowPlayingMovies.addView(view);
               // Log.i("now",movies.get(i).getTitle()+"\n");
            }
        }

    }


}