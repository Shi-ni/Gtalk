package com.example.chata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class find_firends extends AppCompatActivity {


    private Toolbar mToolbar;
    private RecyclerView friendRecyclerList;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_firends);


        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        friendRecyclerList = findViewById(R.id.friend_view);
        friendRecyclerList.setLayoutManager(new LinearLayoutManager(this));


        mToolbar = findViewById(R.id.friend_app_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Group Members");


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Friends> options =
                new FirebaseRecyclerOptions.Builder<Friends>()
                .setQuery(UserRef, Friends.class)
                .build();



        FirebaseRecyclerAdapter<Friends, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<Friends, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder findFriendViewHolder, int i, @NonNull Friends friends) {


                        findFriendViewHolder.Username.setText(friends.getName());
                        findFriendViewHolder.UserStatus.setText(friends.getStatus());
                     //   findFriendViewHolder.UserProfile.

                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent,false);

                   FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                   return viewHolder;
                    }
                };
        friendRecyclerList.setAdapter(adapter);

        adapter.startListening();
    }



    public static class FindFriendViewHolder extends RecyclerView.ViewHolder{

        TextView Username, UserStatus, UserProfile ;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);


            Username = itemView.findViewById(R.id.user_profile_name);
            UserStatus = itemView.findViewById(R.id.user_profile_status);
          //  UserProfile = itemView.findViewById(R.id.user_profile_image);
        }
    }
}
