package com.example.usman.appretail;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements Communicator {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CartBarFragment cbf= new CartBarFragment();
        CatagoryMainFragment cmf=new CatagoryMainFragment();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.gridGroup,cmf,"cmf");
        transaction.add(R.id.cartGroup,cbf,"cbf");

        transaction.commit();


        //  logout=(Button)findViewById(R.id.btnlogout);
        //      logout.setOnClickListener(this);
    }


    @Override
    public void respond(String data) {
        //yahn tm jis item fragment may data pass krvana hy us ko call kro gy data jo tm ne catagory k fragment se recive kra hy.... see example below


        /*FragmentManager manager=getFragmentManager();
        MyFragement2 f2 = (MyFragement2) manager.findFragmentById(R.id.fragment2);
        f2.changeData(data);
*/

        //yahn tm jis cart fragment may data pass krvana hy us ko call kro gy data jo tm ne item k fragment se recive kra hy.... see example below


        /*FragmentManager manager=getFragmentManager();
        MyFragement3 f3 = (MyFragement3) manager.findFragmentById(R.id.fragment3);
        f3.changeCartDetail(data);
*/


    }
}
