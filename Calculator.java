package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator;
    private double firstOperand;
    private boolean startNewNumber;

    public Calculator() {
        setTitle("Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 28));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        operator = "";
        firstOperand = 0;
        startNewNumber = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if ("/*-+".contains(command)) {
            firstOperand = Double.parseDouble(display.getText());
            operator = command;
            startNewNumber = true;
        } else if ("=".equals(command)) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = 0;

            try {
                switch (operator) {
                    case "/":
                        if (secondOperand == 0) {
                            display.setText("Error");
                            startNewNumber = true;
                            return;
                        }
                        result = firstOperand / secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                }
            } catch (Exception ex) {
                display.setText("Error");
                startNewNumber = true;
                return;
            }

            display.setText(String.valueOf(result));
            startNewNumber = true;
        } else if ("C".equals(command)) {
            display.setText("");
            operator = "";
            firstOperand = 0;
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
