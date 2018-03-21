package com.sweetdeveloper.instacoffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @NotEmpty
    @Email
    EditText emailEditText;
    @NotEmpty
    EditText userNameEditText;
    @NotEmpty
    @Password
    EditText passwordEditText;
    @NotEmpty
    @ConfirmPassword
    EditText confirmPasswordEditText;

    TextView orSignUpTextView;
    Button loginButton;

    Validator validator;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener fireBaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        fireBaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                }
            }
        };

        emailEditText = findViewById(R.id.email_edit_text);
        userNameEditText = findViewById(R.id.user_name_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        loginButton = findViewById(R.id.login_button);
        orSignUpTextView = findViewById(R.id.or_sign_up_text_view);
        orSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orSignUpTextView.getText().toString().equals(getString(R.string.or_sign_up))) {

                    loginButton.setText(getString(R.string.sign_up));
                    setHTMLText(orSignUpTextView, getString(R.string.or_login));

                    userNameEditText.setVisibility(View.VISIBLE);
                    confirmPasswordEditText.setVisibility(View.VISIBLE);

                } else {

                    loginButton.setText(getString(R.string.login));
                    setHTMLText(orSignUpTextView, getString(R.string.or_sign_up));

                    userNameEditText.setVisibility(View.GONE);
                    confirmPasswordEditText.setVisibility(View.GONE);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                if (loginButton.getText().toString().equals(getString(R.string.login))) {
                    informUserViaToast("Logging in...");
                    logIn();

                } else {
                    informUserViaToast("Signing up...");
                    createAccount();

                }
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getApplicationContext());

                    // Display error messages
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        informUserViaToast(message);
                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireBaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fireBaseAuthStateListener != null) {
            firebaseAuth.removeAuthStateListener(fireBaseAuthStateListener);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.or_sign_up), orSignUpTextView.getText().toString());
        outState.putString(getString(R.string.login), loginButton.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            setHTMLText(orSignUpTextView, savedInstanceState.getString(getString(R.string.or_sign_up)));
            loginButton.setText(savedInstanceState.getString(getString(R.string.login)));
            if (loginButton.getText().toString().equals(getString(R.string.sign_up))) {
                userNameEditText.setVisibility(View.VISIBLE);
                confirmPasswordEditText.setVisibility(View.VISIBLE);
            } else {
                userNameEditText.setVisibility(View.GONE);
                confirmPasswordEditText.setVisibility(View.GONE);
            }
        }
    }

    public void setHTMLText(TextView textView, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml("<u>" + text + "</u>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml("<u>" + text + "</u>"));
        }
    }

    public void createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),
                passwordEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                informUserViaToast(e.getLocalizedMessage());
            }
        });
    }

    public void logIn() {
        firebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(),
                passwordEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                informUserViaToast(e.getLocalizedMessage());
            }
        });
    }

    public void informUserViaToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
