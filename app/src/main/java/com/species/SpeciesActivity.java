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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species);
        View view = getWindow().getDecorView();
        Game.hideview(view);

        drawButtons();
    }

    private void drawButtons() {
        LinearLayout lin = findViewById(R.id.idSpeciesButtons);
        List<Species> speciesList = specie.getSpecies(this);

        for(Species val : speciesList){
            int res = Game.getResId( val.getImage(), R.drawable.class );
            Button button = new Button(this);
            button.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            button.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            button.setCompoundDrawablePadding(50);
            button.setText(val.getName());
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTextColor(Color.WHITE);
            button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            button.setOnClickListener(v -> runStars(val));
            lin.addView(button);
        }
    }

    public void runStars(Species val){
        int res = Game.getResId( val.getImage(), R.drawable.class );
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
            .setNegativeButton("RECHAZAR", (dialogInterface, i) ->
                    Toast.makeText(getApplicationContext(),"Selecciona una specie",Toast.LENGTH_SHORT).show())

            .show();
            alertDialog.isShowing();
    }

    public void onPause(){
        super.onPause();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("specieId", specie.getId());
        edit.putInt("turn", 0);
        edit.apply();
    }

}
