package com.example.ku.collection.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.ku.collection.bean.Repo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Ronan.zhuang
 * @Date 5/17/17.
 * All copyright reserved.
 */

public class RetrofitActivity extends BaseActivity {
    private static final String TAG = RetrofitActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .build();

                    GitHubService service = retrofit.create(GitHubService.class);

                    Call<List<Repo>> repos = service.listRepos("zhuangzaiku");
                    Response<List<Repo>> resp = repos.execute();
                    if(resp.isSuccessful()) {
                        List<Repo> list = resp.body();
                        for(Repo repo : list) {
                            Log.d(TAG,repo.getHtml_url());
                        }
//                        Log.d(TAG,resp.body().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }
}
