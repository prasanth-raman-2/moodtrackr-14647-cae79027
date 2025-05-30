package com.example.moodtrackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

// PUBLIC_INTERFACE
class MainActivity : AppCompatActivity() {

    private enum class MainViewType { ENTRY, TRENDS }

    private var selectedMood: Mood? = null
    private var currentView: MainViewType = MainViewType.ENTRY

    enum class Mood(val label: String) {
        HAPPY("happy"),
        SAD("sad"),
        STRESSED("stressed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        setupDate()
        setupMoodSelection()
        setupNavigation()
        setupSaveEntry()
    }

    private fun setupDate() {
        val textDate = findViewById<TextView>(R.id.text_date)
        val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        textDate.text = "Today, ${dateFormat.format(Date())}"
    }

    private fun setupMoodSelection() {
        val happyBtn = findViewById<ImageButton>(R.id.mood_happy)
        val sadBtn = findViewById<ImageButton>(R.id.mood_sad)
        val stressedBtn = findViewById<ImageButton>(R.id.mood_stressed)

        val moodButtons = mapOf(
            Mood.HAPPY to happyBtn,
            Mood.SAD to sadBtn,
            Mood.STRESSED to stressedBtn
        )

        moodButtons.forEach { (mood, btn) ->
            btn.setOnClickListener {
                selectMood(mood)
                // Visual active indication
                moodButtons.forEach { (m, b) ->
                    b.alpha = if (m == mood) 1.0f else 0.32f
                }
            }
        }
    }

    private fun selectMood(mood: Mood) {
        selectedMood = mood
    }

    private fun setupSaveEntry() {
        val saveButton = findViewById<Button>(R.id.btn_save_mood)
        val journalEditText = findViewById<EditText>(R.id.text_journal)
        saveButton.setOnClickListener {
            val mood = selectedMood
            if (mood == null) {
                Toast.makeText(this, "Please select a mood.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val entry = journalEditText.text.toString().trim()
            // Saving could be done to DB or prefs; for now, just show toast
            Toast.makeText(
                this,
                "Saved: ${mood.label.capitalize()} mood with journal entry.",
                Toast.LENGTH_SHORT
            ).show()
            journalEditText.setText("")
        }
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_entry -> showEntryView()
                R.id.nav_trends -> showTrendsView()
                else -> false
            }
        }
        // Set default as Entry
        bottomNav.selectedItemId = R.id.nav_entry
    }

    // Switch to mood entry view
    private fun showEntryView(): Boolean {
        val layout = findViewById<ConstraintLayout>(R.id.main_container)
        // Show all mood/journal components, hide trends
        findViewById<TextView>(R.id.text_date).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.layout_moods).visibility = View.VISIBLE
        findViewById<EditText>(R.id.text_journal).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_save_mood).visibility = View.VISIBLE
        // Trends: Would be a fragment or view; here just toast/placeholder for now
        // Remove/Hide trends view if implemented
        currentView = MainViewType.ENTRY
        return true
    }

    // Switch to trends view
    private fun showTrendsView(): Boolean {
        // Just a placeholder. Replace with actual fragment/view logic later.
        findViewById<TextView>(R.id.text_date).visibility = View.GONE
        findViewById<LinearLayout>(R.id.layout_moods).visibility = View.GONE
        findViewById<EditText>(R.id.text_journal).visibility = View.GONE
        findViewById<Button>(R.id.btn_save_mood).visibility = View.GONE

        Toast.makeText(this, "Trends view coming soon!", Toast.LENGTH_SHORT).show()
        currentView = MainViewType.TRENDS
        // TODO: Add Calendar/Graph fragment/switch logic
        return true
    }
}
