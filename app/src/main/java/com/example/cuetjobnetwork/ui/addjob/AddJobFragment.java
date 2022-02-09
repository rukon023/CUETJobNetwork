package com.example.cuetjobnetwork.ui.addjob;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cuetjobnetwork.R;

import com.example.cuetjobnetwork.model.JobPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AddJobFragment extends Fragment {
    private EditText job_title_et, company_et, salary_et, job_skill_et, job_descrip_et, job_loc_et;
    private Button post_job_btn;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

     View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addjob, container, false);
        job_title_et = view.findViewById(R.id.job_title_et);
        company_et = view.findViewById(R.id.company_et);
        salary_et = view.findViewById(R.id.salary_et);
        job_skill_et = view.findViewById(R.id.skill_et);
        job_descrip_et = view.findViewById(R.id.description_et);
        job_loc_et = view.findViewById(R.id.location_et);
        post_job_btn = view.findViewById(R.id.post_btn);

        post_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, posted_by, company, salary, skill, descrip, location, date, jobId;
                JobPost jobPost;

                title = job_title_et.getText().toString();
                posted_by = firebaseUser.getEmail().toString();
                System.out.println(posted_by);
                company = company_et.getText().toString();
                salary = salary_et.getText().toString();
                skill = job_skill_et.getText().toString();
                descrip = job_descrip_et.getText().toString();
                location = job_loc_et.getText().toString();

                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                date = myDateObj.format(myFormatObj);

                if (title.isEmpty() || company.isEmpty() || salary.isEmpty() || skill.isEmpty() ||
                        descrip.isEmpty() || location.isEmpty()) {
                    Toast.makeText(getActivity(), "Empty Credentials",
                            Toast.LENGTH_SHORT).show();
                }
                //jobPost = new JobPost(title, posted_by, company, salary, skill, descrip, location, date);
                jobPost = new JobPost(company, date, descrip, location, skill, title, posted_by, salary);

                jobId = posted_by.replace("@student.cuet.ac.bd", "") + date.trim();
                System.out.println(jobId);
                mDatabase.child("jobposts").child(jobId).setValue(jobPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // ...
                        Toast.makeText(getContext(), "Job post added successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                mDatabase.child("jobposts").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        sendNotification();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return  view;
    }



    public void  sendNotification(){
        LocalDateTime myDateObj = LocalDateTime.now();
        //System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        //System.out.println("After formatting: " + formattedDate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n",
                    "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), "n")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("New Job Post Added")
                .setContentText("A new job post was added on "+formattedDate)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(requireActivity());
        manager.notify(101, builder.build());
    }
}