package com.example.midexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<StudentModel> students;
    Context context;
    DBHelper dbHelper;

    public StudentAdapter(List<StudentModel> students, Context context, DBHelper dbHelper) {
        this.students = students;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final StudentModel studentModel = students.get(position);
        holder.textViewID.setText("Student ID: " + studentModel.getStudentId());
        holder.editTextName.setText(studentModel.getName());
        holder.seekBarAge.setProgress(studentModel.getAge() - StudentModel.MIN_AGE);
        holder.textViewAge.setText("Student Age: " + studentModel.getAge());

        holder.btnEdit.setOnClickListener(view -> {
            String name = holder.editTextName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(context, "Enter data", Toast.LENGTH_SHORT).show();
            } else {
                studentModel.setName(name);
                studentModel.setAge(holder.seekBarAge.getProgress() + StudentModel.MIN_AGE);
                dbHelper.updateStudent(studentModel);
                Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) {
                return;
            }
            dbHelper.deleteStudent(studentModel.getId());
            students.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewAge;
        EditText editTextName;
        SeekBar seekBarAge;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            editTextName = itemView.findViewById(R.id.editTextName);
            seekBarAge = itemView.findViewById(R.id.seekBarAge);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
