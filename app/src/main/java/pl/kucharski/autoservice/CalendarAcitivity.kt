package pl.kucharski.autoservice

import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Tutaj możesz obsługiwać zmianę daty
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            // Możesz wykonać dowolne działania na podstawie wybranej daty
        }
    }
}
