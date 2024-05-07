package com.example.campus_capitalists24;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //private AssetManager assets;
    private Accounts profileInfo;

    TextView nameTextView;
    TextView emailTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nameTextView = view.findViewById(R.id.welcome);
        emailTextView = view.findViewById(R.id.home1);

        if (getArguments() != null) {
            int id = getArguments().getInt("id", -1);
            setupProfile(id);
        }
        // Inflate the layout for this fragment
        return view;
    }

    public void setupProfile(int id) {
        // Construct the file path within the app's internal storage directory
        File file = new File(requireContext().getFilesDir(), "accounts.txt");

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (Integer.parseInt(data[0]) == id) {
                        profileInfo = new Accounts(id, data[1], data[2]);
                        break;
                    }
                }

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle error gracefully, maybe show a message to the user
        }

        // Update TextViews if profileInfo is not null
        if (profileInfo != null) {
            nameTextView.setText(profileInfo.getName());
            emailTextView.setText(profileInfo.getEmail());

        } else {
            // If profileInfo is null, handle the case where the profile data for the given ID is not found
            // For example, you can set default text or show an error message
            nameTextView.setText("Profile Not Found");
            emailTextView.setText("Profile Not Found");
        }
    }
}