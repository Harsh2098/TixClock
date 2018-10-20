package com.hmproductions.tixclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mTenHourTixAdapter: TixRecyclerView
    private lateinit var mUnitHourTixAdapter: TixRecyclerView
    private lateinit var mTenMinTixAdapter: TixRecyclerView
    private lateinit var mUnitMinTixAdapter: TixRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTenHourTixAdapter = TixRecyclerView(this, null, "#E53935")
        mUnitHourTixAdapter = TixRecyclerView(this, null, "#76FF03")
        mTenMinTixAdapter = TixRecyclerView(this, null, "#2196F3")
        mUnitMinTixAdapter = TixRecyclerView(this, null, "#E53935")

        with(tenHourRecyclerView) {
            adapter = mTenHourTixAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        with(unitHourRecyclerView) {
            adapter = mUnitHourTixAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        with(tenMinRecyclerView) {
            adapter = mTenMinTixAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        with(unitMinRecyclerView) {
            adapter = mUnitMinTixAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        startClock()
    }

    private fun startClock() {
        object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        Thread.sleep(1000)
                        runOnUiThread {
                            val formatter = SimpleDateFormat("HH:mm", Locale.US)
                            val time = formatter.format(Date(System.currentTimeMillis()))

                            mTenHourTixAdapter.swapData(createTixList(Integer.parseInt(time[0].toString()), 3))
                            mUnitHourTixAdapter.swapData(createTixList(Integer.parseInt(time[1].toString()), 9))
                            mTenMinTixAdapter.swapData(createTixList(Integer.parseInt(time[3].toString()), 6))
                            mUnitMinTixAdapter.swapData(createTixList(Integer.parseInt(time[4].toString()), 9))
                        }
                    }
                } catch (e: InterruptedException) {
                    Log.e(LOG_TAG, "Interrupted Exception")
                }

            }
        }.start()
    }

    /* @param tiles It is the number of tiles to be lightened up
       @param total It is the total number of tiles     */

    private fun createTixList(tiles: Int, total: Int): ArrayList<Tix> {
        val tixList = ArrayList<Tix>()

        val tilesArray = BooleanArray(total)
        for (i in 0 until total)
            tilesArray[i] = i < tiles

        for (i in 0..9) {
            val randomNumber1 = Random().nextInt(total)
            val randomNumber2 = Random().nextInt(total)

            val temp = tilesArray[randomNumber1]
            tilesArray[randomNumber1] = tilesArray[randomNumber2]
            tilesArray[randomNumber2] = temp
        }

        for (i in 0 until total)
            tixList.add(Tix(tilesArray[i]))

        return tixList
    }
}
