package ma.ensa.lkenach;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ma.ensa.lkenach.AddNewTask;
import ma.ensa.lkenach.DialogCloseListener;
import ma.ensa.lkenach.R;
import ma.ensa.lkenach.RecyclerItemTouchHelper;
import ma.ensa.lkenach.adapter.DoneTaskAdapter;
import ma.ensa.lkenach.model.ToDoModel;
import ma.ensa.lkenach.utils.DatabaseHandler;

public class SearchTask extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private DoneTaskAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Bundle b;

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

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        List <ToDoModel> show = new ArrayList<ToDoModel>();

        b = getIntent().getExtras();
        String s = b.getString("searchTask");
        System.out.println(s);

        int n = taskList.size();
        for (int i=0;i<n;i++){
            if(taskList.get(i).getTask().contains(s)){
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
