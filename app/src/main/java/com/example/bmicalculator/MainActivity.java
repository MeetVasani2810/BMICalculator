package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Fixing Window Insets issue
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets; // ✅ Fix: Return insets instead of "Insets"
        });

        // Initializing UI elements
        EditText etHeight = findViewById(R.id.etHeight); // ✅ Fix: Correct ID
        EditText etWeight = findViewById(R.id.etWeight); // ✅ Fix: Correct ID
        Button btnSubmit = findViewById(R.id.btnSubmit); // ✅ Fix: Correct ID
        TextView tvResult = findViewById(R.id.tvResult); // ✅ Fix: Correct ID

        // Adding click listener for BMI calculation
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Convert input to float
                    float height = Float.parseFloat(etHeight.getText().toString().trim()) / 100;
                    float weight = Float.parseFloat(etWeight.getText().toString().trim());

                    // Validate input
                    if (height <= 0 || weight <= 0) {
                        tvResult.setText("❌ Enter valid height & weight!");
                        return;
                    }

                    // Convert height from cm to meters if needed
                    if (height > 3) height /= 100;

                    // Calculate BMI
                    float bmi = weight / (height * height);

                    // Determine category
                    String category;
                    if (bmi < 18.5) {
                        category = "🔵 Underweight";
                    } else if (bmi < 25) {
                        category = "🟢 Normal weight";
                    } else if (bmi < 30) {
                        category = "🟠 Overweight";
                    } else {
                        category = "🔴 Obese";
                    }

                    // Display result
                    tvResult.setText(String.format("📊 BMI: %.2f\n%s", bmi, category));

                } catch (NumberFormatException e) {
                    tvResult.setText("❌ Please enter valid numbers!");
                }
            }
        });
    }
}
