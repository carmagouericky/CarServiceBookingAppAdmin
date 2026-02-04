package com.example.carservicebookingappadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Button uploadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        uploadButton = view.findViewById(R.id.btn_upload_roster);

        uploadButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
