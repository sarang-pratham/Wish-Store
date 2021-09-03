package com.example.wishstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PropCardAdapter extends RecyclerView.Adapter<PropCardAdapter.viewHolder> {
    ArrayList<UserHelper> userList;

    public PropCardAdapter(ArrayList<UserHelper> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_card, parent, false);
        return new PropCardAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropCardAdapter.viewHolder holder, int position) {
        holder.name.setText(userList.get(position).getUsername());
        holder.email.setText(userList.get(position).getEmail());
        holder.phone.setText(userList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class viewHolder extends RecyclerView.ViewHolder{
        TextView name, email, phone;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.u_name);
            email= itemView.findViewById(R.id.u_email);
            phone = itemView.findViewById(R.id.u_phone);
        }
    }
}
