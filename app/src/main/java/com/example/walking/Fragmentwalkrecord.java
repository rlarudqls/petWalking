package com.example.walking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.walking.R;

import java.util.ArrayList;
import java.util.Objects;

class RecordListItemView extends LinearLayout {

    TextView startTime;
    TextView endTime;
    TextView bottom;
    ImageButton delete;

    public RecordListItemView(Context context) {   // context는 MainActivity에서 가져온 context
        super(context);
        init(context);
    }

    public RecordListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //xml 파일
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_listview_record, this, true);

        startTime = (TextView) findViewById(R.id.list_recorditem_startTime);
        endTime = (TextView) findViewById(R.id.list_recorditem_endTime);
        bottom = (TextView) findViewById(R.id.list_recorditem_bottom);


    }

    public void setStartTime(String startTime) {
        this.startTime.setText(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime.setText(endTime);
    }

    public void setBottom(String bottom) {
        this.bottom.setText(bottom);
    }

}

class RecordItem {
    public String startTime;
    public String endTime;
    public String bottom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordItem that = (RecordItem) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(bottom, that.bottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, bottom);
    }

    public RecordItem(String startTime, String endTime, String bottom) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.bottom = bottom;
    }
}

class RecordListAdapter extends BaseAdapter {

    ArrayList<RecordItem> items;
    Context context;

    public RecordListAdapter(ArrayList<RecordItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    public void addItem(RecordItem item) {
        items.add(item);
    }

    public void removeItem(RecordItem item) {
        items.remove(item);
    }

    public void clearAllItem() {
        items.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordListItemView view = new RecordListItemView(context);
        RecordItem item = items.get(position);
        view.setStartTime(item.startTime);
        view.setEndTime(item.endTime);
        view.setBottom(item.bottom);
        return view;
    }
}

public class Fragmentwalkrecord extends Fragment {


    ListView listview_record;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.custom_walkrecord, container, false);
        listview_record = (ListView) rootView.findViewById(R.id.custom_walkrecord_listview);

        RecordListAdapter adapter = new RecordListAdapter(AppData.getInstance().getAllRecordItems(), getContext().getApplicationContext());
        AppData.getInstance().recordAapter = adapter;
        listview_record.setAdapter(adapter);

        return rootView;
    }


}