package com.jarvislau.quickitemdecorationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jarvislau.quickitemdecoration.ItemDecorationConfig;
import com.jarvislau.quickitemdecoration.ItemDivider;
import com.jarvislau.quickitemdecoration.QuickItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JarvisLau on 2019-05-10.
 * Description :
 */
public class TwoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        getSupportActionBar().setTitle("two");
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            entities.add(new Entity("text" + i, "2018-07-" + i));
        }

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TextAdapter textAdapter = new TextAdapter(entities);
        recyclerView.setAdapter(textAdapter);

        QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
        quickItemDecoration.setConfig(new ItemDecorationConfig().create().setRecyclerMarginTop(100).build());
        recyclerView.addItemDecoration(quickItemDecoration);
    }

    public void startActivity(View view) {
        startActivity(new Intent(this, ThreeActivity.class));
    }

    class TextAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        private List<Entity> entities;

        TextAdapter(List<Entity> entities) {
            this.entities = entities;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(TwoActivity.this).inflate(R.layout.item, parent, false));
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
