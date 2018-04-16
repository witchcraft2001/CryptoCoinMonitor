package ru.dm_dev.cryptocurrencymonitor.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.dm_dev.cryptocurrencymonitor.api.models.CoinList;

public interface CryptoCompareService {
    @GET("all/coinlist")
    Call<CoinList> getCoinList();

    //https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC,USD,EUR,RUR
    @GET("price")
    Call<Map<String, Double>> getPrice(@Query("fsym") String fromSymbol, @Query("tsyms") String toSymbols);
}
