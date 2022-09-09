package com.test.customlauncher.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.customlauncher.model.App;
import com.test.customlauncher.databinding.ItemAppBinding;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private List<App> list;
    private final ClickInterface iClick;


    public AppAdapter(List<App> list, ClickInterface iClick) {
        this.list = list;
        this.iClick = iClick;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AppViewHolder(ItemAppBinding.inflate(LayoutInflater.from(viewGroup.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder viewHolder, final int i) {
        App app= list.get(i);
        if(app!=null){
            viewHolder.binding.name.setText(app.getName());
            viewHolder.binding.icon.setBackgroundDrawable(app.getDrawableIcon());
            viewHolder.itemView.setOnClickListener(view -> {
                if(iClick!=null){
                    iClick.onSelect(app);
                }
            });

            viewHolder.itemView.setOnLongClickListener(view -> {
                if(iClick!=null)
                    iClick.onLongPress(app);
                return false;
            });
        }
    }
    @Override
    public int getItemCount() {
        if(list==null)
            return 0 ;
        else
            return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<App> apps){
        list = new ArrayList<>();
        if(apps!=null) {
            list.addAll(apps);
            notifyDataSetChanged();
        }
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder{
        ItemAppBinding binding;
        public AppViewHolder(ItemAppBinding b){
            super(b.getRoot());
            binding = b;
        }
    }

    public interface ClickInterface{
        void onSelect(App app);
        void onLongPress(App app);
    }
}