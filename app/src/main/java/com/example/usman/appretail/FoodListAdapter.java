package com.example.usman.appretail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usman on 2/1/2018.
 */

class FoodListAdapter extends BaseAdapter {

    private CatagoryMainFragment context;
    private  int layout;
    private ArrayList<CatModel> foodsList;

    public FoodListAdapter(CatagoryMainFragment context, int layout, ArrayList<CatModel> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{
                TextView txtName;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        CatModel food = foodsList.get(position);

        holder.txtName.setText(food.getName());

        return row;
    }
}
