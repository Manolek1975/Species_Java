package com.species;

import static android.icu.text.ListFormatter.Type.OR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Range;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanetManager extends AppCompatActivity implements Serializable {

    Main main = new Main();
    public static final int IMAGE_TOP = 20; // Distancia superior de la imagen
    private Stars star = new Stars();
    private Planets planet;
    private Builds build;
    private Surfaces surface;
    private Recursos res;
    private Point buildPoint;
    private int buildX, buildY;
    private Boolean canBuild;
    private LinearLayout lin;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_manager);
        View view = getWindow().getDecorView();
        main.hideViewMenu(view);

        res = new Recursos();
        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);
        planet = (Planets)i.getSerializableExtra("planet");
        build = (Builds)i.getSerializableExtra("build");
        canBuild = (Boolean)i.getSerializableExtra("canBuild");
        planet = (Planets)i.getSerializableExtra("planet");

        lin = findViewById(R.id.planetLayout);
        img = findViewById(R.id.planetView);
        //Log.i("PlanetManager", "Star: " + planet.getStar() + " Planet: " + planet.getName());

        setPlanet();

    }

    protected void play() {
        //super.onStart();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.factory);
        mp.start();
    }

    private void setPlanet() {
        TextView name = findViewById(R.id.planetName);
        TextView desc = findViewById(R.id.planetDesc);
        TextView msg =  findViewById(R.id.textMessage);
        TextView textProyecto =  findViewById(R.id.textProyecto);
        Button proyecto = findViewById(R.id.buildButton);

        String nameType = planet.getNameType(planet.getType());
        String nameSize = planet.getNameSize(planet.getSize());
        name.setText(planet.getName());
        desc.setText(String.format("Planeta %s %s", nameSize, nameType));

        Bitmap fondo = Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        drawPlanet(fondo, bitmap, canvas, planet.getSize());

        surface = new Surfaces();
        int explore = planet.getExplore();
        boolean origin = surface.getOrigin(this); // Comprueba si hay Colonia Base

        if(explore == 0){
            msg.setText(R.string.explore);
            textProyecto.setEnabled(false);
            proyecto.setEnabled(false);
            Log.i("NO_EXPLORADO", explore+"");
        } else if(origin){
            msg.setText(R.string.proyecto);
            Log.i("ORIGEN", origin+"");
            drawSurface();
        } else {
            msg.setText(R.string.proyecto);
            setNewSurface();
        }
    }

    private void setNewSurface() {
        Paint color = new Paint();
        res.insertRecursos(this, planet);
        List<Point> coords = surface.getSquares(this, planet.getSize());
        for(Point point : coords){
            surface.setPlanet(planet.getName());
            if(point.equals(550, 550)){
                surface.setBuild("Colonia Base");
                surface.setTurns(0);
            } else {
                surface.setBuild(null);
            }
            surface.setX(point.x);
            surface.setY(point.y);
            color = surface.setSquareColor(color, planet.getType());
            surface.setColor(color.getColor());
            surface.setSquares(this, surface);
        }
        drawSurface();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void drawSurface() {
        showRecursos();
        // Draw planet
        Paint color = new Paint();
        color.setStyle(Paint.Style.STROKE);
        color.setStrokeWidth(5);
        Bitmap fondo = Bitmap.createBitmap(1080, 1056, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        drawPlanet(fondo, bitmap, canvas, planet.getSize());

        // Draw squares
        List<Surfaces> squares = surface.getSurfaces(this, planet.getName());
        List<Surfaces> buildList = surface.getBuildings(this, planet.getName());
        for(Surfaces point : buildList){
            buildPoint = new Point(point.getX(), point.getY());
        }
        for(Surfaces surface : squares){
            int i = 1;
            List<Point> availables = this.setAvailables();
            for(Point val : availables){
                if (val.x == surface.getX() && val.y == surface.getY()) {
                    i = 5;
                    break;
                }
            }
            Path wallpath = new Path();
            wallpath.reset();
            wallpath.moveTo(surface.getX() - 20*i, surface.getY());
            wallpath.lineTo(surface.getX(), surface.getY() - 10*i);
            wallpath.lineTo(surface.getX() + 20*i, surface.getY());
            wallpath.lineTo(surface.getX(), surface.getY() + 10*i);
            wallpath.lineTo(surface.getX() - 20*i, surface.getY());
            color.setColor(surface.getColor());
            for(Point val : availables){
                if(val.x == surface.getX() && val.y == surface.getY()){
                    color.setStyle(Paint.Style.FILL);
                    color.setAlpha(80);
                }
            }
            canvas.drawPath(wallpath, color);
            color.setStyle(Paint.Style.STROKE);
        }
        img.setImageBitmap(bitmap);

        for(Surfaces val : buildList) {
            // Draw Builds
            Builds newBuild = new Builds();
            String imageBuild = newBuild.getImageBuild(this, val.getBuild());
            int resBuild = this.getResources().getIdentifier(imageBuild, "drawable", this.getPackageName());
            Bitmap buildCenter = BitmapFactory.decodeResource(getResources(), resBuild);
            canvas.drawBitmap(buildCenter,
                    val.getX() - (buildCenter.getWidth() >> 1),
                    val.getY() - (buildCenter.getHeight() >> 1) - IMAGE_TOP, new Paint());
        }
        img.setImageBitmap(bitmap);
        if(img.getParent() != null) {
            ((ViewGroup) img.getParent()).removeView(img);
        }

        img.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                int x = (int)event.getX();
                int y = (int)event.getY();
                for(Surfaces point : buildList){
                    buildPoint = new Point(point.getX(), point.getY());
                }
                for(Surfaces surface : squares){
                    Range<Integer> rangoX = Range.create(x - 80,x + 80);
                    Range<Integer> rangoY = Range.create(y -40,y + 40);
                    if(rangoX.contains(surface.getX()) && rangoY.contains(surface.getY())){
                        buildX = surface.getX();
                        buildY = surface.getY();
                        List<Point> availables = setAvailables();
                        for(Point val : availables){
                            if(val.x == buildX && val.y == surface.getY() && canBuild ){
                                if(surface.getColor() == Color.BLACK && build.getId() != 6) break;
                                //if(surface.getBuild() != null) break;
                                int resImage = getResources().getIdentifier(build.getImage(), "drawable", getPackageName());
                                Bitmap buildCenter = BitmapFactory.decodeResource(getResources(), resImage);
                                canvas.drawBitmap(buildCenter,
                                        buildX - (buildCenter.getWidth() >> 1),
                                        buildY - (buildCenter.getHeight() >> 1) - IMAGE_TOP, new Paint());
                                // TODO Mostrar squares availables
                                //res.increaseRecursos(PlanetManager.this, planet, build, surface.getColor());
                                surface.setBuilding(PlanetManager.this, surface, build);
                                play();
                                canBuild = false;
                                showRecursos();
                                break;
                            }
                        }
                    }
                }
            }
            img.setImageBitmap(bitmap);
            return true;
        });
        lin.addView(img);

    }

    private void drawPlanet(Bitmap fondo, Bitmap bitmap, Canvas canvas, int size) {
        String imagePlanet = planet.getImagePlanet(this, planet.getType());
        int resImage = this.getResources().getIdentifier(imagePlanet, "drawable", this.getPackageName());
        Bitmap planetCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizePlanet = Bitmap.createScaledBitmap(planetCenter, size*230, size*230, true);
        // Draw Planet
        canvas.drawBitmap(fondo, new Matrix(), null);
        canvas.drawBitmap(resizePlanet,
                (fondo.getWidth() - resizePlanet.getWidth()) >> 1,
                (fondo.getHeight() - resizePlanet.getHeight()) >> 1, new Paint());
        img.setImageBitmap(bitmap);
    }

    private void showRecursos() {
        // Draw recursos
        int numPopulation = 0;
        TextView industry = findViewById(R.id.textIndustry);
        TextView prosperity = findViewById(R.id.textProsperity);
        TextView research = findViewById(R.id.textResearch);
        TextView population = findViewById(R.id.textPopulation);
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textProyectoTurnos);
        TextView message = findViewById(R.id.textMessage);
        Button proyecto = findViewById(R.id.buildButton);

        List<Recursos> recursos = res.getRecursos(this, planet);
        for (Recursos val : recursos) {
            industry.setText(String.format("Industria: %s", val.getIndustry()));
            prosperity.setText(String.format("Alimentos: %s", val.getProsperity()));
            research.setText(String.format("Ciencia: %s", val.getResearch()));
            population.setText(String.format("Población: %s", val.getPopulation()));
            numPopulation = val.getPopulation();
        }

        // Draw population
        Paint color = new Paint();
        int size = planet.getSize();
        int room = res.getRoom(size);
        ImageView pop = findViewById(R.id.viewPopulation);
        int resImage = getResources().getIdentifier("human", "drawable", getPackageName());
        Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.population_view);
        Bitmap image = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resultingBitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(resultingBitmap);
        canvas.drawBitmap(fondo, new Matrix(), null);

        int x = 0;
        int y = 0;
        for(int i=0; i<room; i++){
            if(i % 12 == 0){
                x = 0;
                y += 55;
            }
            color.setStyle(Paint.Style.STROKE);
            color.setColor(Color.GREEN);
            color.setStrokeWidth(4);
            canvas.drawCircle(image.getWidth() + x*40, y, 15, color);
            x += 1;
        }

        // Draw población
        for (int i = 0; i < numPopulation; i++) {
            canvas.drawBitmap(image,
                    (image.getWidth()) + 80*i >> 1,
                    (image.getHeight()) >> 1, new Paint());
        }

        int numWorkers = surface.countBuildings(this);
        int resWorker = getResources().getIdentifier("worker", "drawable", getPackageName());
        Bitmap imageWorker = BitmapFactory.decodeResource(getResources(), resWorker);
        for (int i = 0; i < numWorkers; i++) {
            canvas.drawBitmap(imageWorker,
                    (image.getWidth()) + 80*i >> 1,
                    (image.getHeight()) >> 1, new Paint());
        }
        pop.setImageBitmap(resultingBitmap);

        // Draw Button Proyecto
        List<Surfaces> surfaceList = surface.getTurns(this);
        for(Surfaces val : surfaceList){
            textProyecto.setText(val.getBuild());
            textProyectoTurnos.setText(String.format("%s turnos", val.getTurns()));
            message.setText(String.format("Construyendo %s", val.getBuild()));
            message.setTextColor(Color.GREEN);
            Builds build = new Builds();
            String buildImage = build.getImageBuild(this, val.getBuild());
            resImage = getResources().getIdentifier(buildImage, "drawable", getPackageName());
            proyecto.setCompoundDrawablesWithIntrinsicBounds(0, resImage, 0, 0);
        }
    }

    private List<Point> setAvailables() {
        List<Surfaces> builds = surface.getBuildings(this, planet.getName());
        List<Point> availables = new ArrayList<>();
        for(Surfaces val : builds) {
            Point point = new Point(val.getX(), val.getY());
            Point c1 = new Point(point.x - 100, point.y - 50);
            Point c2 = new Point(point.x + 100, point.y - 50);
            Point c3 = new Point(point.x - 100, point.y + 50);
            Point c4 = new Point(point.x + 100, point.y + 50);
            availables.add(c1);
            availables.add(c2);
            availables.add(c3);
            availables.add(c4);
        }
        return availables;
    }

    public void onResume(){
        super.onResume();
        Species specie = new Species();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        int specieId = data.getInt("specieId", 0);
        specie = specie.getSpecieById(this, specieId);
        Log.i("specieId", specie.getId()  + "");
    }

    public void runSistem(View view) {
        Intent i =  new Intent(this, SistemActivity.class);
        i.putExtra("starId", star.getId());
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
