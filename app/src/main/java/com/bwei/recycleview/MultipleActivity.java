package com.bwei.recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class MultipleActivity extends Activity implements MainAdapter.OnItemClickListener {

    private RecyclerView recyclerView;


    private MultipleAdapter mainAdapter;


    private ArrayList<String> list = new ArrayList<String>();
    private LinearLayoutManager linearLayoutManager;
    private int findLastVisibleItemPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }

//        recyclerView = (RecyclerView) findViewById(R.id.recycleview_id);

        mainAdapter = new MultipleAdapter(this, list);

        recyclerView.setAdapter(mainAdapter);


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);



//添加分割线
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
                        .build());


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == newState) {

                    //当前滚动 停止
                    int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                    for (int i = findLastVisibleItemPosition;i<findLastVisibleItemPosition + 20;i++){
                        list.add(i+"");
                    }
                    mainAdapter.notifyDataSetChanged();
                    System.out.println("findLastCompletelyVisibleItemPosition = " + findLastCompletelyVisibleItemPosition);
                    System.out.println("findFirstCompletelyVisibleItemPosition = " + findFirstCompletelyVisibleItemPosition);
                    System.out.println("findFirstVisibleItemPosition = " + findFirstVisibleItemPosition);
                    System.out.println("findLastVisibleItemPosition = " + findLastVisibleItemPosition);

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


    }


    @Override
    public void onItemClickListener(int position, View view) {

    }

    @Override
    public void onItemLongClickListener(int position, View view) {

    }
}
