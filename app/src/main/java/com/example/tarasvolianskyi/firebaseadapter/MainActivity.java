package com.example.tarasvolianskyi.firebaseadapter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";

    EditText editTextUser;
    Button btnAddUser;
    Spinner spinnerCategoryOfUsers;

    DatabaseReference databaseReferenceUsers;
    ListView listViewUsers;
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
        listViewUsers = (ListView) findViewById(R.id.lv_data_of_users_from_firebase_main_activity);
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
                UserList adapterForUser = new UserList(MainActivity.this, usersPojoList);
                listViewUsers.setAdapter(adapterForUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    /*   listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UsersPojo usersPojo = usersPojoList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddOptionActivity.class);
                intent.putExtra(USER_ID, usersPojo.getUserId());
                intent.putExtra(USER_NAME, usersPojo.getUserName());
                startActivity(intent);
            }
        });*/

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UsersPojo usersPojo = usersPojoList.get(position);
                showUpdateDialog(usersPojo.getUserId(), usersPojo.getUserName());
                return false;
            }
        });

    }


    private void showUpdateDialog(final String userId, String userName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_user_dialog, null);
        dialogBuilder.setView(dialogView);
        //final TextView textViewName = (TextView) dialogView.findViewById(R.id.tv_name_of_user_update_dialog);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.et_new_name_of_user_update_dialog);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btn_click_for_update_user_update_dialog);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btn_click_for_delete_user_update_dialog);
        final Spinner spinnerCategoriesDialUptd = (Spinner) dialogView.findViewById(R.id.spinner_categories_of_user_update_dialog);
        dialogBuilder.setTitle("Updating of user" + userName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String category = spinnerCategoriesDialUptd.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Name required");
                    return;
                }
                updateUser(userId, name, category);
                alertDialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(userId);
            }
        });

    }

    private void deleteUser(String userId) {
        DatabaseReference dbrefUser = FirebaseDatabase.getInstance().getReference("user").child(userId);
        DatabaseReference dbrefOptions = FirebaseDatabase.getInstance().getReference("options").child("option_" + userId);
        dbrefUser.removeValue();
        dbrefOptions.removeValue();
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean updateUser(String id, String name, String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(id);
        UsersPojo usersPojo = new UsersPojo(id, name, category);
        databaseReference.setValue(usersPojo);
        Toast.makeText(this, "User update successfully", Toast.LENGTH_SHORT).show();
        return true;
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
