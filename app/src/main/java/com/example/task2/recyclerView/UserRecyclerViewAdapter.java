package com.example.task2.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task2.R;
import com.example.task2.User.User;
import com.example.task2.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
    List<User>  users = new ArrayList<>();
    ItemBinding binding;
    public  UserRecyclerViewAdapter(List<User> users){
        this.users = users;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        binding.firstName.setText(user.firstname);
        binding.lastName.setText(user.lastname);
        binding.City.setText(user.city);
        binding.Country.setText(user.country);
        Glide.with(holder.itemView).load(user.img).apply(new RequestOptions().placeholder(R.drawable.placeholderpic).error(R.drawable.errorpic)).into(binding.profilePicture);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
