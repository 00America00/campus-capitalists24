package com.example.campus_capitalists24;

import static android.content.Intent.getIntent;
import static android.content.Intent.parseUri;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private AssetManager assets;
    private Accounts profileInfo;

    TextView nameTextView;
    TextView emailTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        nameTextView = view.findViewById(R.id.welcome);
        emailTextView = view.findViewById(R.id.home1);

        setupProfile();
        // Inflate the layout for this fragment
        return view;
    }

    private void setupProfile() {
        assets = getResources().getAssets();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assets.open("accounts.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                // Assuming arr[0] is the ID, arr[1] is the name, and arr[2] is the email
                profileInfo = new Accounts(Integer.parseInt(arr[0]), arr[1], arr[2]);
                break; // We assume there's only one matching ID
            }
            reader.close();
        } catch (IOException e) {
            Log.e("HomeFragment", "Error reading accounts.txt: " + e.getMessage());
        }

        if (profileInfo != null) {
            // Update TextViews with profile info
            nameTextView.setText(profileInfo.getName());
            emailTextView.setText(profileInfo.getEmail());
        }
    }
}