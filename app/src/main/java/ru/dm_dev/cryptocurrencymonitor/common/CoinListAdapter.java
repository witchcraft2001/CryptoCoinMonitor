package ru.dm_dev.cryptocurrencymonitor.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.dm_dev.cryptocurrencymonitor.R;
import ru.dm_dev.cryptocurrencymonitor.api.models.Data;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ViewHolder> {
    private List<Data> list;
    private AdapterClickListener clickListener;
    private String baseImageUrl;
    private Context context;

    public CoinListAdapter(Context context, List<Data> list, AdapterClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data item = list.get(position);
        holder.setRowID(item.getId());
        holder.name.setText(item.getFullName());
        holder.symbol.setText(item.getSymbol());
        Glide
                .with(context)
                .load(baseImageUrl + item.getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public String getSymbolById(long id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i).getSymbol();
            }
        }
        return null;
    }

    public void setList(List<Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private long rowID;
        //private String symbol;
        public final TextView name;
        public final TextView symbol;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_full_name);
            symbol = itemView.findViewById(R.id.text_symbol);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onClickItem(rowID);
                    }
                }
            });
        }

        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }
}
