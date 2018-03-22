package com.sweetdeveloper.instacoffee.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.interfaces.ProgressBarListener;

import java.util.List;


public class ChangePasswordDialogFragment extends DialogFragment {

    @NotEmpty
    @Password
    EditText passwordEditText;

    @NotEmpty
    @ConfirmPassword
    EditText confirmPasswordEditText;

    Button okButton;
    Button cancelButton;

    Validator validator;

    ProgressBarListener progressBarListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chane_password_dialog_fragment, container, false);
        progressBarListener = (ProgressBarListener) getActivity();
        validator = new Validator(this);
        passwordEditText = view.findViewById(R.id.change_password_enter_password);
        confirmPasswordEditText = view.findViewById(R.id.change_password_confirm_password);
        cancelButton = view.findViewById(R.id.change_password_cancel_button);

        okButton = view.findViewById(R.id.change_password_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validator.validate();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                progressBarListener.displayProgressBar();
                updatePassword();


            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getActivity());

                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public void updatePassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), getString(R.string.password_has_been_changed), Toast.LENGTH_LONG).show();
                                progressBarListener.hideProgressBar();
                                dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    progressBarListener.hideProgressBar();
                    dismiss();

                }
            });
        }
    }


}
