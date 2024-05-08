package com.example.campus_capitalists24;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class ExpenseListFragment extends Fragment {

        private ListView listView;
        private List<String> expenses;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_expenselist, container, false);

                listView = view.findViewById(R.id.listView);
                expenses = readFromFile(); // Read expense data from CSV file

                // Create an adapter to populate the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, expenses);
                listView.setAdapter(adapter);

                return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);

                Button addButton = view.findViewById(R.id.add_expense_button);
                addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.constraintLayout2, new newExpenseFragment());
                                transaction.commit();
                        }
                });
        }
        private List<String> readFromFile() {
                List<String> lines = new ArrayList<>();
                OutputStreamWriter w = null; //edit made
                File f = new File(requireContext().getFilesDir().getAbsolutePath() + "/expenses.txt");
                if(!f.exists()){
                        try {
                                //FileOutputStream fos = requireContext().openFileOutput("expenses.csv", Context.MODE_PRIVATE);
                                w = new OutputStreamWriter(requireContext().openFileOutput("expenses.txt", MODE_PRIVATE));
                                //fos.close();
                                w.close();
                        } catch (IOException ex) {
                                Toast.makeText(requireContext(), "Error creating expenses file", Toast.LENGTH_SHORT).show();
                        }

                }
                else {
                        try {
                                FileInputStream fis = requireContext().openFileInput("expenses.txt");
                                InputStreamReader isr = new InputStreamReader(fis);
                                BufferedReader br = new BufferedReader(isr);
                                String line;
                                while ((line = br.readLine()) != null) {
                                        lines.add(line);
                                }
                                br.close();
                /*}catch (FileNotFoundException e) {
                        // File does not exist, create it
                        try {
                                //FileOutputStream fos = requireContext().openFileOutput("expenses.csv", Context.MODE_PRIVATE);
                                w = new OutputStreamWriter(requireContext().openFileOutput("expenses.txt", MODE_PRIVATE));
                                //fos.close();
                                w.close();
                        } catch (IOException ex) {
                                Toast.makeText(requireContext(), "Error creating expenses file", Toast.LENGTH_SHORT).show();
                        }*/
                        } catch (IOException e) {
                                Toast.makeText(requireContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }

                if (lines.isEmpty()) {
                        // No expenses found
                        lines.add("No expenses added");
                }/*
                catch (IOException e) {
                        Toast.makeText(requireContext(), "IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }*/

                return lines;
        }

}