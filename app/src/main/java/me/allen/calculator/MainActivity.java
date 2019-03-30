package me.allen.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;

import me.allen.calculator.process.CalculateProcess;
import me.allen.calculator.util.LayoutUtil;
import me.allen.calculator.util.NumberUtil;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private CalculateProcess process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = findViewById(R.id.outputBox);
        Log.d("[Debug]", "Initializing... ");
        initButtons();
    }

    private void initButtons() {
        this.process = new CalculateProcess();
        LayoutUtil.getViewsFromViewGroup(findViewById(R.id.layout), Button.class).forEach(button -> button.setOnClickListener(view -> {
            TextView textView = (TextView) view;
            String title = textView.getText().toString();

            if (NumberUtil.isNumber(title)) {
                this.process.addWholeNumber(Integer.parseInt(title));
            } else if (title.equalsIgnoreCase("=")) {
                this.process.submit();
            } else if (title.equalsIgnoreCase("CLEAR")) {
                this.process.clear();
            } else {
                this.process.addCalculateType(CalculateProcess.CalculateType.findBySymbol(title));
            }

            if (process.getFinalExpression() != null) {
                Expression expression = process.getFinalExpression();
                if (expression.validate(true).isValid()) {
                    double value = expression.evaluate();
                    this.textView.setText(String.valueOf(String.valueOf(value).endsWith(".0") ? String.valueOf(value).replace(".0", "") : value));
                } else {
                    this.textView.setText("Calculation not valid.");
                }
            } else {
                this.textView.setText(this.process.getOperationString());
            }
        }));

    }
}
