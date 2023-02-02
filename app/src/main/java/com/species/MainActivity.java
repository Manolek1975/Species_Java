package com.species;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LoadDB db = new LoadDB(this);
    Main main = new Main();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        View view = getWindow().getDecorView();
        main.hideview(view);
    }

    public void runSpecies(View view){
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
