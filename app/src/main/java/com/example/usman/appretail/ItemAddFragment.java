package com.example.usman.appretail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Fahad Tanwir on 1/26/2018.
 */

public class ItemAddFragment extends Fragment {

    Spinner spinner;
    private Button btnAddItem;
    private EditText etItmNm,etItmprc;
    //   ImageView catag_image;

   /* final int REQUEST_CODE_GALLERY = 999;*/

    private DBHelper dbHelper;
    // SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=(inflater.inflate(R.layout.item_add_layout,container,false));
        btnAddItem=(Button)v.findViewById(R.id.BtnAddItem);
        etItmNm=(EditText) v.findViewById(R.id.etItemName);
        etItmprc=(EditText) v.findViewById(R.id.etItemPrice);
        spinner=(Spinner)v.findViewById(R.id.spnrCatag);
      //  spinner.getOnItemSelectedListener();

        loadSpinnerData();

        //     catag_image=(ImageView)v.findViewById(R.id.cat_image);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loadSpinnerData();
                  //  String cat_id=spinner.getSelectedItem().toString();
                    String cat_id= String.valueOf(spinner.getSelectedItem());
                    //Toast.makeText(getActivity().getApplicationContext(),"value of seleected item:"+cat_id,Toast.LENGTH_LONG).show();
                    int cat_val= dbHelper.catagoryRefer(cat_id);
                    String inm = etItmNm.getText().toString();
                    System.out.println(inm);

                    String iprc = etItmprc.getText().toString();
                    System.out.println(iprc);
                    dbHelper.insertItem(inm, iprc,cat_val);

                    Toast.makeText(getActivity().getApplicationContext(), "Item Inserted Successfully!", Toast.LENGTH_LONG).show();
                    etItmNm.setText("");
                    etItmprc.setText("");
                    //  catag_image.setImageResource(R.mipmap.ic_launcher);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(getActivity().getApplicationContext());

        // Spinner Drop down elements
        List<String> CatSpnr = db.getAllCatagorySpinner();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, CatSpnr);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

    }


}
