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

public class ShipsActivity extends AppCompatActivity implements Serializable {
    Ships ships = new Ships();
    int specieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        specieId = data.getInt("specieId", 0);

        drawShips();

    }

    private void drawShips() {
        //Recursos res = new Recursos();
        LinearLayout shipButtons = findViewById(R.id.shipButtons);
        List<Ships> shipList = ships.getOwnShips(this, specieId);
        // Add Planets
        for(Ships ship: shipList) {
            Planets planet = new Planets();
            planet = planet.getPlanetById(this, ship.getPlanet());
            String planetName = planet.getName();
            Button shipButton = new Button(this);
            int res = Game.getResId(ship.getImage(), R.drawable.class);
            shipButton.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            shipButton.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            shipButton.setCompoundDrawablePadding(50);
            shipButton.setPadding(50,20,0,20);
            shipButton.setAllCaps(false);
            shipButton.setBackgroundColor(Color.TRANSPARENT);
            shipButton.setTextColor(Color.WHITE);
            shipButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            shipButton.setText("Nave " + ship.getName() + ", tipo " + ship.getType() +
                    "\nEn " + ship.getTextLocation(this, ship.getLocation()) + " " + planetName );

            Planets finalPlanet = planet;
            shipButton.setOnClickListener(v -> {
                Intent i = new Intent(ShipsActivity.this, PlanetManager.class);
                //i.putExtra("specie", specie);
                //i.putExtra("star", star);
                //i.putExtra("starId", planet.getStar());
                i.putExtra("planet", finalPlanet);
                //i.putExtra("recursos", res);
                i.putExtra("canBuild", false);
                startActivity(i);
            });

            shipButtons.addView(shipButton);
        }
    }

}
