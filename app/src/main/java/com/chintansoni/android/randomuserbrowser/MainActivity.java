package com.chintansoni.android.randomuserbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.chintansoni.android.randomuserbrowser.adapter.RandomUserAdapter;
import com.chintansoni.android.randomuserbrowser.api.response.RandomUserApiResponse;
import com.chintansoni.android.randomuserbrowser.api.service.RandomUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Call<RandomUserApiResponse> randomUserApiResponseCall;
    private RandomUserService randomUserService;
    private RecyclerView recyclerView;
    private RandomUserAdapter randomUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);
        randomUserAdapter = new RandomUserAdapter();
        recyclerView.setAdapter(randomUserAdapter);

        randomUserService = RandomUserApplication.get(this).getRandomUserService();
        randomUserApiResponseCall = randomUserService.randomUser(10);
        randomUserApiResponseCall.enqueue(new Callback<RandomUserApiResponse>() {
            @Override
            public void onResponse(Call<RandomUserApiResponse> call, Response<RandomUserApiResponse> response) {
                randomUserAdapter.setItems(response.body().getResults());
            }

            @Override
            public void onFailure(Call<RandomUserApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomUserApiResponseCall.cancel();
    }
}
