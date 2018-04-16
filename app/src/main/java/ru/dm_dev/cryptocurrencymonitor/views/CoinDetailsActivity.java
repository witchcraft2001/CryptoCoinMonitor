package ru.dm_dev.cryptocurrencymonitor.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dm_dev.cryptocurrencymonitor.R;
import ru.dm_dev.cryptocurrencymonitor.api.CryptoCompareApi;
import ru.dm_dev.cryptocurrencymonitor.api.CryptoCompareService;

public class CoinDetailsActivity extends AppCompatActivity {

    public static final String COIN_SYMBOL = "COIN_SYMBOL";
    private static final String LOG_TAG = "CoinDetailsActivity";
    private String coinSymbol;
    private CryptoCompareService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_details);
        service = CryptoCompareApi.getClient(this).create(CryptoCompareService.class);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                coinSymbol = intent.getStringExtra(COIN_SYMBOL);
                Log.d(LOG_TAG, "Start view for = " + coinSymbol);
                if (!coinSymbol.isEmpty()) {
                    callPriceApi(coinSymbol).enqueue(new Callback<Map<String, Double>>() {
                        @Override
                        public void onResponse(@NonNull Call<Map<String, Double>> call, @NonNull Response<Map<String, Double>> response) {
                            Log.d(LOG_TAG, response.toString());
                        }

                        @Override
                        public void onFailure(Call<Map<String, Double>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    private Call<Map<String, Double>> callPriceApi(String symbol) {
        return service.getPrice(symbol, "USD,EUR,BTC");
    }
}
