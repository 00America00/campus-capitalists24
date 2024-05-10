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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BalanceFragment extends Fragment {

    private ListView listView;
    private TextView balanceView;
    private List<String> transactions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        listView = view.findViewById(R.id.fragment_transaction_list);
        balanceView = view.findViewById(R.id.textView12); // Ensure this TextView is in your layout

        // Load data from both files
        transactions = new ArrayList<>();
        transactions.addAll(readFromFile("expenses.txt"));
        transactions.addAll(readFromFile("MoneyIn.txt"));

        updateListView();
        updateBalance();

        return view;
    }

    private void updateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.expense_list_item, transactions) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = convertView;
                if (itemView == null) {
                    itemView = getLayoutInflater().inflate(R.layout.expense_list_item, parent, false);
                }

                String transaction = getItem(position);
                String[] parts = transaction.split(",");
                // Expect format: type,id,date,amount,category,note
                String date = parts[2];
                double amount = Double.parseDouble(parts[3]);
                String category = parts[4];

                TextView dateTextView = itemView.findViewById(R.id.dateTextView);
                TextView amountTextView = itemView.findViewById(R.id.amountTextView);
                TextView categoryTextView = itemView.findViewById(R.id.categoryTextView);

                dateTextView.setText("Date: " + date);
                amountTextView.setText("Amount: $" + amount);
                categoryTextView.setText("Category: " + category);

                return itemView;
            }
        };
        listView.setAdapter(adapter);
    }

    private List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(requireContext().getFilesDir(), fileName);
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Error reading file: " + fileName, Toast.LENGTH_SHORT).show();
        }
        return lines;
    }

    private void updateBalance() {
        double balance = 0;
        for (String transaction : transactions) {
            String[] parts = transaction.split(",");
            double amount = Double.parseDouble(parts[3]);
            if (parts[0].equals("expense")) {
                balance -= amount;
            } else if (parts[0].equals("income")) {
                balance += amount;
            }
        }
        balanceView.setText(String.format("$%.2f", balance));
    }
}
