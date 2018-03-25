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

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.sweetdeveloper.instacoffee.R;

import java.util.List;


public class AddressDialog extends DialogFragment {

    @NotEmpty
    EditText addressEditText;
    @NotEmpty
    EditText phoneEditText;

    Button okButton;
    Button cancelButton;

    Validator validator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adress_dialog, container, false);

        addressEditText = view.findViewById(R.id.order_dialog_address_edit_text);
        phoneEditText = view.findViewById(R.id.order_dialog_phone_edit_text);
        okButton = view.findViewById(R.id.order_dialog_ok_button);
        cancelButton = view.findViewById(R.id.order_dialog_cancel_button);

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

        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

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
}
