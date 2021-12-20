package ma.ensa.lkenach;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ma.ensa.lkenach.adapter.NotDoneTaskAdapter;
import ma.ensa.lkenach.model.ToDoModel;
import ma.ensa.lkenach.utils.DatabaseHandler;


public class MainActivity extends AppCompatActivity {
    EditText task;
    Button send;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cas du bouton
        send = findViewById(R.id.button);
        send.setOnClickListener(this::onClick);
        //cas des editText
        task = findViewById(R.id.editTextText);
        //cas du bouton d'ajout


    }

    public void showAllTasks(View view){
        startActivity(new Intent(this, AllTask.class));
    }

    public void showDoneTasks(View view){
        startActivity(new Intent(this, DoneTask.class));
    }

    public void showNotDoneTasks(View view){
        startActivity(new Intent(this, NotDoneTask.class));
    }


    public void onClick(View v){
        intent = new Intent(MainActivity.this,SearchTask.class);
        intent.putExtra("searchTask", task.getText().toString());
        if(task.getText().toString().isEmpty()){
            Toast.makeText(this,"Veuillez saisir quelque chose",Toast.LENGTH_SHORT).show();
        }
        else{
            startActivity(intent);
        }

    }
}