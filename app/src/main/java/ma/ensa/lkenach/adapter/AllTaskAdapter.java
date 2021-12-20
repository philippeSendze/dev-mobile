package ma.ensa.lkenach.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ensa.lkenach.AddNewTask;
import ma.ensa.lkenach.AllTask;
import ma.ensa.lkenach.NotDoneTask;
import ma.ensa.lkenach.R;
import ma.ensa.lkenach.model.ToDoModel;
import ma.ensa.lkenach.utils.DatabaseHandler;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.ViewHolder> {

    private List<ToDoModel> toDoModelList;
    private AllTask activity;
    private DatabaseHandler db;
    private NotDoneTask notDoneTask;

    public AllTaskAdapter(DatabaseHandler db, AllTask activity){
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = toDoModelList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                db.updateStatus(item.getId(), 1);
            }
            else {
                db.updateStatus(item.getId(), 0);
            }
        });
    }

    public int getItemCount(){
        return toDoModelList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<ToDoModel> toDoModelList){
        this.toDoModelList = toDoModelList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = toDoModelList.get(position);
        db.deleteTask(item.getId());
        toDoModelList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = toDoModelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
