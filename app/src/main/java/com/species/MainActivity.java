package com.species;

import static com.species.Game.turn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.species.database.LoadDB;
import com.species.ui.stars.StarsActivity;

public class MainActivity extends AppCompatActivity {

    LoadDB db = new LoadDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Game.hideview(getWindow().getDecorView());

        setContentView(R.layout.activity_main);
    }

    public void onResume(){
        super.onResume();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        turn  = data.getInt("turn", 0);
    }

    public void onPause(){
        super.onPause();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();
    }

    public void runSpecies(View view){
        //TODO Crear dialog borrado de datos
        db.deleteDB();
        db.insertDB();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        data.edit().clear().apply();
        Intent i = new Intent(this, SpeciesActivity.class);
        startActivity(i);
    }

    public void runSector(View view){
        Intent i = new Intent(this, StarsActivity.class);
        startActivity(i);
    }

}
