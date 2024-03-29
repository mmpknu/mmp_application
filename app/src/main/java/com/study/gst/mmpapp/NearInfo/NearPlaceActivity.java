package com.study.gst.mmpapp.NearInfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.study.gst.mmpapp.Adapter.NearPlaceAdapter;
import com.study.gst.mmpapp.R;
import com.study.gst.mmpapp.config;
import com.study.gst.mmpapp.model.GpsTracker;
import com.study.gst.mmpapp.RESTAPI.NetworkService;
import com.study.gst.mmpapp.model.Tour;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NearPlaceActivity extends AppCompatActivity {
    private boolean flag = false;
    private NearPlaceAdapter adapter = new NearPlaceAdapter();
    private Retrofit retrofit;
    private ArrayList<Tour> items = new ArrayList<>();

    private GpsTracker gpsTracker;


    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_place);







        gpsTracker = new GpsTracker(NearPlaceActivity.this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Log.d("tag","lopal gpsx"+latitude);

        new JSONTask().execute();


    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            init();
            NetworkService service = retrofit.create(NetworkService.class);
            Call<List<Tour>> call = service.get_version(latitude, longitude );

            call.enqueue(new Callback<List<Tour>>() {

                @Override
                public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                    List<Tour> tours = response.body();
                    for (Tour tour : tours) {
                        items.add(tour);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NearPlaceActivity.this, LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(adapter);

                    adapter.setItems(items);
                }

                @Override
                public void onFailure(Call<List<Tour>> call, Throwable t) {
                    Log.d("tag", "lopal fail");
                }
            });
            return "done";
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("result", "lopal onpostExecute");



        }

    }



    public void init() {
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl(config.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
