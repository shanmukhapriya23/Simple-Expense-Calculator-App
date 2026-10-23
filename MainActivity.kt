package com.example.simpleexpensecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

// Data class to represent an Expense
data class Expense(val amount: Double, val category: String)

class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: TextInputEditText
    private lateinit var etCategory: TextInputEditText
    private lateinit var btnAddExpense: Button
    private lateinit var tvTotalExpenses: TextView
    private lateinit var tvExpenseList: TextView

    // Maintain a list of entered expenses
    private val expenseList = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        etAmount = findViewById(R.id.etAmount)
        etCategory = findViewById(R.id.etCategory)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        tvTotalExpenses = findViewById(R.id.tvTotalExpenses)
        tvExpenseList = findViewById(R.id.tvExpenseList)

        btnAddExpense.setOnClickListener {
            addExpense()
        }
    }

    private fun addExpense() {
        val amountStr = etAmount.text.toString().trim()
        val categoryStr = etCategory.text.toString().trim()

        if (amountStr.isEmpty() || categoryStr.isEmpty()) {
            Toast.makeText(this, "Please enter both amount and category", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid positive amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Add to list
        val expense = Expense(amount, categoryStr)
        expenseList.add(expense)

        // Clear fields
        etAmount.text?.clear()
        etCategory.text?.clear()

        // Update UI displays
        updateTotalAndList()
    }

    private fun updateTotalAndList() {
        // Calculate total expenses
        val total = expenseList.sumOf { it.amount }
        tvTotalExpenses.text = String.format("Total Expenses: $%.2f", total)

        // Build list string display
        val listBuilder = StringBuilder()
        for ((index, item) in expenseList.withIndex()) {
            listBuilder.append("${index + 1}. ${item.category}: $${String.format("%.2f", item.amount)}\n")
        }

        tvExpenseList.text = listBuilder.toString()
    }
}