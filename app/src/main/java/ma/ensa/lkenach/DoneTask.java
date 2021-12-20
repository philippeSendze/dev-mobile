package ma.ensa.lkenach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ma.ensa.lkenach.adapter.AllTaskAdapter;
import ma.ensa.lkenach.adapter.DoneTaskAdapter;
import ma.ensa.lkenach.model.ToDoModel;
import ma.ensa.lkenach.utils.DatabaseHandler;

public class DoneTask extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private DoneTaskAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tast);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this );
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new DoneTaskAdapter(db,this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getDoneTasks();
        taskList.iterator();
        Collections.reverse(taskList);
        List <ToDoModel> show = new ArrayList<ToDoModel>();
        int n = taskList.size();
        for (int i=0;i<n;i++){
            if(taskList.get(i).getStatus()==1){
                show.add(taskList.get(i));
            }
        }

        tasksAdapter.setTasks(show);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

    }

    @Override
    public void hundleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }
}