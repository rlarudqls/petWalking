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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BottomSheetDogSelect {

    // 반려견 선택 커스텀리스트뷰  산책 변수
    private ListView listView_dogselection;
    // 마킹지점 선택 커스텀리스트뷰  산책 변수
    private ListView listview_pathselection;


    @Nullable
    public void init(BottomSheetDog dog) {

        Activity activity = dog.getActivity();
        // 마킹지점 선택 리스트 뷰
        listview_pathselection = dog.getView().findViewById(R.id.listview_pathselection);
        //반려견 선택 리스트뷰
        listView_dogselection = dog.getView().findViewById(R.id.listview_dogselection);

        ArrayList<DogItem> items = new ArrayList<>();
        ArrayList<PathItem> pathItems = new ArrayList<>();
        DogSelectionListAdapter adapter = new DogSelectionListAdapter(items, activity.getApplicationContext(), dog.getView());
        PathSelectionListAdapter pathAdapter = new PathSelectionListAdapter(AppData.getInstance().getAllPathItems(), activity.getApplicationContext(), dog.getView());

        for (DogItem item : AppData.getInstance().getAllDogItems()) {
            adapter.addItem(item);
        }
        /*for (PathItem item : AppData.getInstance().getAllPathItems()) {
            pathAdapter.addItem(item);
        }*/


        // 반려견 선택 페이지 버튼 클릭시 화면 이동
        Button button = dog.getView().findViewById(R.id.btn_hide_bt_sheet);
        button.setOnClickListener((event) -> {
            dog.changeView(1);

        });


        // 경로 지정 페이지 버튼
        Button button1 = dog.getView().findViewById(R.id.btn_startwalk);
        // 경로 지정 페이지 -> 기존경로 체크박스
        CheckBox checkBox1 = dog.getView().findViewById(R.id.linkseletion_alreadcheckbox);
        CheckBox checkBox2 = dog.getView().findViewById(R.id.linkseletion_newycheckbox);
        Button button3 = dog.getView().findViewById(R.id.WkstartBtn);


        button1.setOnClickListener((event) -> {
            // 기존 경로 체크박스 체크시

            if (checkBox1.isChecked()) {
                dog.changeView(2);
                pathAdapter.notifyDataSetChanged();
            }
            // 새로운 경로 체크박스 체크시
            // BottomSheetDog 클래스에서는 안되고 왜 여기는 됌? ;;;; 이유를 모르겟음 ㅋ
            if (checkBox2.isChecked()) {
                dog.dismiss();
                dog.view9.setVisibility(View.VISIBLE);
                dog.button3.setVisibility(View.INVISIBLE);
                AppData.getInstance().pathMode = 2;

                //dog.changeView(1);
            }

        });


        listView_dogselection.setAdapter(adapter);
        listview_pathselection.setAdapter(pathAdapter); //테스트 됨
    }

}


class DogSelectListItemView extends LinearLayout {

    TextView nameView;
    ImageView imageView;
    CheckBox checkBox;

    public DogSelectListItemView(Context context) {   // context는 MainActivity에서 가져온 context
        super(context);
        init(context);
    }

    public DogSelectListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_listview_dogseletion, this, true);

        nameView = (TextView) findViewById(R.id.listSelection_item_dogname);
        imageView = (ImageView) findViewById(R.id.imageViewSelection_dog);

        //반려견 선택 체크박스
        checkBox = (CheckBox) findViewById(R.id.bottomsheetdogselect_listview_dog);

    }

    public void setName(String name) {
        nameView.setText(name);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}

class DogSelectionListAdapter extends BaseAdapter {

    ArrayList<DogItem> items;
    Context context;
    View parent;
    int checkedItem = -1;
    ArrayList<CheckBox> checkboxes = new ArrayList<>();

    public DogSelectionListAdapter(ArrayList<DogItem> items, Context context, View parent) {
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
        Button button = (Button) this.parent.findViewById(R.id.btn_hide_bt_sheet);

        checkBox.setOnClickListener((event) -> {

            if (checkBox.isChecked()) {
                for (CheckBox other : checkboxes) {
                    if (checkBox.equals(other)) continue;
                    other.setChecked(false);
                }
                button.setEnabled(true);
                AppData.getInstance().selectedDogId = item.id;
                ServerService.dogPath();
            }

        });
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.list_item_delte);
        return view;
    }
}





