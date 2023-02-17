package com.species;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class PlanetsActivity extends AppCompatActivity implements Serializable {
    Planets planets = new Planets();
    int specieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planets_activity);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        specieId = data.getInt("specieId", 0);

        drawPlanets();

    }

    private void drawPlanets() {
        //Recursos res = new Recursos();
        LinearLayout planetButtons = findViewById(R.id.planetButtons);
        List<Planets> planetList = planets.getOwnPlanets(this, specieId);
        // Add Planets
        for(Planets planet: planetList) {
            Button planetButton = new Button(this);
            String iconPlanet = planet.getIconPlanet(this, planet.getType());
            int res = Game.getResId(iconPlanet, R.drawable.class);
            //int resImage = this.getResources().getIdentifier(iconPlanet, "drawable", this.getPackageName());
            planetButton.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            planetButton.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            planetButton.setCompoundDrawablePadding(50);
            planetButton.setPadding(50,20,0,20);
            planetButton.setAllCaps(false);
            planetButton.setText(planet.getName());
            planetButton.setBackgroundColor(Color.TRANSPARENT);
            planetButton.setTextColor(Color.WHITE);
            planetButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

            planetButton.setOnClickListener(v -> {
                Intent i = new Intent(PlanetsActivity.this, PlanetManager.class);
                //i.putExtra("specie", specie);
                //i.putExtra("star", star);
                i.putExtra("starId", planet.getStar());
                i.putExtra("planet", planet);
                //i.putExtra("recursos", res);
                i.putExtra("canBuild", false);
                startActivity(i);
            });

            planetButtons.addView(planetButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem option){
        TopMenu menu = new TopMenu(this);
        menu.onOptionsItemSelected(option);
        return false;
    }

}
