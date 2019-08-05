package com.android.hangman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.hangman.R;
import com.android.hangman.domain.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class UsersRankingAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    private static final String POINTS_ABBREVIATION = "pkt";
    private static final String SPACE = " ";

    private ArrayList<User> users;
    private Context context;


    public UsersRankingAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_user_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position) {
        usersViewHolder.getUsernameTextView().setText(String.valueOf(users.get(position).getUserName()));
        usersViewHolder.getPointsTextView().setText(users.get(position).getPoints( ) + SPACE + POINTS_ABBREVIATION);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}