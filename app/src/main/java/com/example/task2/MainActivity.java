package com.example.task2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task2.Room.UserDataBase;
import com.example.task2.Room.UsersTable;
import com.example.task2.User.Root;
import com.example.task2.User.User;
import com.example.task2.User.UserAPIClient;
import com.example.task2.User.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView firstName,lastName,Age,Email,City,Country;
    Button nextUserButton, addThisUserToCollection,ViewCollection;
    ImageView profilePicture;
    User myUser;
    UserDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nextUserButton = findViewById(R.id.nextUserButton);
        addThisUserToCollection = findViewById(R.id.AddThisUserToCollection);
        ViewCollection = findViewById(R.id.ViewCollection);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        Age = findViewById(R.id.Age);
        Email = findViewById(R.id.Email);
        City = findViewById(R.id.City);
        Country = findViewById(R.id.Country);
        profilePicture = findViewById(R.id.profilePicture);

        nextUserButton.setOnClickListener(v -> {
            nextUserButton.setEnabled(false);
            addThisUserToCollection.setEnabled(false);
            ViewCollection.setEnabled(false);

            Retrofit retrofit = UserAPIClient.getClient();
            UserService service = retrofit.create(UserService.class);
            Call<Root> callAsync = service.getUsers(null, null, null);

            callAsync.enqueue(new Callback<Root>() {
                @Override
                public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                    Root user = response.body();
                    assert user != null;
                    firstName.setText(user.results.get(0).name.first);
                    lastName.setText(user.results.get(0).name.last);
                    Age.setText(String.valueOf(user.results.get(0).dob.age));
                    Email.setText("Email: "+user.results.get(0).email);
                    City.setText("City: "+user.results.get(0).location.city);
                    Country.setText("Country: "+user.results.get(0).location.country);
                    Glide.with(MainActivity.this).load(user.results.get(0).picture.large).apply(new RequestOptions().placeholder(R.drawable.placeholderpic).error(R.drawable.errorpic)).into(profilePicture);
                    myUser = new User(user.results.get(0).login.uuid, user.results.get(0).name.first, user.results.get(0).name.last, user.results.get(0).email, user.results.get(0).dob.age, user.results.get(0).location.city, user.results.get(0).location.country, user.results.get(0).picture.large);
                    nextUserButton.setEnabled(true);
                    addThisUserToCollection.setEnabled(true);
                    ViewCollection.setEnabled(true);
                }
                @Override
                public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                    firstName.setText("Error");
                    lastName.setText("Error");
                    Age.setText("Error");
                    Email.setText("Error");
                    City.setText("Error");
                    Country.setText("Error");
                    Glide.with(MainActivity.this).load(R.drawable.errorpic).apply(new RequestOptions().placeholder(R.drawable.placeholderpic).error(R.drawable.errorpic)).into(profilePicture);
                    myUser = new User("Error", "Error", "Error", "Error", 0, "Error", "Error", "Error");
                    nextUserButton.setEnabled(true);
                    addThisUserToCollection.setEnabled(true);
                    ViewCollection.setEnabled(true);
                }
            });
        });

        addThisUserToCollection.setOnClickListener(v -> {
            if(myUser.id.equals("Error")){
                Toast.makeText(MainActivity.this, "Error in adding user to collection", Toast.LENGTH_SHORT).show();
                return;
            }
            db = UserDataBase.getInstance(this);
            UsersTable user = new UsersTable();
            user.uid = myUser.id;
            user.firstname = myUser.firstname;
            user.lastname = myUser.lastname;
            user.city = myUser.city;
            user.country = myUser.country;
            user.img = myUser.img;
            db.userDao().insertUser(user);
//            db.userDao().deleteAllUsers();
            Toast.makeText(MainActivity.this, "User added to collection", Toast.LENGTH_SHORT).show();
        });

        ViewCollection.setOnClickListener(v -> {
            db = UserDataBase.getInstance(this);
            if(db.userDao().getAllUsers().isEmpty()){
                Toast.makeText(MainActivity.this, "Collection is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            //move tp next activity
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        nextUserButton.performClick();
    }
}