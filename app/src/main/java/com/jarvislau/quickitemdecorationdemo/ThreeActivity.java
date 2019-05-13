package com.jarvislau.quickitemdecorationdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jarvislau.quickitemdecoration.QuickItemDecoration;

import java.util.List;

/**
 * Created by JarvisLau on 2019-05-13.
 * Description :
 */
public class ThreeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        recyclerView = findViewById(R.id.recyclerView);

        ThreeAdapter threeAdapter = new ThreeAdapter(null);
        View inflate = LayoutInflater.from(this).inflate(R.layout.empty, null, false);
        threeAdapter.setEmptyView(inflate);
        threeAdapter.getEmptyView().setId(1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(threeAdapter);
        QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
        quickItemDecoration.getUpdateConfig().addIgnoreViewId(1).update();
        recyclerView.addItemDecoration(quickItemDecoration);

    }

    class ThreeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ThreeAdapter(@Nullable List<String> data) {
            super(R.layout.item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tvText, item);
        }

    }

}