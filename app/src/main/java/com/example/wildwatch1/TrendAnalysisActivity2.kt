package com.example.wildwatch1

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Suppress("DEPRECATION")
class TrendAnalysisActivity2 : AppCompatActivity() {

    private lateinit var btnViewTrends: Button
    private lateinit var btnGenerateGraph: Button
    private lateinit var tabContent: FrameLayout

    private var isTrendViewActive = true
    private var isMonthly = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend_analysis2)

        btnViewTrends = findViewById(R.id.btnViewTrend)
        btnGenerateGraph = findViewById(R.id.btnGenerateGraph)
        tabContent = findViewById(R.id.contentContainer)

        btnViewTrends.setOnClickListener {
            isTrendViewActive = true
            showTrendTable()
        }

        btnGenerateGraph.setOnClickListener {
            isTrendViewActive = false
            showGraph()
        }

        showTrendTable() // default view
    }

    private fun showTrendTable() {
        tabContent.removeAllViews()

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toggleLayout = LinearLayout(this)
        toggleLayout.orientation = LinearLayout.HORIZONTAL
        toggleLayout.setPadding(8, 8, 8, 8)

        val btnMonth = Button(this).apply {
            text = "By Month"
            setBackgroundColor(resources.getColor(R.color.primary))
            setTextColor(Color.WHITE)
            setOnClickListener {
                isMonthly = true
                populateTable(tableLayout)
            }
        }

        val btnYear = Button(this).apply {
            text = "By Year"
            setBackgroundColor(resources.getColor(R.color.primary))
            setTextColor(Color.WHITE)
            setOnClickListener {
                isMonthly = false
                populateTable(tableLayout)
            }
        }

        toggleLayout.addView(btnMonth)
        toggleLayout.addView(btnYear)
        layout.addView(toggleLayout)

        tableLayout = TableLayout(this)
        layout.addView(tableLayout)

        tabContent.addView(layout)

        populateTable(tableLayout)
    }

    private lateinit var tableLayout: TableLayout

    private fun populateTable(tableLayout: TableLayout) {
        tableLayout.removeAllViews()

        val headers = arrayOf("Country", "Detections", "Deaths")
        val headerRow = TableRow(this)
        headers.forEach { title ->
            val tv = TextView(this)
            tv.text = title
            tv.setTextColor(Color.BLACK)
            tv.setPadding(16, 8, 16, 8)
            headerRow.addView(tv)
        }
        tableLayout.addView(headerRow)

        val data = if (isMonthly) getMonthlyData() else getYearlyData()
        data.forEach { row ->
            val tableRow = TableRow(this)
            row.forEach { cell ->
                val tv = TextView(this)
                tv.text = cell
                tv.setPadding(16, 8, 16, 8)
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
            color = Color.BLUE
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

    private fun getMonthlyData(): List<Array<String>> {
        return listOf(
            arrayOf("Pakistan", "0", "0"),
            arrayOf("India", "1", "0"),
            arrayOf("Africa", "0", "0"),
            arrayOf("Bangladesh", "3", "0"),
            arrayOf("Japan", "1", "0")
        )
    }

    private fun getYearlyData(): List<Array<String>> {
        return listOf(
            arrayOf("Pakistan", "10", "1"),
            arrayOf("India", "15", "3"),
            arrayOf("Africa", "100", "10"),
            arrayOf("America", "40", "4"),
            arrayOf("Canada", "150", "4"),
            arrayOf("Sirilanka", "300", "17"),
            arrayOf("Bangladesh", "50", "0"),
            arrayOf("Japan", "2", "1")
        )
    }
}
