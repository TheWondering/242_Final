package com.example.project.ui.cart

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.project.R
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var backArrow: ImageView
    private lateinit var tvSubtotalNum: TextView
    private lateinit var tvTotalNum: TextView
    private lateinit var tvTotal2Num: TextView
    private lateinit var btnCheckout: AppCompatButton

    // Payment method selection
    private lateinit var cardViewVisa: CardView
    private lateinit var cardViewPaypal: CardView
    private lateinit var cardViewMastercard: CardView
    private lateinit var radioVisa: RadioButton
    private lateinit var radioPaypal: RadioButton
    private lateinit var radioMastercard: RadioButton

    // Form fields
    private lateinit var etCardNumber: EditText
    private lateinit var etCardHolder: EditText
    private lateinit var etExpDate: EditText
    private lateinit var etCVV: EditText

    // Variables
    private var selectedPaymentMethod = PaymentMethod.VISA
    private var subtotal = 0.0
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Initialize UI elements
        initializeViews()

        // Set up listeners
        setupListeners()

        // Retrieve data from previous screen
        retrieveCartData()

        // Update UI with cart data
        updatePrices()
    }

    private fun initializeViews() {
        // Basic UI elements
        backArrow = findViewById(R.id.backArrow)
        tvSubtotalNum = findViewById(R.id.tvSubtotalNum)
        tvTotalNum = findViewById(R.id.tvTotalNum)
        tvTotal2Num = findViewById(R.id.tvTotal2Num)
        btnCheckout = findViewById(R.id.btnCheckout)

        // Payment method selection
        cardViewVisa = findViewById(R.id.cardViewVisa)
        cardViewPaypal = findViewById(R.id.cardViewPaypal)
        cardViewMastercard = findViewById(R.id.cardViewMastercard)
        radioVisa = findViewById(R.id.radioVisa)
        radioPaypal = findViewById(R.id.radioPaypal)
        radioMastercard = findViewById(R.id.radioMastercard)

        // Form fields
        etCardNumber = findViewById(R.id.etCardNumber)
        etCardHolder = findViewById(R.id.etCardHolder)
        etExpDate = findViewById(R.id.etExpDate)
        etCVV = findViewById(R.id.etCVV)

        // Set default payment method
        radioVisa.isChecked = true
    }

    private fun setupListeners() {
        // Back arrow click listener
        backArrow.setOnClickListener {
            finish()
        }

        // Payment method selection listeners
        setupPaymentMethodListeners()

        // Form field listeners
        setupFormFieldListeners()

        // Checkout button click listener
        btnCheckout.setOnClickListener {
            // Simply show success message and finish
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupPaymentMethodListeners() {
        // Set up click listeners for payment method cards
        cardViewVisa.setOnClickListener {
            selectPaymentMethod(PaymentMethod.VISA)
        }

        cardViewPaypal.setOnClickListener {
            selectPaymentMethod(PaymentMethod.PAYPAL)
        }

        cardViewMastercard.setOnClickListener {
            selectPaymentMethod(PaymentMethod.MASTERCARD)
        }

        // Set up click listeners for radio buttons
        radioVisa.setOnClickListener {
            selectPaymentMethod(PaymentMethod.VISA)
        }

        radioPaypal.setOnClickListener {
            selectPaymentMethod(PaymentMethod.PAYPAL)
        }

        radioMastercard.setOnClickListener {
            selectPaymentMethod(PaymentMethod.MASTERCARD)
        }
    }

    private fun setupFormFieldListeners() {
        // Add text formatting for card number (adds spaces every 4 digits)
        etCardNumber.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(" ", "")
                    if (userInput.length <= 16) {
                        val formatted = formatCardNumber(userInput)
                        current = formatted
                        etCardNumber.setText(formatted)
                        etCardNumber.setSelection(formatted.length)
                    }
                }
            }
        })

        // Add text formatting for expiration date (adds / after 2 digits)
        etExpDate.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace("/", "")
                    if (userInput.length <= 4) {
                        val formatted = formatExpirationDate(userInput)
                        current = formatted
                        etExpDate.setText(formatted)
                        etExpDate.setSelection(formatted.length)
                    }
                }
            }
        })
    }

    private fun retrieveCartData() {
        // Here you would typically get the cart data from intent or shared preferences
        // For this example, I'll use static values
        subtotal = intent.getDoubleExtra("subtotal", 233.45)
        total = subtotal // In a real app, you might add shipping, tax, etc.
    }

    private fun updatePrices() {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)

        tvSubtotalNum.text = formatter.format(subtotal)
        tvTotalNum.text = formatter.format(total)
        tvTotal2Num.text = formatter.format(total)
    }

    private fun selectPaymentMethod(method: PaymentMethod) {
        // Update selected payment method
        selectedPaymentMethod = method

        // Update UI to reflect selected payment method
        radioVisa.isChecked = method == PaymentMethod.VISA
        radioPaypal.isChecked = method == PaymentMethod.PAYPAL
        radioMastercard.isChecked = method == PaymentMethod.MASTERCARD

        // Update card fields visibility based on payment method
        val cardFieldsVisible = method != PaymentMethod.PAYPAL
        findViewById<TextView>(R.id.tvCardDetails).visibility = if (cardFieldsVisible) View.VISIBLE else View.GONE
        etCardNumber.visibility = if (cardFieldsVisible) View.VISIBLE else View.GONE
        etCardHolder.visibility = if (cardFieldsVisible) View.VISIBLE else View.GONE
        etExpDate.visibility = if (cardFieldsVisible) View.VISIBLE else View.GONE
        etCVV.visibility = if (cardFieldsVisible) View.VISIBLE else View.GONE
    }

    // Helper methods
    private fun formatCardNumber(number: String): String {
        val formatted = StringBuilder()
        for (i in number.indices) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(" ")
            }
            formatted.append(number[i])
        }
        return formatted.toString()
    }

    private fun formatExpirationDate(date: String): String {
        if (date.length < 3) {
            return date
        }
        return date.substring(0, 2) + "/" + date.substring(2)
    }

    // Enum for payment methods
    enum class PaymentMethod {
        VISA, PAYPAL, MASTERCARD
    }
}