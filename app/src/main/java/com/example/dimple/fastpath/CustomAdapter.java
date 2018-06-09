package com.example.dimple.fastpath;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    public static ArrayList<Model> modelArrayList;


    public CustomAdapter(Context context, ArrayList<Model> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_single_listitem, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.item = (TextView) convertView.findViewById(R.id.itempurchase);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }


       // holder.checkBox.setText("Checkbox " + position);
        holder.item.setText(modelArrayList.get(position).getitems());

        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());
        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.itempurchase);
                Integer pos = (Integer) holder.checkBox.getTag();
              //  Toast.makeText(context, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();

                if (modelArrayList.get(pos).getSelected()) {
                    modelArrayList.get(pos).setSelected(false);
                } else {
                    modelArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView item;

    }
}
