package com.example.tarasvolianskyi.firebaseadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextUser;
    Button btnAddUser;
    Spinner spinnerCategoryOfUsers;

    DatabaseReference databaseReferenceUsers;
    ListView lvUsers;
    List<UsersPojo> usersPojoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("user");
        editTextUser = (EditText) findViewById(R.id.et_text_for_entering_main_activity);
        btnAddUser = (Button) findViewById(R.id.btn_add_user_main_activity);
        spinnerCategoryOfUsers = (Spinner) findViewById(R.id.spinner_categories_main_activity);
        usersPojoList = new ArrayList<>();
        lvUsers = (ListView) findViewById(R.id.lv_data_of_users_from_firebase_main_activity);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersPojoList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    UsersPojo usersPojo = userSnapshot.getValue(UsersPojo.class);
                    usersPojoList.add(usersPojo);
                }
                UserList adapter = new UserList(MainActivity.this, usersPojoList);
                lvUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addUser() {
        String name = editTextUser.getText().toString().trim();
        String category = spinnerCategoryOfUsers.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {
            String id = databaseReferenceUsers.push().getKey();
            UsersPojo usersPojo = new UsersPojo(id, name, category);
            databaseReferenceUsers.child(id).setValue(usersPojo);
        } else {
            Toast.makeText(this, "Write the name", Toast.LENGTH_SHORT).show();
        }

    }


}
