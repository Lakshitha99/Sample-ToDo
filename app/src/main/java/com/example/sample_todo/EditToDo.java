package com.example.sample_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditToDo extends AppCompatActivity {

     private EditText title,des;
     private Button edit;
     private DbHandler dbHandler;
     private Context context;
     private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        context = this;
        dbHandler = new DbHandler(context);

        title = findViewById(R.id.editToDoTextTitle);
        des = findViewById(R.id.editToDoTextDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id =  getIntent().getStringExtra("id");
        ToDo todo = dbHandler.getSingleToDo(Integer.parseInt(id));

        title.setText(todo.getTitle());
        des.setText(todo.getDescription());

    }
}
