package com.species;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class StarsActivity extends AppCompatActivity implements Serializable {

    String star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stars_activity);
        hideView();
        Intent i = getIntent();
        star = (String)i.getSerializableExtra("star");
        TextView starView = findViewById(R.id.nameStar);
        starView.setText(star);
        //drawPlanets();

    }

/*    private void drawPlanets() {
        Planets planets = new Planets();
        List<Planets> worlds = planets.getOwnPlanets(this, specie);
        // Add Planets
        LinearLayout planetButtons = findViewById(R.id.planetButtons);
        for(Planets planet: worlds) {
            Button planetButton = new Button(this);
            String iconPlanet = getIconPlanet(planet.getType());

            int resImage = this.getResources().getIdentifier(iconPlanet, "drawable", this.getPackageName());
            planetButton.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            planetButton.setCompoundDrawablesWithIntrinsicBounds(resImage, 0, 0, 0);
            planetButton.setPadding(50,20,0,20);
            planetButton.setAllCaps(false);
            planetButton.setText(planet.getName());
            planetButton.setBackgroundColor(Color.TRANSPARENT);
            planetButton.setTextColor(Color.WHITE);
            planetButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

            planetButton.setOnClickListener(v -> {
                Intent i = new Intent(PlanetsActivity.this, PlanetManager.class);
                i.putExtra("specie", specie);
                i.putExtra("star", star);
                i.putExtra("planet", planet);
                i.putExtra("recursos", res);
                i.putExtra("canBuild", false);
                startActivity(i);
            });

            planetButtons.addView(planetButton);
        }
    }*/

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

    private void hideView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
    }
}
