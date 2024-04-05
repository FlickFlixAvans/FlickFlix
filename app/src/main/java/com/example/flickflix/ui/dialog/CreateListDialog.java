package com.example.flickflix.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;

import com.example.flickflix.R;
import com.example.flickflix.data.SharedPreferencesManager;
import com.example.flickflix.viewmodel.ListViewModel;

public class CreateListDialog extends AlertDialog {
    private EditText editTextName;
    private EditText editTextDescription;
    ListViewModel listViewModel;

    public CreateListDialog(@NonNull Context context, ListViewModel listViewModel) {
        super(context);
        this.listViewModel = listViewModel;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.create_list_dialog, null);
        setView(dialogView);

        editTextName = dialogView.findViewById(R.id.et_name);
        editTextDescription = dialogView.findViewById(R.id.et_description);

        setTitle("Lijst aanmaken");

        setButton(BUTTON_POSITIVE, "Aanmaken", null, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createList();
            }
        });

        setButton(BUTTON_NEGATIVE, "Annuleren", null, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
    }

    private void createList() {
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();

        // Add the access token to the header
        SharedPreferencesManager manager = new SharedPreferencesManager(getContext());
        String accessToken = manager.getAccessToken();

        String authorization = "Bearer " + accessToken;

        listViewModel.createList(name, description, authorization).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                Log.d("DSAdasD", "Succ: "+String.valueOf(success));

                if (success) {
                    Toast.makeText(getContext(), "Successfully created list!", Toast.LENGTH_SHORT).show();

                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed to create list!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
