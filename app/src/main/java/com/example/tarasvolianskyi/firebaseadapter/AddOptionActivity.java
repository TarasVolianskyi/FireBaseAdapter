package com.example.tarasvolianskyi.firebaseadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddOptionActivity extends AppCompatActivity {

    private TextView textViewOptionName;
    private EditText editTextOptionName;
    private SeekBar seekBarRating;
    private Button btnOption;
    private ListView listViewOptions;

    DatabaseReference databaseReferenceOption;
    List<OptionPojo> optionPojoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_option);

        initView();
    }

    private void initView() {
        textViewOptionName = (TextView) findViewById(R.id.tv_text_add_option_activity);
        editTextOptionName = (EditText) findViewById(R.id.et_text_for_entering_add_option_activity);
        seekBarRating = (SeekBar) findViewById(R.id.seek_bar_add_option_activity);
        btnOption = (Button) findViewById(R.id.btn_add_option_activity);
        listViewOptions = (ListView) findViewById(R.id.lv_data_of_options_from_firebase_main_activity);
        Intent intent = getIntent();
        optionPojoList = new ArrayList<>();
        String idOfOption = "option_" + intent.getStringExtra(MainActivity.USER_ID);
        String nameOfOption = intent.getStringExtra(MainActivity.USER_NAME);
        textViewOptionName.setText(nameOfOption);
        databaseReferenceOption = FirebaseDatabase.getInstance().getReference("options").child(idOfOption);

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOption();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceOption.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                optionPojoList.clear();
                for (DataSnapshot optionSnapshot : dataSnapshot.getChildren()) {
                    OptionPojo optionPojo = optionSnapshot.getValue(OptionPojo.class);
                    optionPojoList.add(optionPojo);
                }
                OptionList adapterForOption = new OptionList(AddOptionActivity.this, optionPojoList);
                listViewOptions.setAdapter(adapterForOption);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveOption() {
        String optionName = editTextOptionName.getText().toString().trim();
        int rationgOption = seekBarRating.getProgress();

        if (!TextUtils.isEmpty(optionName)) {
            String id = databaseReferenceOption.push().getKey();
            OptionPojo optionPojo = new OptionPojo(id, optionName, rationgOption);
            databaseReferenceOption.child(id).setValue(optionPojo);
            Toast.makeText(this, "Option saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Write option name", Toast.LENGTH_SHORT).show();
        }

    }
}
