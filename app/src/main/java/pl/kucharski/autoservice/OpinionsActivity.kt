// OpinionsActivity.kt
package pl.kucharski.autoservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OpinionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinions)

        // Inicjalizacja RecyclerView
        val opinionsRecyclerView = findViewById<RecyclerView>(R.id.opinionsRecyclerView)
        opinionsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Przygotowanie danych opinii (przykładowe dane)
        val opinionsList = mutableListOf(
            "Bardzo profesjonalna obsługa, polecam!",
            "Świetny serwis samochodowy, szybko i solidnie.",
            "Bardzo zadowolony z usług, na pewno tu wrócę!",
            "Bardzo miła obsługa, szybko załatwione wszystkie sprawy.",
            "Jestem bardzo zadowolony z jakości usług świadczonych przez ten serwis.",
            "Nie mam żadnych zastrzeżeń, wszystko zostało wykonane zgodnie z oczekiwaniami.",
            "Naprawdę polecam, profesjonalna obsługa klienta.",
            "Szybka reakcja na zgłoszenie usterki, fachowa pomoc.",
            "Pozytywne doświadczenie, będę wracać."
        )

        // Utworzenie adaptera i przypisanie go do RecyclerView
        val adapter = OpinionsAdapter(opinionsList)
        opinionsRecyclerView.adapter = adapter
    }
}
