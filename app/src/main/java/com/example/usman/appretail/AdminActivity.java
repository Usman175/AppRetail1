package com.example.usman.appretail;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Fahad Tanwir on 1/25/2018.
 */



public class AdminActivity extends Activity  {

    private Button addCatag,addItem;
    FragmentManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        addItem=findViewById(R.id.btnList);
        addCatag=findViewById(R.id.btnCatag);
        /*btnDb=findViewById(R.id.btnDB);*/
        manager=getFragmentManager();

    }


    public void btnCatag(View v){
        CatagAddFagement ctfrag= new CatagAddFagement();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.cat_group,ctfrag,"catag");
        transaction.commit();
    }

    public void btnList(View v){
        ItemAddFragment ais=new ItemAddFragment();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.cat_group,ais);
        transaction.commit();
    }
 }
