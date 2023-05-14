package com.example.walking;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
class PathSelectListItemView extends LinearLayout {

        TextView nameView;
        CheckBox checkBox;
        public PathSelectListItemView(Context context) {   // context는 MainActivity에서 가져온 context
            super(context);
            init(context);
        }

        public PathSelectListItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public void init(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.custom_listview_loadseletion, this, true);

            nameView = (TextView) findViewById(R.id.listSelection_item_loadname);

            //반려견 선택 체크박스
            checkBox = (CheckBox) findViewById(R.id.bottomsheetloadselect_check_load);

        }

        public void setName(String name) {
            nameView.setText(name);
        }
}

class PathSelectionListAdapter extends BaseAdapter {

    ArrayList<PathItem> items;
    Context context;
    View parent;
    ArrayList<CheckBox> checkboxes = new ArrayList<>();
    public PathSelectionListAdapter(ArrayList<PathItem> items, Context context, View parent) {
        this.items = items;
        this.context = context;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    public void addItem(PathItem item) {
        items.add(item);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PathSelectListItemView view = new PathSelectListItemView(context);
        PathItem item = items.get(position);
        view.setName(item.name);

        // 반려견 선택
        CheckBox checkBox = view.findViewById(R.id.bottomsheetloadselect_check_load);
        checkboxes.add(checkBox);
        // 반려견 선택
        Button button = (Button) this.parent.findViewById(R.id.btn_hide_bt_sheet);

        checkBox.setOnClickListener((event) -> {
            for(CheckBox other : checkboxes) {
                if(other.isChecked()) {
                    button.setEnabled(true);
                    break;
                }
            }

            if(checkBox.isChecked()) {
                for(CheckBox other : checkboxes) {
                    if(checkBox.equals(other)) continue;
                    other.setChecked(false);
                }
                AppData.getInstance().selectedPathId = item.id;
            }

        });
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.list_item_delte);
        return view;
    }
}




