package com.example.walking;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

class DogWalkSelectionListAdapter extends BaseAdapter {

    ArrayList<DogItem> items;
    Context context;
    ArrayList<CheckBox> checkboxes = new ArrayList<>();
    public DogWalkSelectionListAdapter(ArrayList<DogItem> items, Context context) {
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

    public void addItem(DogItem item) {
        items.add(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DogSelectListItemView view = new DogSelectListItemView(context);
        DogItem item = items.get(position);
        view.setName(item.name);
        view.setImage(item.resId);

        // 반려견 선택
        CheckBox checkBox = view.findViewById(R.id.bottomsheetdogselect_listview_dog);
        checkboxes.add(checkBox);
        // 반려견 선택
        checkBox.setOnClickListener((event) -> {
            if (checkBox.isChecked()) {
                for (CheckBox other : checkboxes) {
                    if (checkBox.equals(other)) continue;
                    other.setChecked(false);
                }
                AppData.getInstance().selectedWalkDogId = item.id;
                ServerService.dogRecord();

            }
        });
        return view;
    }
}


public class Tabwalk extends FragmentActivity {

    TabLayout tabs;

    Fragmentwalkrecord fragmentrecord; //산책 기록
    Fragmentwalkstatistics fragmentwalkstatistics; // 산책 통계
    ListView dogListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_walkdiary);

        fragmentrecord = new Fragmentwalkrecord();
        fragmentwalkstatistics = new Fragmentwalkstatistics();
        dogListView = (ListView)findViewById(R.id.listview_dogwalk);
        ArrayList<DogItem> items = new ArrayList<>();
        DogWalkSelectionListAdapter adapter = new DogWalkSelectionListAdapter(items, getApplicationContext());
        for(DogItem item : AppData.getInstance().getAllDogItems()) {
            adapter.addItem(item);
        }
        dogListView.setAdapter(adapter);

        getSupportFragmentManager().beginTransaction().add(R.id.walkconti, fragmentrecord).commit();

        tabs = findViewById(R.id.tabs);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragmentrecord;
                else if(position == 1)
                    selected = fragmentwalkstatistics;

                getSupportFragmentManager().beginTransaction().replace(R.id.walkconti, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}