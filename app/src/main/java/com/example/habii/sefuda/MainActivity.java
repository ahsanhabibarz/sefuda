package com.example.habii.sefuda;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karan.churi.PermissionManager.PermissionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List sounds;

    List<Sounds> soundsList;

    int x = 0;

    MediaPlayer mediaPlayer;

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    AutoCompleteTextView searchbox;

    List <String> suggestionarray;

    private long doubleBackToExitPressedOnce;

    ImageView cancel;

    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());


        sounds = new ArrayList();

        mediaPlayer = new MediaPlayer();


        soundsList = new ArrayList<>();

        //permissionManager = new PermissionManager() {

//            @Override
//            public void ifCancelledAndCannotRequest(Activity activity) {
//                super.ifCancelledAndCannotRequest(activity);
//
//                try {
//                    // clearing app data
//                    String packageName = getApplicationContext().getPackageName();
//                    Runtime runtime = Runtime.getRuntime();
//                    runtime.exec("pm clear "+packageName);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                finishAffinity();
//            }
        //};


        //permissionManager.checkAndRequestPermissions(MainActivity.this);


        searchbox = (AutoCompleteTextView)findViewById(R.id.searchBox);

        suggestionarray = new ArrayList<>();


        recyclerView = (RecyclerView)findViewById(R.id.Rsound);

        adapter = new SoundAdapter(soundsList,this,MainActivity.this);

        cancel = (ImageView)findViewById(R.id.cancel);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);


        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        loadSound();



        searchbox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){


                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                    if(searchbox.getText().toString().isEmpty()){

                        cancel.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Missing keyword", Toast.LENGTH_SHORT).show();


                    }else{


                        loadSearch(searchbox.getText().toString());
                        cancel.setVisibility(View.VISIBLE);



                    }


                }

                return false;
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundsList.clear();

                searchbox.setText("");

                cancel.setVisibility(View.GONE);

                loadSound();

                if(searchbox.hasFocus()){

                    searchbox.clearFocus();

                }
            }
        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,suggestionarray);

        searchbox.setThreshold(1);
        searchbox.setAdapter(arrayAdapter);


    }



    public void loadSound(){

        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){

            Sounds sound = null;

            try {
                sound = new Sounds(fields[count].getName(),fields[count].getInt(fields[count]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            soundsList.add(sound);
            suggestionarray.add(fields[count].getName());

            adapter.notifyDataSetChanged();
            x++;
        }

    }


    public void loadSearch(String s){

        soundsList.clear();

        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){

            Sounds sound = null;

            if(fields[count].getName().toString().equals(s)){

                try {
                    sound = new Sounds(fields[count].getName(),fields[count].getInt(fields[count]));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                soundsList.add(sound);
                adapter.notifyDataSetChanged();

            }

        }

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        permissionManager.checkResult(requestCode,permissions,grantResults);
//
//    }


    @Override
    public void onBackPressed() {

        if (searchbox.hasFocus()) {

            searchbox.setText("");

            searchbox.clearFocus();

        } else {

            if (doubleBackToExitPressedOnce + 2000 > System.currentTimeMillis()) {

                finishAffinity();

                super.onBackPressed();
            } else {

                Toast.makeText(getBaseContext(),
                        "Press once again to exit!", Toast.LENGTH_SHORT)
                        .show();
            }
            doubleBackToExitPressedOnce = System.currentTimeMillis();

        }
    }

}
