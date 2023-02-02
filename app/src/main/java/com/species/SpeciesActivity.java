package com.species;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class SpeciesActivity extends AppCompatActivity {
    Species specie = new Species();
    Main main = new Main();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.species_activity);
        View view = getWindow().getDecorView();
        main.hideview(view);

        drawButtons();
    }

    private void drawButtons() {
        LinearLayout lin = findViewById(R.id.idSpeciesButtons);
        List<Species> speciesList = specie.getSpecies(this);

        for(Species val : speciesList){
            int image = getResources().getIdentifier(val.getImage(), "drawable", this.getPackageName());
            Button button = new Button(this);
            button.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            button.setCompoundDrawablesWithIntrinsicBounds(image, 0, 0, 0);
            button.setText(val.getName());
            button.setCompoundDrawablePadding(50);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(v -> runStars(val));
            button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            lin.addView(button);
        }
    }

    public void runStars(Species val){
        int res = getResources().getIdentifier(val.getImage(), "drawable", this.getPackageName());

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
            .setIcon(res)
            .setTitle(val.getName())
            .setMessage(val.getDesc())
            .setPositiveButton("ACEPTAR", (dialogInterface, i) -> {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                LoadDB db = new LoadDB(this);
                db.insertStars(width, height);
                db.insertPlanets();
                db.insertBuilds();
                val.setMainSpecie(SpeciesActivity.this, val.getId());
                specie = val;
                Intent intent = new Intent(SpeciesActivity.this, StarsActivity.class);
                startActivity(intent);
            })
            .setNegativeButton("RECHAZAR", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(),"Selecciona una specie",Toast.LENGTH_SHORT).show();
            })

            .show();
            alertDialog.isShowing();
    }

    public void onPause(){
        super.onPause();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("specieId", specie.getId());
        edit.apply();
    }

}
