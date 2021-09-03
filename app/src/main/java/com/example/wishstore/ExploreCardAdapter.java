package com.example.wishstore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class ExploreCardAdapter extends FirebaseRecyclerAdapter<PostsHelper, ExploreCardAdapter.ExplorecardViewHolder> {

    public ExploreCardAdapter(@NonNull FirebaseRecyclerOptions<PostsHelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExploreCardAdapter.ExplorecardViewHolder holder, int position, @NonNull PostsHelper model) {
//        Log.d("post key", getRef(position).getKey());
        holder.proj_title.setText(model.getTitle());
        holder.proj_budget.setText(model.getBudget());
        holder.proj_bids.setText(model.getBids());

        holder.proj_title.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreDescFragment(model.getTitle(), model.getDesc(), model.getBudget(), model.getUserId(),getRef(position).getKey(),model.getBids())).addToBackStack(null).commit();
        });
    }

    @NonNull

    @Override
    public ExplorecardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card,parent,false);
        return new ExploreCardAdapter.ExplorecardViewHolder(view);
    }

    public static class ExplorecardViewHolder extends RecyclerView.ViewHolder {
        TextView proj_title, proj_budget, proj_bids;


        public ExplorecardViewHolder(@NonNull View itemView) {
            super(itemView);

            proj_title = itemView.findViewById(R.id.proj_title);
            proj_budget = itemView.findViewById(R.id.proj_budget);
            proj_bids = itemView.findViewById(R.id.proj_bids);
        }
    }
}
