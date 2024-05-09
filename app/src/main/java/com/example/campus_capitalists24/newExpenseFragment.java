package com.example.campus_capitalists24;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class newExpenseFragment extends Fragment {
    private static final String FILENAME = "/expenses.txt"; // File name
    private static final String DELIMITER = ","; // Delimiter

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_expense, container, false);
        setupButtons(view);
        return view;
    }

    private void setupButtons(View view) {
        Button button1 = (Button) view.findViewById(R.id.reg_exp_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int tid = -1;
                EditText dateInput = (EditText) view.findViewById(R.id.exp_date_input);
                EditText amountInput = (EditText) view.findViewById(R.id.exp_amount_input);
                EditText categoryInput = (EditText) view.findViewById(R.id.exp_category_input);
                EditText noteInput = (EditText) view.findViewById(R.id.exp_notes_input);

                if (validateExpenseInfo(view)) {
                    try {
                        // Retrieve or generate account ID
                        int accountId = 123; // Example account ID

                        // Retrieve last transaction ID from the file
                        tid = getLastExpenseId() + 1;

                        // Extract expense details
                        String date = dateInput.getText().toString();
                        double amount = Double.parseDouble(amountInput.getText().toString());
                        String category = categoryInput.getText().toString();
                        String note = noteInput.getText().toString().isEmpty() ? "No Note" : noteInput.getText().toString();

                        // Format the expense data
                        String expenseData = accountId + DELIMITER + tid + DELIMITER + date + DELIMITER + amount + DELIMITER + category + DELIMITER + note;

                        // Write data to file
                        writeToFile(expenseData);

                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        Fragment expenseListFragment = fragmentManager.findFragmentByTag("expenseList");
                        if (expenseListFragment != null && expenseListFragment.isVisible()) {
                            fragmentManager.popBackStack("expenseList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, new ExpenseListFragment())
                                    .addToBackStack("expenseList")
                                    .commit();
                        }

                        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        //fragmentManager.popBackStack("expenseList", FragmentManager.POP_BACK_STACK_INCLUSIVE); // Pops the back stack to go back to the previous fragment
                    } catch (IOException e) {
                        Toast.makeText(requireContext(), "IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dateInput.setText("");
                    amountInput.setText("");
                    categoryInput.setText("");
                    dateInput.setError("Must be in XX-XX-XXXX format");
                    amountInput.setError("Must be in $XX.XX format");
                    categoryInput.setError("Field must be filled out");
                }
            }
        });
    }

    private boolean validateExpenseInfo(View view) {
        EditText dateInput = (EditText) view.findViewById(R.id.exp_date_input);
        EditText amountInput = (EditText) view.findViewById(R.id.exp_amount_input);
        EditText categoryInput = (EditText) view.findViewById(R.id.exp_category_input);
        EditText noteInput = (EditText) view.findViewById(R.id.exp_notes_input);

        boolean isDateValid = dateInput.getText().toString().matches("(\\d{1,2}-\\d{1,2}-\\d{2,4})|(\\d{1,2}/\\d{1,2}/\\d{2,4})");
        boolean isAmountValid = amountInput.getText().toString().matches("\\d+[.,]\\d+");
        boolean isCategoryValid = !categoryInput.getText().toString().isEmpty();
        boolean isNoteValid = noteInput.getText().toString().isEmpty() || !noteInput.getText().toString().isEmpty();

        if (isDateValid && isAmountValid && isCategoryValid && isNoteValid) {
            return true;
        } else {
            return false; // Invalid
        }
    }

    private void writeToFile(String data) throws IOException {
        File file = new File(requireContext().getFilesDir().getAbsolutePath() + FILENAME);
        FileWriter fw = new FileWriter(file, true); // Append mode
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(data);
        bw.newLine();
        bw.close();
    }

    private int getLastExpenseId() throws IOException {
        File file = new File(requireContext().getFilesDir().getAbsolutePath() + FILENAME);
        if (!file.exists()) {
            return 0; // Return 0 if the file does not exist
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int maxId = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(DELIMITER);
            int tid = Integer.parseInt(parts[1]); // Expense ID is at index 1
            if (tid > maxId) {
                maxId = tid;
            }
        }

        br.close();
        return maxId;
    }

}