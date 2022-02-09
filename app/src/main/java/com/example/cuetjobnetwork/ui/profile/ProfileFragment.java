package com.example.cuetjobnetwork.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cuetjobnetwork.LoginActivity;
import com.example.cuetjobnetwork.R;
import com.example.cuetjobnetwork.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    TextView email_tv, user_tv, cuetid_tv, cuetdept_tv;
    EditText  addr_et;
    Button update_btn, delete_btn, logout_btn;
    FirebaseUser user;
    DatabaseReference reference;

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_tv = root.findViewById(R.id.user_name_tv);
        email_tv = root.findViewById(R.id.email_tv);
        cuetid_tv = root.findViewById(R.id.cuet_id_tv);
        cuetdept_tv = root.findViewById(R.id.cuet_dept_tv);
        addr_et = root.findViewById(R.id.addr_et);

        update_btn = (Button) root.findViewById(R.id.update_btn);
        delete_btn = (Button) root.findViewById(R.id.delete_btn);
        logout_btn = (Button) root.findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutUser();
            }
        });
        // delete user
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user);
            }
        });

        if(user.getEmail()!=null){
          String firstPart = user.getEmail().split("@")[0];
          loadUserData(firstPart);
        }

        return root;
    }

    private void deleteUser(FirebaseUser user) {
        String firstPart = user.getEmail().split("@")[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("Your account will be deleted permanently! This action can not be undone.");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference = FirebaseDatabase.getInstance().getReference();
                        //remove from rtdb
                        reference.child("users").child(firstPart).removeValue();
                        // sign out user
                        FirebaseAuth.getInstance().signOut();
                        // delete user
                        user.delete();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void logOutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Confirm Log Out");
        builder.setMessage("You will be returned to the Log in screen!");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getContext(), LoginActivity.class));
//        getActivity().finish();
    }

    private  void loadUserData(String emailfirstPart){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

       DatabaseReference ref= db.getReference("users").child(emailfirstPart);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user_tv.setText(user.getfName()+" "+user.getlName());
                email_tv.setText("Email: " + user.getEmail());
                cuetid_tv.setText("CUET ID: " + user.getCuetId());
                cuetdept_tv.setText("Department: " + user.getCuetDept());
                addr_et.setText(user.getAddress());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

    }


}