package com.example.wishstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.viewHolder>{
    ArrayList<PostsHelper> dataHolder;

    public cardAdapter(ArrayList<PostsHelper> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardAdapter.viewHolder holder, int position) {
        holder.proj_title.setText(dataHolder.get(position).getTitle());
        holder.proj_budget.setText(dataHolder.get(position).getBudget());
        holder.proj_bids.setText(dataHolder.get(position).getBids());

        holder.proj_title.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DescFragment(dataHolder.get(position).getTitle(), dataHolder.get(position).getDesc(), dataHolder.get(position).getBudget(), dataHolder.get(position).getBids(),dataHolder.get(position).getPostId())).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView proj_title, proj_budget, proj_bids;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            proj_title = itemView.findViewById(R.id.proj_title);
            proj_budget = itemView.findViewById(R.id.proj_budget);
            proj_bids = itemView.findViewById(R.id.proj_bids);

        }
    }
}