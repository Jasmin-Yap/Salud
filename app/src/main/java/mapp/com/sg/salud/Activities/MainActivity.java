package mapp.com.sg.salud.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mapp.com.sg.salud.R;
import mapp.com.sg.salud.model.userData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etUID;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUID = (EditText) findViewById(R.id.etUID);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = firebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);
    }

    protected void loginUser() {
        final String user = etUID.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this,"Please enter your email.",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT);
            return;
        }
        progressDialog.setMessage("Logging in....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            userData userData = new userData();
                            userData.setUserEmail(user);
                            userData.setUserId(firebaseAuth.getCurrentUser().getUid());
                            startActivity(new Intent(getApplicationContext(), Nav.class));
                        } else {
                            // display same message here
                            Toast.makeText(MainActivity.this,"Wrong email/password. Please try again.",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }



    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            loginUser();
        }
        if (view == btnSignUp) {
            startActivity(new Intent(this,signUp.class));
        }
        Intent i;
        switch (view.getId()) {
            case R.id.btnSignUp:
                i = new Intent(this, signUp.class);
                startActivity(i);
                break;
            case R.id.btnLogin:
                loginUser();
                break;
            case R.id.btnLoginOthers:
                Toast toast = Toast.makeText(this,"Function not available at the moment", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
