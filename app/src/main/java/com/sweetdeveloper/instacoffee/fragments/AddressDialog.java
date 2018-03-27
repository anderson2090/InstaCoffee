package com.sweetdeveloper.instacoffee.fragments;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.WelcomeActivity;
import com.sweetdeveloper.instacoffee.models.DBOrder;

import java.util.List;

import static com.sweetdeveloper.instacoffee.utils.Cart.getTotal;
import static com.sweetdeveloper.instacoffee.utils.Cart.orders;


public class AddressDialog extends DialogFragment {

    @NotEmpty
    EditText addressEditText;
    @NotEmpty
    EditText phoneEditText;

    Button okButton;
    Button cancelButton;

    Validator validator;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adress_dialog, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


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
                placeOrder();
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

    public void placeOrder() {

        DBOrder dbOrder = new DBOrder(user.getDisplayName(),
                user.getEmail(),
                phoneEditText.getText().toString(),
                addressEditText.getText().toString(),
                getTotal(orders) + "",
                orders);
        if (orders.size() > 0) {
            databaseReference = firebaseDatabase.getReference("pending_orders").child(firebaseAuth.getCurrentUser().getUid());
            databaseReference.setValue(dbOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), getString(R.string.order_placed), Toast.LENGTH_SHORT).show();
                        orders.clear();
                        startActivity(new Intent(getActivity(), WelcomeActivity.class));
                        dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();
        }
    }
}
