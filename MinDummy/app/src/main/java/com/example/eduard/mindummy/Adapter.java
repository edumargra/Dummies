package com.example.eduard.mindummy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eduard on 28/06/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Thing> mDataset;
    private MyItemClickListener mItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView name;
        private MyItemClickListener mListener;
        private int id;

        public ViewHolder(View v, MyItemClickListener listener) {
            super(v);
            name = (TextView) itemView.findViewById(R.id.name);


            this.mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(this.id);
            }
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter(ArrayList<Thing> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        // create a new layout
        View layout = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        ViewHolder vh = new ViewHolder(layout, mItemClickListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        Thing value = getThing(position);
        // - replace the contents of the view with that element
        holder.name.setText(value.getName());
        holder.id = position;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public Thing getThing(int position) {
        return mDataset.get(position);
    }

    public void updateList(ArrayList<Thing> data) {
        if(!data.equals(mDataset)) {
            mDataset = data;
            notifyDataSetChanged();
        }
    }
}
