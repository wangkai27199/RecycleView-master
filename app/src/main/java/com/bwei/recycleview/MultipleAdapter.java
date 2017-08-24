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

public class MultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<String> mList ;
    private LayoutInflater inflater ;


//    viewType 定义3中
    private int header = 0;
    private int content = 1 ;
    private int footer = 2 ;

    public MultipleAdapter(Context context, ArrayList<String> list){

        this.mContext = context;
        this.mList = list ;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == 0){


            View view = inflater.inflate(R.layout.header,parent,false);

            HViewHolder viewHolder = new HViewHolder(view);

            return viewHolder ;

        } else if(viewType == footer){
            View view = inflater.inflate(R.layout.footer,parent,false);

            FViewHolder viewHolder = new FViewHolder(view);

            return viewHolder ;
        } else {
            View view = inflater.inflate(R.layout.main_item,parent,false);

            CViewHolder viewHolder = new CViewHolder(view);

            return viewHolder ;
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof HViewHolder){

            HViewHolder hViewHolder = (HViewHolder) holder ;

            hViewHolder.textView.setText("我是header");
        }


        if(holder instanceof FViewHolder){
            FViewHolder fViewHolder = (FViewHolder) holder ;
            fViewHolder.textView.setText("footer ");

        }


        if(holder instanceof CViewHolder){
            CViewHolder cViewHolder = (CViewHolder) holder ;
            cViewHolder.textView.setText(mList.get(position-1));
        }


    }


    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return header;
        } else if(position == mList.size() + 1){
            return footer;
        }else {
            return content;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size()+2;
    }

    class HViewHolder extends RecyclerView.ViewHolder {

        TextView textView ;

        public HViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.header);

        }
    }

    class FViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;

        public FViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.footer);

        }
    }

    class CViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;

        public CViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);

        }
    }




    interface OnItemClickListener {

        void onItemClickListener(int position, View view);
        void onItemLongClickListener(int position, View view);
    }


    public OnItemClickListener listener ;



    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
