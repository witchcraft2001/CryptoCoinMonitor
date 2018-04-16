package ru.dm_dev.cryptocurrencymonitor.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dm_dev.cryptocurrencymonitor.R;
import ru.dm_dev.cryptocurrencymonitor.api.CryptoCompareApi;
import ru.dm_dev.cryptocurrencymonitor.api.CryptoCompareService;
import ru.dm_dev.cryptocurrencymonitor.api.models.CoinList;
import ru.dm_dev.cryptocurrencymonitor.api.models.Data;
import ru.dm_dev.cryptocurrencymonitor.common.AdapterClickListener;
import ru.dm_dev.cryptocurrencymonitor.common.CoinListAdapter;
import ru.dm_dev.cryptocurrencymonitor.common.ItemDivider;

public class CoinListFragment extends Fragment implements AdapterClickListener {

    private static final String LOG_TAG = "CoinListFragment";
    private CryptoCompareService service;
    private View rootView;
    private RecyclerView recyclerView;
    private List<Data> coinList;
    private CoinListAdapter coinAdapter;
    private ProgressBar progress;

    public CoinListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_coin_list, container, false);
        progress = rootView.findViewById(R.id.progress);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getBaseContext()));

        coinAdapter = new CoinListAdapter(getContext(), null, this);

        recyclerView.setAdapter(coinAdapter);

        // attach a custom ItemDecorator to draw dividers between list items
        recyclerView.addItemDecoration(new ItemDivider(getContext()));

        // improves performance if RecyclerView's layout size never changes
        recyclerView.setHasFixedSize(false);

        service = CryptoCompareApi.getClient(getContext()).create(CryptoCompareService.class);

        loadCoinList();

        return rootView;
    }

    private void setProgressBarVisible(boolean visible) {
        progress.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setRecyclerViewVisible(boolean visible) {
        recyclerView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClickItem(long id) {
        Intent intent = new Intent(getContext(), CoinDetailsActivity.class);
        intent.putExtra(CoinDetailsActivity.COIN_SYMBOL, coinAdapter.getSymbolById(id));
        startActivity(intent);
    }

    private Call<CoinList> callCoinListApi() {
        return service.getCoinList();
    }

    private void loadCoinList() {
        Log.d(LOG_TAG, "loadCoinList");

        setProgressBarVisible(true);
        setRecyclerViewVisible(false);
        callCoinListApi().enqueue(
                new Callback<CoinList>() {
                    @Override
                    public void onResponse(Call<CoinList> call, Response<CoinList> response) {
                        Log.d(LOG_TAG, "loadFirstPage_cb: " + response.code());
                        CoinList items = response.body();
                        coinList = new ArrayList<Data>(items.getData().values());
                        coinAdapter.setBaseImageUrl(items.getBaseImageUrl());
                        coinAdapter.setList(coinList);
                        setRecyclerViewVisible(true);
                        setProgressBarVisible(false);
                    }

                    @Override
                    public void onFailure(Call<CoinList> call, Throwable t) {
                        t.printStackTrace();
                        setProgressBarVisible(false);
//                        setErrorVisible(getString(R.string.text_message_error_connection));
                    }
                }
        );
    }

}
