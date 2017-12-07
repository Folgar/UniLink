package com.example.uqac.unilink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Lorane on 03/12/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignUpActivity";

    //defining views
    private Button buttonSignUp;
    private EditText editTextDisplayName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    private CallbackManager callbackManager;

    private TextView connexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextDisplayName = (EditText) findViewById(R.id.editTextDisplayName);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        connexion = (TextView) findViewById(R.id.connexion);

        Intent intent = getIntent();
        editTextEmail.setText(intent.getStringExtra("email"));
        editTextPassword.setText(intent.getStringExtra("password"));
        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonSignUp.setOnClickListener(this);
        connexion.setOnClickListener(this);

    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String displayName = editTextDisplayName.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Veuillez entrer un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Veuillez entrer un mot de passe",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(displayName)){
            Toast.makeText(this,"Veuillez entrer un nom d'utilisateur",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email, password and displayName are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Enregistrement en cours...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName).build();

                            user.updateProfile(profileUpdates);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(SignUpActivity.this,"Erreur lors de l'inscription",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignUp){
            registerUser();
        }
        else if(view == connexion){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
