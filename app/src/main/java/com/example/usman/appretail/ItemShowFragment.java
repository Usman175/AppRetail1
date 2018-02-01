package com.example.usman.appretail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fahad Tanwir on 1/31/2018.
 */

public class ItemShowFragment extends Fragment implements View.OnClickListener {
    //private Button btn;

    private DBHelper dbHelper;

    // yea mai ne Communicator interface ka instance banaya hy... nechay usko onActivityCreated() may initilize krvaya hy.... check it
    Communicator comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.item_show_layout,container,false);

        // See Example below

        //btn=(Button)findbyId(........)

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        comm = (Communicator) getActivity();

        // yahn logic banay ge commented logic example deako nechay



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

    // yeah ap ka data activity se aye ga jo ap ne catagory k fragment se pass kra tha

    /*public void changeData(String data){
        this.data = data;
        tv.setText(data);

    }*/


    @Override
    public void onClick(View view) {

        // Yahn tm pass krvao gy data is frament se activity p.... See example below

        // comm.response("Clicked "+counter+" time(s)!!");
    }
}
