package com.bwei.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by muhanxi on 17/5/23.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CViewHolder> {


    private Context mContext;
    private ArrayList<String> mList ;
    private LayoutInflater inflater ;
    public MainAdapter(Context context, ArrayList<String> list){

        this.mContext = context;
        this.mList = list ;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.main_item,parent,false);

        CViewHolder viewHolder = new CViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, final int position) {


//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.textView.getLayoutParams();
//        Random random = new Random();
//        params.height = random.nextInt(500);
//        holder.textView.setLayoutParams(params);
//
//        if(position % 2 == 0){
//            holder.textView.setBackgroundColor(Color.GRAY);
//        } else{
//            holder.textView.setBackgroundColor(Color.BLUE);
//        }
        holder.textView.setText(mList.get(position));


        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position,v);
            }
        });


        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClickListener(position,v);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;

        public CViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);

        }
    }




    public void add(int position){

        mList.add(position," insert   " + position);
//        notifyDataSetChanged();

        // 在指定的位置 插入item， 局部刷新
        notifyItemInserted(position);

        //刷新指定区域 start end
        notifyItemRangeChanged(position,mList.size());


    }


    public void remove(int position){

        mList.remove(position);

        notifyItemRemoved(position);
//        notifyDataSetChanged();

        notifyItemRangeChanged(position,mList.size());

    }





    interface OnItemClickListener {

        void onItemClickListener(int position,View view);
        void onItemLongClickListener(int position,View view);
    }


    public OnItemClickListener listener ;



    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
