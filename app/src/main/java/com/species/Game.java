package com.species;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public abstract class Game extends AppCompatActivity {

    static int turn;

    public static int advanceTurn(View view){
        //TODO Insertar en Species Detalle el surface y los recursos -- setNewSquares()
        //surface.incTurn();
        //turn = surface.getTurn();
        //surface.decTurns();
        //int turns = surface.getTurns();
        //surface.setCost(this, turns);

        //planet = planet.getPlanetTarget(this, surface.getPlanet());
        //res = res.getRecursosByPlanet(this, planet);
        //planet.setPopulation(this, planet, res);

        turn += 1;
        return turn;

    }


    public static void hideview(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }


    public static void hideviewMenu(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

}
