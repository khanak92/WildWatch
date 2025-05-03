package com.example.wildwatch1

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class TrendAnalysisActivity2 : AppCompatActivity() {

    private lateinit var btnViewTrends: Button
    private lateinit var btnGenerateGraph: Button
    private lateinit var btnByMonth: Button
    private lateinit var btnByYear: Button
    private lateinit var tabContent: FrameLayout
    private lateinit var tableLayout: TableLayout

    private var isTrendViewActive = true
    private var isMonthly = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend_analysis2)

        btnViewTrends = findViewById(R.id.btnViewTrend)
        btnGenerateGraph = findViewById(R.id.btnGenerateGraph)
        btnByMonth = findViewById(R.id.btnByMonth)
        btnByYear = findViewById(R.id.btnByYear)
        tabContent = findViewById(R.id.contentContainer)

        btnViewTrends.setOnClickListener {
            isTrendViewActive = true
            findViewById<LinearLayout>(R.id.secondaryToggle).visibility = LinearLayout.VISIBLE
            showTrendTable()
        }

        btnGenerateGraph.setOnClickListener {
            isTrendViewActive = false
            findViewById<LinearLayout>(R.id.secondaryToggle).visibility = LinearLayout.GONE
            showGraph()
        }

        btnByMonth.setOnClickListener {
            isMonthly = true
            populateTable(tableLayout)
        }

        btnByYear.setOnClickListener {
            isMonthly = false
            populateTable(tableLayout)
        }

        showTrendTable() // Default view
    }

    private fun showTrendTable() {
        tabContent.removeAllViews()

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        tableLayout = TableLayout(this)
        layout.addView(tableLayout)

        tabContent.addView(layout)
        populateTable(tableLayout)
    }

    private fun populateTable(tableLayout: TableLayout) {
        tableLayout.removeAllViews()

        val headers = arrayOf("Province", "Detections", "Deaths")
        val headerRow = TableRow(this)
        headers.forEach { title ->
            val tv = TextView(this).apply {
                text = title
                setTextColor(Color.BLACK)
                setPadding(16, 8, 16, 8)
                textSize = 22f
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            headerRow.addView(tv)
        }
        tableLayout.addView(headerRow)

        val data = if (isMonthly) getMonthlyData() else getYearlyData()
        data.forEach { row ->
            val tableRow = TableRow(this)
            row.forEach { cell ->
                val tv = TextView(this).apply {
                    text = cell
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    setPadding(16, 8, 16, 8)
                }
                tableRow.addView(tv)
            }
            tableLayout.addView(tableRow)
        }
    }

    private fun showGraph() {
        tabContent.removeAllViews()
        val lineChart = LineChart(this)

        val entries = listOf(
            Entry(0f, 35f),
            Entry(1f, 28f),
            Entry(2f, 34f),
            Entry(3f, 32f),
            Entry(4f, 40f)
        )

        val dataSet = LineDataSet(entries, "Detections by Month").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            lineWidth = 2f
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.description.isEnabled = false
        lineChart.invalidate()

        tabContent.addView(lineChart)
    }

    private fun getMonthlyData(): List<Array<String>> = listOf(
        arrayOf("Panjab", "0", "0"),
        arrayOf("Sindh", "1", "0"),
        arrayOf("Balochistan", "0", "0"),
        arrayOf("KPK", "3", "0"),
        arrayOf("Kashmir", "1", "0")
    )

    private fun getYearlyData(): List<Array<String>> = listOf(
        arrayOf("Panjab", "10", "1"),
        arrayOf("Sindh", "15", "3"),
        arrayOf("Balochistan", "100", "10"),
        arrayOf("KPK", "40", "4"),
        arrayOf("Kashmir", "150", "4"),
        arrayOf("IIJOK", "300", "17"),
        arrayOf("Coastal", "50", "0"),
        arrayOf("Centre", "2", "1")
    )
}
