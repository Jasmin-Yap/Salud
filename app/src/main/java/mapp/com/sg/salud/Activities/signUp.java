package mapp.com.sg.salud.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import mapp.com.sg.salud.R;

/**
 * Created by Jasmin on 15/5/2018.
 */

public class signUp extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEditText;
    private EditText passEditText;
    private EditText pass2EditText;
    private EditText emailEditText;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CheckBox TACcheckbox;

    DatabaseReference firebaseDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Initialising views
        firebaseAuth = FirebaseAuth.getInstance();
        nameEditText = (EditText) findViewById(R.id.nameET);
        passEditText = (EditText) findViewById(R.id.passET);
        pass2EditText = (EditText) findViewById(R.id.pass2ET);
        emailEditText = (EditText) findViewById(R.id.emailET);
        btnSignUp = (Button) findViewById(R.id.createBtn);
        TACcheckbox = (CheckBox) findViewById(R.id.tacCb);
        progressDialog = new ProgressDialog(this);

        btnSignUp.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    }
    private void registerUser(){
        String name = nameEditText.getText().toString().trim();
        String pass = passEditText.getText().toString().trim();
        String pass2 = pass2EditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if(!TACcheckbox.isChecked()) {
            Toast.makeText(this,"You must agree to the terms and conditions before proceeding.",Toast.LENGTH_SHORT).show();
            return ;
        }

        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this,"Please enter a name.",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(this,"Please enter a password.",Toast.LENGTH_SHORT).show();
            return ;
        }
        if (!pass.equals(pass2)) {
            Toast.makeText(this,"Passwords do not match. Please try again.",Toast.LENGTH_SHORT).show();
            return ;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please enter an email.",Toast.LENGTH_SHORT).show();
            return ;
        }
        progressDialog.setMessage("Registering user please wait....");
        progressDialog.show();

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("Firebase","connected");
                } else {
                    Log.e("Firebase","not connected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            String name = nameEditText.getText().toString().trim();
                            String phone = "";
                            String email = emailEditText.getText().toString().trim();

                            Map<String, Object> newPost = new HashMap<String, Object>();
                            newPost.put("name",name);
                            newPost.put("phoneNumber",phone);
                            newPost.put("email",email);

                            firebaseDatabase.child("Users").child(user_id).setValue(newPost).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Firebase", "Data saved!");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Firebase", "Data failed to save");
                                }
                            });
                            Toast.makeText(signUp.this,"Registered successfully.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signUp.this,MainActivity.class));
                            progressDialog.hide();
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                passEditText.setError(getString(R.string.ERROR_WEAK_PASSWORD));
                                passEditText.requestFocus();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                emailEditText.setError(getString(R.string.ERROR_INVALID_EMAIL));
                                emailEditText.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e) {
                                emailEditText.setError(getString(R.string.ERROR_USER_EXIST));
                                emailEditText.requestFocus();
                            } catch(Exception e) {
                                Toast.makeText(signUp.this,e.getMessage(),Toast.LENGTH_LONG);
                            }
                            Log.d("Firebase :","Error",task.getException());
                            Toast.makeText(signUp.this,"Something went wrong,Please try again",Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == btnSignUp){
            registerUser();
//            startActivity(new Intent(this,MainActivity.class));
        }
//        Intent i;
//        switch (view.getId()) {
//            case R.id.createBtn:
//                i = new Intent(this, MainActivity.class);
//                startActivity(i);
//                break;
//        }
    }
}
