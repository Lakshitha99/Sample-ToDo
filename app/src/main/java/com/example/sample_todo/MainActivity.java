package com.example.sample_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private ListView listView;
    private TextView count;
    Context context;
    private DbHandler dbHandler;
    private List<ToDo> toDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        dbHandler = new DbHandler(context);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.todolist);
        count = findViewById(R.id.todocount);
        toDos = new ArrayList<>();

        toDos = dbHandler.getAllToDos();

        ToDoAdapter adapter = new ToDoAdapter(context,R.layout.single_todo,toDos);

        listView.setAdapter(adapter);

        //get todo counts from the table
        int countToDo = dbHandler.countToDo();
        count.setText("you have  "+countToDo+" todos");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,AddToDo.class));
            }
        });
    }
}
