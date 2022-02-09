package com.example.cuetjobnetwork.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuetjobnetwork.R;
import com.example.cuetjobnetwork.model.JobInterface;
import com.example.cuetjobnetwork.model.JobPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<JobPost, MyAdapter.MyViewHolder> {
    JobInterface jobInterface;
    public MyAdapter(@NonNull FirebaseRecyclerOptions<JobPost> options, JobInterface jobInterface) {
        super(options);
        this.jobInterface=jobInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull JobPost model) {
        holder.title_tv.setText(model.getJobTitle());
        holder.skill_tv.setText(model.getJobSkill());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jobInterface.onJobClick(model);

            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_tv, skill_tv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            skill_tv = itemView.findViewById(R.id.skill_tv);



        }
    }
}
