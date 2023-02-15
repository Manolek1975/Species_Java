package com.species;

import static com.species.Game.turn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlanetManager extends AppCompatActivity implements Serializable {
    public static final int IMAGE_TOP = 20; // Distancia superior de la imagen
    private int starId;
    private Planets planet;
    private Surfaces surface;
    private int endTurn;
    private List<Surfaces> surfaceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_manager);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        Intent i = getIntent();
        planet = (Planets)i.getSerializableExtra("planet");
        starId = planet.getStar();
        //build = (Builds)i.getSerializableExtra("build");
        surface =  new Surfaces();
        surfaceList = surface.getBuildings(this, planet.getId());

        //
        setPlanet();
    }

    private void setPlanet() {
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textEndTurn);
        TextView planetName = findViewById(R.id.planetName);
        TextView planetType = findViewById(R.id.planetType);
        //Draw planet
        planetName.setText(planet.getName());
        String type = planet.getNameType(planet.getType());
        planetType.setText(String.format("Planeta %s", type));
        ImageView imgPlanet = findViewById(R.id.imagePlanet);
        String img = planet.getImagePlanet(this, planet.getType());
        int res = Game.getResId(img, R.drawable.class);
        //int res = this.getResources().getIdentifier(img, "drawable", this.getPackageName());
        imgPlanet.setImageResource(res);
        //Draw proyecto
        Surfaces proyecto = surface.getProyecto(this, planet.getId());
        if (proyecto != null){
            Builds build = new Builds();
            build = build.getBuildById(this, proyecto.getBuild());
            ImageButton proyectoButton = findViewById(R.id.proyectoButton);
            String imgBuild = build.getImage();
            int resBuild = Game.getResId(imgBuild, R.drawable.class);
            //int resBuild = this.getResources().getIdentifier(imgBuild, "drawable", this.getPackageName());
            proyectoButton.setImageResource(resBuild);
            endTurn = proyecto.getCost();
            textProyecto.setText(build.getName());
            textProyectoTurnos.setText(String.format(Locale.US, "%d turnos", endTurn));
            //surface.insertSurface(this, planet.getId(), build.getId(), endTurn, 0);
        } else {
            textProyecto.setText(R.string.sin_proyecto);
        }
        //TODO Draw Orbitales
        drawOrbital();
        drawBuilds();
        showRecursos();
    }

    private void drawOrbital() {
        Ships ships = new Ships();
        LinearLayout lin = findViewById(R.id.orbitalLayout);
        List<Ships> shipList = ships.getPlanetShips(this, planet.getId());
        for (Ships ship : shipList){
            Button button =  new Button(this);
            int res = Game.getResId(ship.getImage(), R.drawable.class);
            button.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            button.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            button.setCompoundDrawablePadding(50);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setOnClickListener(v -> Game.leaveOrbit(this, ship, planet.getId()));
            lin.addView(button);

        }

    }

    private void drawBuilds() {
        LinearLayout surfaceLayout = findViewById(R.id.surfaceLayout);
        for (Surfaces surface : surfaceList){
            Builds build = new Builds();
            Button imgBuild = new Button(this);
            build = build.getBuildById(this, surface.getBuild());
            String imgString = build.getImageBuild(this, build.getId());
            int resBuild = Game.getResId(imgString, R.drawable.class);
            //int resBuild = this.getResources().getIdentifier(imgString, "drawable", this.getPackageName());
            imgBuild.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            imgBuild.setCompoundDrawablesWithIntrinsicBounds(resBuild, 0, 0, 0);
            imgBuild.setCompoundDrawablePadding(50);
            imgBuild.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            imgBuild.setBackgroundColor(Color.TRANSPARENT);
            imgBuild.setTextColor(Color.WHITE);
            imgBuild.setText(build.getName());

            surfaceLayout.addView(imgBuild);
        }
    }

    public void advanceTurn(View view) {
        TextView textTurn = findViewById(R.id.textTurn);
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textEndTurn);

        int turn = Game.advanceTurn(view);
        textTurn.setText(String.valueOf(turn));
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();

        Surfaces square = new Surfaces();
        square.updateSquare(this);

        List<Surfaces> surfaceList = surface.getTurns(this);
        if(surfaceList.isEmpty()) return;
        for(Surfaces val : surfaceList) {
            endTurn = val.getCost();
            textProyectoTurnos.setText(String.format("%s turnos", endTurn));
            surface = val;
        }

        ImageButton buildButton = findViewById(R.id.proyectoButton);
        MutableLiveData<Integer> listen = new MutableLiveData<>();
        listen.setValue(endTurn);
        listen.observe(this, changedValue -> {
            if(endTurn == 0){
                square.updateSquare(this);
                buildButton.setImageDrawable(null);
                textProyectoTurnos.setText("");
                textProyecto.setText(R.string.sin_proyecto);
                //res.increaseRecursos(this, planet, build.getBuildById(this, surface.getBuild()), surface.getColor());
                Game.buildCompleted(this, surface);
                play();
                showRecursos();
            }
        });
    }

    public void advanceFast(View view) {
        TextView textTurn = findViewById(R.id.textTurn);
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textEndTurn);
        Surfaces square = new Surfaces();
        do {
            int turn = Game.advanceTurn(view);
            textTurn.setText(String.valueOf(turn));
            square.updateSquare(this);
            List<Surfaces> surfaceList = surface.getTurns(this);
            if(surfaceList.isEmpty()) return;
            for(Surfaces val : surfaceList) {
                endTurn = val.getCost();
                textProyectoTurnos.setText(String.format("%s turnos", endTurn));
                surface = val;
            }

        } while(surface.getCost() > 0);

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();

        ImageButton buildButton = findViewById(R.id.proyectoButton);
        MutableLiveData<Integer> listen = new MutableLiveData<>();
        listen.setValue(endTurn);
        listen.observe(this, changedValue -> {
            if(endTurn == 0){
                square.updateSquare(this);
                buildButton.setImageDrawable(null);
                textProyectoTurnos.setText("");
                textProyecto.setText(R.string.sin_proyecto);
                //res.increaseRecursos(this, planet, build.getBuildById(this, surface.getBuild()), surface.getColor());
                Game.buildCompleted(this, surface);
                play();
                showRecursos();
            }
        });
    }

    private void showRecursos() {
        Locale sp = new Locale("es", "ES");
        Recursos recursos = new Recursos();
        recursos = recursos.getRecursosByPlanet(this, planet.getId());
        TextView textIndustry = findViewById(R.id.textIndustry);
        TextView textProsperity = findViewById(R.id.textProsperity);
        TextView textResearch = findViewById(R.id.textResearch);
        TextView textPopulation = findViewById(R.id.textPopulation);
        TextView textDefence = findViewById(R.id.textDefence);
        textIndustry.setText(String.format(sp, "Industria: %s", recursos.getIndustry()));
        textProsperity.setText(String.format(sp, "Nutrientes: %s", recursos.getProsperity()));
        textResearch.setText(String.format("Ciencia: %s", recursos.getResearch()));
        textPopulation.setText(String.format("Población: %s", recursos.getPopulation()));
        textDefence.setText(String.format("Defensa: %s", recursos.getDefence()));
    }

    protected void play() {
        super.onStart();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.factory);
        mp.start();
    }

    public void onPause(){
        super.onPause();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();
    }

    public void onResume(){
        super.onResume();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        turn  = data.getInt("turn", 0);
        TextView textTurn = findViewById(R.id.textTurn);
        textTurn.setText(String.valueOf(turn));
    }

    public void runSistem(View view) {
        Intent i =  new Intent(this, SistemActivity.class);
        i.putExtra("starId", starId);
        startActivity(i);
    }

    public void runBuilds(View view) {
        Intent i =  new Intent(this, BuildsActivity.class);
        i.putExtra("planet", planet);
        startActivity(i);
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
