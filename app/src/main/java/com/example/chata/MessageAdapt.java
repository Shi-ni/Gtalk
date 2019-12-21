package com.example.chata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessageAdapt extends RecyclerView.Adapter<MessageAdapt.MessageViewHolder> {

    private List<ChatMessage> UserMessageList;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    public MessageAdapt (List<ChatMessage> UserMessageList){

        this.UserMessageList = UserMessageList;

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        public TextView senderMessageTest, receiverMessageText;


        public MessageViewHolder(@NonNull View itemView) {

            super(itemView);

            senderMessageTest = (TextView) itemView.findViewById(R.id.message_stext);
            receiverMessageText = (TextView) itemView.findViewById(R.id.message_text);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_message, parent, false);


        mAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        String messageSenderId = mAuth.getCurrentUser().getUid();
        ChatMessage message = UserMessageList.get(position);

        String fromUserID =  message.getName();
        String fromTime = message.getTime();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return UserMessageList.size();
    }

}
