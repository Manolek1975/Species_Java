package com.species;

import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.species_activity);
        hideView();

        drawButtons();
    }

    private void drawButtons() {
        LinearLayout lin = findViewById(R.id.idSpeciesButtons);
        Species species = new Species();
        List<Species> speciesList = species.getSpecies(this);

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
            //Log.i("species", val.getId() +":"+val.getName());
        }
    }

    public void runStars(Species val){
        int res = getResources().getIdentifier(val.getImage(), "drawable", this.getPackageName());

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setIcon(res)
                .setTitle(val.getName())
                .setMessage("Los humanos son una raza muy prolífica, investigadores incansables, no se arredran" +
                        " ante las dificultades, su capacidad les otorga un gran crecimiento y ventajas en la investigación")
                .setPositiveButton("ACEPTAR", (dialogInterface, i) -> {
                            DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
                    LoadDB db = new LoadDB(this);
                    db.insertStars(width, height);
                    val.setMainSpecie(SpeciesActivity.this, val.getId());
                    Intent intent = new Intent(SpeciesActivity.this, StarsActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("RECHAZAR", (dialogInterface, i) -> {
                    //set what should happen when negative button is clicked
                    //Toast.makeText(getApplicationContext(),"Acción cancelada",Toast.LENGTH_LONG).show();
                })

                .show();
    }

    private void hideView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
