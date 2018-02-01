package com.example.usman.appretail;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Fahad Tanwir on 1/31/2018.
 */

public class CatagoryMainFragment extends Fragment implements View.OnClickListener {

    //private Button btn;

    private DBHelper dbHelper;

    // yea mai ne Communicator interface ka instance banaya hy... nechay usko onActivityCreated() may initilize krvaya hy.... check it
    Communicator comm;

    GridView gridView;
    ArrayList<CatModel> list;
    FoodListAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.catagory_show_layout,container,false);

        // See Example below
        GridView gridView = (GridView) v.findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new FoodListAdapter(this, R.layout.cat_list, list);
        gridView.setAdapter(adapter);

        //btn=(Button)findbyId(........)

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        comm = (Communicator) getActivity();

        dbHelper=new DBHelper(getActivity());
        // yahn logic banay ge commented logic example deako nechay


        // get all data from sqlite
        Cursor cursor = dbHelper.getAllCategory("SELECT * FROM catagory;");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            list.add(new CatModel(id,name));
        }
        adapter.notifyDataSetChanged();


        /*dbHelper=new DBHelper(getActivity());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String cnm=etCatag.getText().toString();
                    System.out.println(cnm);
                    dbHelper.insertCatag(cnm);

                    Toast.makeText(getActivity().getApplicationContext(),"INSERTED CATAG",Toast.LENGTH_LONG).show();
                    etCatag.setText("");
                    //  catag_image.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/


    }

    @Override
    public void onClick(View view) {

        // Yahn tm pass krvao gy data is frament se activity p.... See example below

       // comm.response("Clicked "+counter+" time(s)!!");
    }
}
