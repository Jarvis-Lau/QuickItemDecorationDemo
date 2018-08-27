package com.jarvislau.quickitemdecorationdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarvislau.quickitemdecoration.ItemDivider;
import com.jarvislau.quickitemdecoration.QuickItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            entities.add(new Entity("text" + i, "2018-07-" + i));
        }

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TextAdapter textAdapter = new TextAdapter(entities);
        recyclerView.setAdapter(textAdapter);

        QuickItemDecoration quickItemDecoration = new QuickItemDecoration(new ItemDivider()
                .setColor(0xFFFF0000)
                .setWidth(10)
                .setMarginLeft(15)
                .setMarginTop(15)
                .setMarginBottom(40)
                .setMarginRight(100)
                .setItemGridBackgroundColor(0xFF0000FF)
                , 500
                , 200,
                0xFF00FF00, false);
        recyclerView.addItemDecoration(quickItemDecoration);
    }

    class TextAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        private List<Entity> entities;

        TextAdapter(List<Entity> entities) {
            this.entities = entities;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.text.setText(entities.get(position).getText());
            holder.date.setText(entities.get(position).getDate());
        }

        @Override
        public int getItemCount() {
            return entities.size();
        }

    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private TextView date;

        BaseViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tvText);
            date = itemView.findViewById(R.id.tvDate);
        }
    }

}