package pl.kucharski.autoservice

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsActivity : AppCompatActivity() {
    private lateinit var settingsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Zastosowanie zapisanej lokalizacji przed załadowaniem widoków
        LocaleManager.applyPersistedLanguage(this)
        setContentView(R.layout.activity_settings)

        // Inicjalizacja widoków
        settingsRecyclerView = findViewById(R.id.settingsRecyclerView)

        // Przykładowe opcje ustawień w RecyclerView
        val settingsOptions = listOf(
            "Zmień głośność aplikacji",
            "Dostosuj jasność ekranu",
            "Przełącz powiadomienia",
            "Ustawienia językowe",
            "Preferencje dotyczące motywu"
            // Dodaj więcej opcji w razie potrzeby
        )

        // Ustawienia RecyclerView
        settingsRecyclerView.layoutManager = LinearLayoutManager(this)
        val settingsAdapter = OptionsAdapter(settingsOptions, object : OptionsAdapter.OnItemClickListener {
            override fun onItemClick(option: String) {
                // Obsługa kliknięcia na opcję w RecyclerView
                when (option) {
                    "Zmień głośność aplikacji" -> showVolumeDialog()
                    "Dostosuj jasność ekranu" -> showBrightnessDialog()
                    "Ustawienia językowe" -> showLanguageDialog()
                }
            }
        })
        settingsRecyclerView.adapter = settingsAdapter
    }

    private fun showVolumeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_volume_control, null)
        val seekBar: SeekBar = dialogView.findViewById(R.id.dialogVolumeSeekBar)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Zmień głośność aplikacji")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Obsługa zatwierdzenia zmiany głośności
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                // Obsługa anulowania zmiany głośności
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showBrightnessDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_brightness_control, null)
        val seekBar: SeekBar = dialogView.findViewById(R.id.dialogBrightnessSeekBar)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Dostosuj jasność ekranu")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Obsługa zatwierdzenia zmiany jasności
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                // Obsługa anulowania zmiany jasności
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showLanguageDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_language_settings, null)
        val languageSpinner: Spinner = dialogView.findViewById(R.id.dialogLanguageSpinner)

        // Przykładowe języki
        val languages = listOf("pl", "en", "de", "fr")
        val languageNames = listOf("Polski", "Angielski", "Niemiecki", "Francuski")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languageNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setTitle("Ustawienia językowe")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Obsługa zatwierdzenia zmiany języka
                val selectedLanguage = languages[languageSpinner.selectedItemPosition]
                LocaleManager.setLocale(this, selectedLanguage)
                recreate() // Odświeżenie aktywności w celu zastosowania nowego języka
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                // Obsługa anulowania zmiany języka
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}
