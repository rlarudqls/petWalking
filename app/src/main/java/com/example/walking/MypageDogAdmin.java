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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MypageDogAdmin extends AppCompatActivity {




    ListView listView_dogadmin;
    DogListAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("테스트");
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_dogadmin);
        listView_dogadmin =(ListView)findViewById(R.id.listview_dogadmin);
        adapter = new DogListAdapter(AppData.getInstance().getAllDogItems(), getApplicationContext());
        listView_dogadmin.setAdapter(adapter);
        Button name_rule_btn = (Button) findViewById(R.id.add_pat);
        name_rule_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MypageDogAdd.class);
                startActivity(intent);
            }
        });
    }
}

class DogListItemView extends LinearLayout {

    TextView nameView;
    TextView birthView;
    TextView speciesView;
    ImageView imageView;


    public DogListItemView(Context context) {   // context는 MainActivity에서 가져온 context
        super(context);
        init(context);
    }

    public DogListItemView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_listview_dogadd, this, true);

        nameView = (TextView) findViewById(R.id.list_item_dogname);
        birthView = (TextView) findViewById(R.id.list_item_birth);
        speciesView = (TextView) findViewById(R.id.list_item_species);
        imageView = (ImageView) findViewById(R.id.imageView_dog);

    }

    public void setName(String name){
        nameView.setText(name);
    }

    public void setBirth(String birth){
        birthView.setText(birth);
    }

    public void setSpecies(String species){
        speciesView.setText(species);
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
}

class DogItem {
    public String id;
    public String name;
    public String birth;
    public String species;
    public int resId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogItem dogItem = (DogItem) o;
        return id == dogItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birth, species, resId);
    }

    public DogItem(String id, String name, String birth, String species, int resId){
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.species = species;
        this.resId = resId;
    }
}
class PathItem {
    public String id;
    public String name;


    @Override
    public boolean equals(Object o) {
        return id == ((PathItem)o).id;
    }
    public PathItem(String id, String name){

        this.name = name;
        this.id = id;
    }
}
class PathListAdapter extends BaseAdapter{

    ArrayList<PathItem> items;
    Context context;

    public PathListAdapter(ArrayList<PathItem> items, Context context){
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

    public void addItem(PathItem item){
        items.add(item);
    }
    public void removeItem(PathItem item) {
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
        PathSelectListItemView view = new PathSelectListItemView(context);
        PathItem item = items.get(position);
        view.setName(item.name);

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.list_item_delte);
        deleteButton.setOnClickListener((View v) -> {
            AppData.getInstance().removePath(item);
            removeItem(item);
            notifyDataSetChanged();
        });

        return  view;
    }
}
class DogListAdapter extends BaseAdapter{

    ArrayList<DogItem> items;
    Context context;

    public DogListAdapter(ArrayList<DogItem> items, Context context){
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

    public void addItem(DogItem item){
        items.add(item);
    }
    public void removeItem(DogItem item) {
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
        DogListItemView view = new DogListItemView(context);
        DogItem item = items.get(position);
        view.setName(item.name);
        view.setBirth(item.birth);
        view.setSpecies(item.species);
        view.setImage(item.resId);

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.list_item_delte);
        deleteButton.setOnClickListener((View v) -> {
            ServerService.dogDelete(item.id);
            AppData.getInstance().removeDog(item);
            removeItem(item);
            notifyDataSetChanged();
        });

        return  view;
    }
}