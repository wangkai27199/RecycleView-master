package com.bwei.recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class MainActivity extends Activity implements MainAdapter.OnItemClickListener {

    private IRecyclerView recyclerView;

    private MainAdapter mainAdapter;

    int count = 0;

    private ArrayList<String> list = new ArrayList<String>();
    private LinearLayoutManager linearLayoutManager;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    recyclerView.setRefreshing(false);

                    break;
                case 2:

                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);

                    break;
            }
        }
    };
    private LoadMoreFooterView loadMoreFooterView;
    private int findLastVisibleItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData(0);

        recyclerView = (IRecyclerView) findViewById(R.id.recycleview_id);

        loadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();


        mainAdapter = new MainAdapter(this, list);

        recyclerView.setIAdapter(mainAdapter);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= linearLayoutManager.getItemCount() - 1) {
                        count++;
                        initData(count);
                    }
                }
            }
        });*/

        //设置布局管理器
//        LinearLayoutManager.VERTICAL 表示水平垂直滑动
//                false true     layouts from end to start. else

//
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

//添加分割线
//        recyclerView.addItemDecoration
//        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
//                        .sizeResId(R.dimen.divider)
//                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());

        // 为 item add remove 时增加动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

//
//        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position, View view) {
//
//                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onItemLongClickListener(int position, View view) {
//                Toast.makeText(MainActivity.this, "long " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        ListView listview  ;
//
//        AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        } ;
//        listview.setOnItemClickListener(listListener);

//        MainAdapter.OnItemClickListener  listener = new MainAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position, View view) {
//
//            }
//
//            @Override
//            public void onItemLongClickListener(int position, View view) {
//
//            }
//        } ;
//        mainAdapter.setOnItemClickListener(listener);


        mainAdapter.setOnItemClickListener(this);


        //表格管理器
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
//
//        recyclerView.setLayoutManager(gridLayoutManager);


        //StaggeredGridLayoutManager 瀑布流
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);


//        startActivity(new Intent(this,MultipleActivity.class));

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
                    //加载更多
                    for (int i = findLastVisibleItemPosition;i<findLastVisibleItemPosition + 20;i++){
                        list.add(i+"");
                    }
                    mainAdapter.notifyDataSetChanged();


                    /*System.out.println("findLastCompletelyVisibleItemPosition = " + findLastCompletelyVisibleItemPosition);
                    System.out.println("findFirstCompletelyVisibleItemPosition = " + findFirstCompletelyVisibleItemPosition);
                    System.out.println("findFirstVisibleItemPosition = " + findFirstVisibleItemPosition);
                    System.out.println("findLastVisibleItemPosition = " + findLastVisibleItemPosition);*/

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                recyclerView.setRefreshing(true);


                handler.sendEmptyMessageDelayed(1, 2000);

            }
        });


        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                handler.sendEmptyMessageDelayed(2, 2000);
            }
        });

    }

    private void initData(int num) {
        for (int i = num; i < 20; i++) {
            list.add(i + "");
        }
    }


    @Override
    public void onItemClickListener(int position, View view) {

        mainAdapter.add(position);


    }

    @Override
    public void onItemLongClickListener(int position, View view) {

        mainAdapter.remove(position);

    }
}
