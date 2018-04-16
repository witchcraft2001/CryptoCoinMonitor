package ru.dm_dev.cryptocurrencymonitor.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.dm_dev.cryptocurrencymonitor.api.models.CoinList;

public interface CryptoCompareService {
    @GET("all/coinlist")
    Call<CoinList> getCoinList();
}
