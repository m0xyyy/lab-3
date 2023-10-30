import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView calculatorScreen;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorScreen = findViewById(R.id.calculatorScreen);

        // Set click listeners for digit buttons (0-9)
        for (int i = 0; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("button_" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            setDigitButtonClick(button, String.valueOf(i));
        }

        // Set click listeners for operator buttons (+, -, *, /, =)
        setOperatorButtonClick(R.id.button_plus, "+");
        setOperatorButtonClick(R.id.button_minus, "-");
        setOperatorButtonClick(R.id.button_multiply, "*");
        setOperatorButtonClick(R.id.button_divide, "/");
        setEqualButtonClick(R.id.button_equal);

        // Set click listener for the clear button
        setClearButtonClick(R.id.button_clear);

        // Set click listener for the square root button
        setSquareRootButtonClick(R.id.button_sqrt);

        // Set click listener for the back button
        setBackButtonClick(R.id.button_back);

        // Set click listener for the sign change button
        setSignChangeButtonClick(R.id.button_sign_change);
    }

    private void setDigitButtonClick(Button button, final String digit) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput += digit;
                updateDisplay();
            }
        });
    }

    private void setOperatorButtonClick(int buttonId, final String op) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    if (!operator.isEmpty()) {
                        calculate();
                    }
                    firstValue = Double.parseDouble(currentInput);
                    operator = op;
                    currentInput = "";
                }
            }
        });
    }

    private void setEqualButtonClick(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    calculate();
                    operator = "";
                }
            }
        });
    }

    private void setClearButtonClick(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstValue = 0;
                updateDisplay();
            }
        });
    }

    private void setSquareRootButtonClick(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double inputValue = Double.parseDouble(currentInput);
                    if (inputValue >= 0) {
                        double result = Math.sqrt(inputValue);
                        currentInput = Double.toString(result);
                        updateDisplay();
                    } else {
                        // Handle negative input
                        currentInput = "Error";
                        updateDisplay();
                    }
                }
            }
        });
    }

    private void setBackButtonClick(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 0) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    updateDisplay();
                }
            }
        });
    }

    private void setSignChangeButtonClick(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                    if (currentInput.charAt(0) == '-') {
                        currentInput = currentInput.substring(1);
                    } else {
                        currentInput = "-" + currentInput;
                    }
                    updateDisplay();
                }
            }
        });
    }

    private void calculate() {
        double secondValue = Double.parseDouble(currentInput);
        switch (operator) {
            case "+":
                firstValue += secondValue;
                break;
            case "-":
                firstValue -= secondValue;
                break;
            case "*":
                firstValue *= secondValue;
                break;
            case "/":
                if (secondValue != 0) {
                    firstValue /= secondValue;
                } else {
                    // Handle division by zero error
                    currentInput = "Error";
                    updateDisplay();
                    return;
                }
                break;
        }
        currentInput = String.valueOf(firstValue);
        updateDisplay();
    }

    private void updateDisplay() {
        calculatorScreen.setText(currentInput);
    }
}
