package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private var isNewOp: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        val opButtons = listOf(
            R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { view ->
                onNumberClicked((view as Button).text.toString())
            }
        }

        opButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { view ->
                onOperatorClicked((view as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnEq).setOnClickListener { onEqualClicked() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClicked() }
    }

    private fun onNumberClicked(number: String) {
        if (isNewOp) {
            resultTextView.text = ""
            isNewOp = false
        }
        var currentText = resultTextView.text.toString()
        if (number == "." && currentText.contains(".")) return
        currentText += number
        resultTextView.text = currentText
    }

    private fun onOperatorClicked(op: String) {
        val value = resultTextView.text.toString().toDoubleOrNull()
        if (value != null) {
            operand1 = value
        }
        operator = op
        isNewOp = true
    }

    private fun onEqualClicked() {
        val value = resultTextView.text.toString().toDoubleOrNull()
        if (operand1 != null && value != null && operator != null) {
            var result = 0.0
            when (operator) {
                "+" -> result = operand1!! + value
                "-" -> result = operand1!! - value
                "*" -> result = operand1!! * value
                "/" -> if (value != 0.0) result = operand1!! / value
            }
            resultTextView.text = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            operand1 = null
            operator = null
            isNewOp = true
        }
    }

    private fun onClearClicked() {
        resultTextView.text = "0"
        operand1 = null
        operator = null
        isNewOp = true
    }
}