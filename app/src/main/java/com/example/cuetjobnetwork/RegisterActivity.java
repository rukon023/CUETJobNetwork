package com.example.cuetjobnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuetjobnetwork.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText fname_et, lname_et, id_et, dept_et, addr_et, email_et, pass_et, pass_conf_et;
    private Button signup_btn;
    private String fname, lname,  cuet_id, dept, addr, email, passwd, passwd_conf;
    private FirebaseAuth auth;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Sign Up");
        fname_et = findViewById(R.id.fname_et);
        lname_et = findViewById(R.id.lname_et);
        id_et = findViewById(R.id.cuetid_et);
        dept_et = findViewById(R.id.cuet_dept);
        addr_et = findViewById(R.id.address_et);
        email_et = findViewById(R.id.email_et);
        pass_et = findViewById(R.id.password_et);
        pass_conf_et = findViewById(R.id.pass_conf);
        signup_btn = findViewById(R.id.signup_btn);

        auth = FirebaseAuth.getInstance();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = fname_et.getText().toString();
                lname = lname_et.getText().toString();
                cuet_id = id_et.getText().toString();
                dept = dept_et.getText().toString();
                addr = addr_et.getText().toString();
                email = email_et.getText().toString();
                passwd = pass_et.getText().toString();
                passwd_conf = pass_conf_et.getText().toString();
                User user = new User(fname, lname, cuet_id, dept, addr, email);
                if (fname.isEmpty() || lname.isEmpty() || cuet_id.isEmpty() || dept.isEmpty() ||
                        addr.isEmpty() || email.isEmpty() || passwd.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else if (!email.endsWith("cuet.ac.bd")){
                    email_et.setError("Use CUET Email to Signup");
                    Toast.makeText(RegisterActivity.this, "Please Use CUET Email to signup!", Toast.LENGTH_SHORT).show();

                }
                else if (passwd.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                    System.out.println(passwd);
                }
                else if (!passwd.equals(passwd_conf)){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
//                    System.out.println(passwd);
//                    System.out.println(passwd_conf);
                }
                else{
                    registerUser(user, email, passwd);
                }

            }

            private void registerUser(User user, String email, String passwd) {
                String userId;
                auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(RegisterActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful!",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // save to database
                userId = email.replace("@student.cuet.ac.bd", ""); // u1604012
                database.child("users").child(userId).setValue(user);
            }


        });
    }


}