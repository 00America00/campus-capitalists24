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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.expense_list_item, expenses) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View itemView = convertView;
                                if (itemView == null) {
                                        itemView = getLayoutInflater().inflate(R.layout.expense_list_item, parent, false);
                                }

                                // Get the current expense string
                                String expenseString = getItem(position);

                                // Parse the expense data from the CSV string
                                String[] parts = expenseString.split(",");
                                String id = parts[0];
                                String tid = parts[1];
                                String date = parts[2];
                                double amount = Double.parseDouble(parts[3]);
                                String category = parts[4];
                                String note = parts[5];

                                // Find the TextViews in the layout
                                TextView dateTextView = itemView.findViewById(R.id.dateTextView);
                                TextView amountTextView = itemView.findViewById(R.id.amountTextView);
                                TextView categoryTextView = itemView.findViewById(R.id.categoryTextView);
                                TextView notesTextView = itemView.findViewById(R.id.notesTextView);

                                // Set the text for each TextView with labels
                                dateTextView.setText("Date: " + date);
                                amountTextView.setText("Amount: $" + amount);
                                categoryTextView.setText("Category: " + category);
                                notesTextView.setText("Notes: " + note);

                                return itemView;
                        }
                };
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
                                // Hide the button
                                addButton.setVisibility(View.GONE);

                                // Create newExpenseFragment and add it as a child fragment
                                newExpenseFragment fragment = new newExpenseFragment();
                                getChildFragmentManager().beginTransaction()
                                        .add(R.id.fragment_container, fragment)
                                        .addToBackStack("expenseList")
                                        .commit();
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
                        } catch (IOException e) {
                                Toast.makeText(requireContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }
                return lines;
        }

}