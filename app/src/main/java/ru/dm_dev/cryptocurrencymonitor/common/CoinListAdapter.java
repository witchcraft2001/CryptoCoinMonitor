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
        holder.category.setText(item.getSymbol());
        Glide
                .with(context)
                .load(baseImageUrl + item.getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
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
        public final TextView name;
        public final TextView category;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.text_full_name);
            category = (TextView)itemView.findViewById(R.id.text_symbol);
            image = (ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onClickItem(rowID);
                    }
                }
            });
//            ImageView btn = (ImageView)itemView.findViewById(R.id.button_edit);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (clickListener != null) {
//                        clickListener.onClickEditButton(rowID);
//                    }
//                }
//            });
        }

        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }
}
