package com.example.walking;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.renderer.YAxisRenderer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class MonthRecord {
    MonthRecord(int year, int month) {
        this.year = year;
        this.month = month;
    }
    int year;
    int month;
    int size = 0;
    double avgDist = 0;
    double avgMarking = 0;
    double avgTime = 0;
}
public class Fragmentwalkstatistics extends Fragment {

    @Nullable
    int year;
    int month;
    TextView countText;
    TextView timeText;
    TextView distText;
    TextView markingText;
    BarChart chart;
    int[] colors = new int[12];
    int graphIndex = 0;
    Button nextButton;



    @RequiresApi(api = Build.VERSION_CODES.O)
    void updateData() {
        Map<String, MonthRecord> records;
        records = new TreeMap();

        int size = AppData.getInstance().getAllRecordItems().size();
        double avgDist = 0;
        double avgTime = 0;
        double avgMarking = 0;
        if(size != 0) {
            for (RecordItem item : AppData.getInstance().getAllRecordItems()) {
                int thisYear = Integer.parseInt(item.startTime.split("-")[0]);
                int thisMonth = Integer.parseInt(item.startTime.split("-")[1]);

                String key = "" + thisYear + thisMonth;
                String[] args = item.bottom.split(" ");
                double time = Integer.parseInt(args[1].split(":")[0]) * 60 + Integer.parseInt(args[1].split(":")[1]);
                double dist = Double.parseDouble(args[3].replace("KM", ""));
                double marking = Integer.parseInt(args[5]);
                if (!records.containsKey(key))
                    records.put(key, new MonthRecord(thisYear, thisMonth));
                MonthRecord record = records.get(key);
                record.avgDist = (record.avgDist * record.size + dist) / (record.size + 1);
                record.avgTime = (record.avgTime * record.size + time) / (record.size + 1);
                record.avgMarking = (record.avgMarking * record.size + marking) / (record.size + 1);
                record.size++;
                avgDist += dist;
                avgTime += time;
                avgMarking += marking;
            }
            avgDist /= size;
            avgTime /= size;
            avgMarking /= size;
        }
        double finalAvgTime = avgTime;
        double finalAvgDist = avgDist;
        double finalAvgMarking = avgMarking;
        this.getActivity().runOnUiThread(() -> {
            countText.setText(String.format("총산책횟수 %d", size));
            timeText.setText(String.format("평균시간 %d:%d", (int)(finalAvgTime /60),(int)(finalAvgTime % 60)));
            distText.setText(String.format("평균거리 %.2fKM", finalAvgDist));
            markingText.setText(String.format("평균마킹횟수 %d", (int) finalAvgMarking));
            if(graphIndex == 0) {
                nextButton.setText("거리");
            }
            else if(graphIndex == 1) {
                nextButton.setText("시간(분)");
            }
            else if(graphIndex == 2) {
                nextButton.setText("마킹횟수");
            } else if(graphIndex == 3) {
                nextButton.setText("산책횟수");
            }
            int cYear = (this.month - 6 <= 0) ? this.year - 1 : this.year;
            int cMonth = ((this.month + 7) % 12);
            chart.clear();
            BarData barData = new BarData();
            for(int i = 0; i < 6; i++) {

                float value = 0;
                String key = "" + cYear + cMonth;
                if(records.containsKey(key)){
                    MonthRecord record = records.get(key);
                    if(graphIndex == 0) {
                        value = (float)record.avgDist;
                    } else if(graphIndex == 1) {
                        value = (float)record.avgTime / 60;
                    } else if(graphIndex == 2) {
                        value = (float)record.avgMarking;
                    } else if(graphIndex == 3) {
                        value = (float)record.size;
                    }
                }

                List<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(i + 1, value));
                BarDataSet barDataSet = new BarDataSet(entries, String.format("%d월", cMonth));
                barDataSet.setColor(colors[i]);

                barData.addDataSet(barDataSet);

                cMonth += 1;
                if(cMonth == 13) {
                    cYear += 1;
                    cMonth = 1;
                }
            }
            barData.setBarWidth(1f);

            barData.setValueTextSize(0f);

            chart.setMinimumWidth(2);

            YAxis yAxis = chart.getAxisLeft();
            chart.getAxisRight().setAxisMinimum(0f);
            XAxis xAxis = chart.getXAxis();
            xAxis.setTextSize(6f);
            yAxis.setAxisMinimum(0f);
            chart.setData(barData);
        });

        
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.custom_walkstatistics, container, false);

        nextButton = rootView.findViewById(R.id.staticNextButton);
        nextButton.setOnClickListener((event) -> {
            graphIndex = (graphIndex + 1) % 4;
            updateData();
        });
        //System.out.println("할당");
        countText = rootView.findViewById(R.id.staticWalk);
        timeText = rootView.findViewById(R.id.staticClock);
        distText = rootView.findViewById(R.id.staticLenge);
        markingText = rootView.findViewById(R.id.staticMarking);
        chart = rootView.findViewById(R.id.chart);
        chart.setClickable(false);
        chart.setFocusable(false);
        chart.setEnabled(false);
        chart.setHovered(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setHighlightFullBarEnabled(false);
        Description desc = new Description();
        desc.setText("");

        chart.setDescription(desc);
        year = LocalDateTime.now().getYear();
        month = LocalDateTime.now().getMonthValue();
        Fragmentwalkstatistics parent = this;
        for(int i = 0; i < 12; i++) {
            colors[i] = Color.rgb((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (parent.isVisible()) {
                        updateData();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (parent.isDetached()) {
                        break;
                    }
                }
            }
        });
        thread.start();
        return rootView;
    }
}