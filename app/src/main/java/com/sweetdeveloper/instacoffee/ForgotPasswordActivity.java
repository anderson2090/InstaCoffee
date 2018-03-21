package com.sweetdeveloper.instacoffee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class ForgotPasswordActivity extends RootActivity {

    @NotEmpty
    @Email
    EditText emailEditText;

    Button resetPasswordButton;
    Validator validator;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.reset_password_email_edit_text);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                firebaseAuth.sendPasswordResetEmail(emailEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    informUserViaToast(getString(R.string.reser_pw_instructions));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        informUserViaToast(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getApplicationContext());

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        informUserViaToast(message);
                    }
                }
            }
        });


    }
}
