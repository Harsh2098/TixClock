package com.hmproductions.tixclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    GridView tenHour_gridView, unitHour_gridView, tenMin_gridView, unitMin_gridView;
    TixAdapter mTenHourTixAdapter, mUnitHourTixAdapter, mTenMinTixAdapter, mUnitMinTixAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tenHour_gridView = (GridView)findViewById(R.id.tenHour_gridView);
        unitHour_gridView = (GridView) findViewById(R.id.unitHour_gridView);
        tenMin_gridView = (GridView)findViewById(R.id.tenMin_gridView);
        unitMin_gridView = (GridView) findViewById(R.id.unitMin_gridView);

        mTenHourTixAdapter = new TixAdapter(this, "#E53935", 3);
        mUnitHourTixAdapter = new TixAdapter(this, "#76FF03", 9);
        mTenMinTixAdapter = new TixAdapter(this, "#2196F3", 6);
        mUnitMinTixAdapter = new TixAdapter(this, "#E53935", 9);

        tenHour_gridView.setAdapter(mTenHourTixAdapter);
        unitHour_gridView.setAdapter(mUnitHourTixAdapter);
        tenMin_gridView.setAdapter(mTenMinTixAdapter);
        unitMin_gridView.setAdapter(mUnitMinTixAdapter);

        StartClock();
    }

    private void StartClock()
    {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",Locale.US);
                                String time = formatter.format(new Date(System.currentTimeMillis()));

                                mTenHourTixAdapter.swapData(createTixList(Integer.parseInt(String.valueOf(time.charAt(0))), 3));
                                mUnitHourTixAdapter.swapData(createTixList(Integer.parseInt(String.valueOf(time.charAt(1))), 9));
                                mTenMinTixAdapter.swapData(createTixList(Integer.parseInt(String.valueOf(time.charAt(3))), 6));
                                mUnitMinTixAdapter.swapData(createTixList(Integer.parseInt(String.valueOf(time.charAt(4))), 9));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Log.e(LOG_TAG,"Interrupted Exception");
                }
            }
        }.start();
    }

    /* @param tiles It is the number of tiles to be lightened up
       @param total It is the total number of tiles     */

    private List<Tix> createTixList(int tiles, int total)
    {
        List<Tix> tixList = new ArrayList<>();

        boolean[] tilesArray = new boolean[total];
        for(int i =0 ; i<total ; ++i)
            tilesArray[i] = i < tiles;

        for(int i=0 ; i<10 ; ++i)
        {
            boolean temp;

            Random r1 = new Random();
            Random r2 = new Random();

            int randomNumber1 = (r1.nextInt(total));
            int randomNumber2 = (r2.nextInt(total));

            temp = tilesArray[randomNumber1];
            tilesArray[randomNumber1] = tilesArray[randomNumber2];
            tilesArray[randomNumber2] = temp;
        }

        for (int i=0 ; i<total ; ++i)
            tixList.add(new Tix(tilesArray[i]));

        return tixList;
    }
}
