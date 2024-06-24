package com.example.task2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.Room.UserDataBase;
import com.example.task2.Room.UsersTable;
import com.example.task2.User.User;
import com.example.task2.databinding.ActivityUsersBinding;
import com.example.task2.recyclerView.UserRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    ActivityUsersBinding binding;
    List<User> usersList=new ArrayList<>();
    UserDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.usersList.setHasFixedSize(true);
        binding.usersList.setLayoutManager(new LinearLayoutManager(this));

        //get the data from Room
        db=UserDataBase.getInstance(this);
        List<UsersTable> usersTableList=db.userDao().getAllUsers();
        for(UsersTable user:usersTableList){
            User user1=new User();
            user1.firstname=user.firstname;
            user1.lastname=user.lastname;
            user1.city=user.city;
            user1.country=user.country;
            user1.img=user.img;
            usersList.add(user1);
        }
        //see the data in the recycler view

        UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(usersList);
        binding.usersList.setAdapter(adapter);
    }
}