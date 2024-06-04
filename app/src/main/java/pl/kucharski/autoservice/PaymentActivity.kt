package pl.kucharski.autoservice

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        findViewById<ImageButton>(R.id.payment_blik).setOnClickListener {
            Toast.makeText(this, "Blik selected", Toast.LENGTH_SHORT).show()
            // Dodaj kod obsługujący płatność Blik
        }

        findViewById<ImageButton>(R.id.payment_bank).setOnClickListener {
            Toast.makeText(this, "Bank selected", Toast.LENGTH_SHORT).show()
            // Dodaj kod obsługujący płatność Bank
        }

        findViewById<ImageButton>(R.id.payment_visa).setOnClickListener {
            Toast.makeText(this, "Visa selected", Toast.LENGTH_SHORT).show()
            // Dodaj kod obsługujący płatność Visa
        }

        findViewById<ImageButton>(R.id.payment_google_pay).setOnClickListener {
            Toast.makeText(this, "Google Pay selected", Toast.LENGTH_SHORT).show()
            // Dodaj kod obsługujący płatność Google Pay
        }
    }
}
