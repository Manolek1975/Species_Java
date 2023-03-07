package com.species;

import static android.graphics.Color.alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SistemActivity extends AppCompatActivity {
    private Stars star = new Stars();
    private Planets planet = new Planets();
    private ImageButton activeShip;
    LinearLayout fondo, menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        fondo = findViewById(R.id.sistemLayout);
        menu = findViewById(R.id.menu_ships_layout);
        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);
        //int planetId = (int)i.getSerializableExtra("planet");
        //planet = planet.getPlanetById(this, planetId);

        drawStar();
        drawPlanets();
        drawShips();

    }

    private void drawStar() {
        TextView sistemName = findViewById(R.id.sistemName);
        sistemName.setText(star.getName());
        ImageButton img = new ImageButton(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int resImage = Game.getResId(star.getImage(), R.drawable.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeStar = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        img.setImageBitmap(resizeStar);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setX(0);
        img.setY(300);
        fondo.addView(img);

        Log.i("StarXY", img.getX() + "," + img.getY());

        img.setOnClickListener(v -> {
            View customToastroot = View.inflate(this, R.layout.custom_toast, null);
            TextView msg = customToastroot.findViewById(R.id.toastMsg);
            msg.setText("Estrella " + star.getName()+"\nTipo: " + star.getType() + ":" + img.getX() + ",:" + img.getY() );
            msg.setTextColor(Color.WHITE);
            Toast customtoast = new Toast(getApplicationContext());
            customtoast.setView(customToastroot);
            customtoast.setDuration(Toast.LENGTH_LONG);
            customtoast.show();
        });
    }

    private void drawPlanets() {
        Planets planets = new Planets();
        for(int i=0; i < star.getPlanets(); i++){
            List<Planets> planetList = planets.getStarPlanets(this, star.getId());
            ImageButton img = new ImageButton(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            planets = planetList.get(i);
            int type = planets.getType();
            String image = planets.getImagePlanet(this, type);
            int resImage = Game.getResId(image, R.drawable.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizePlanet = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            img.setImageBitmap(resizePlanet);
            img.setBackgroundColor(Color.TRANSPARENT);
            img.setAdjustViewBounds(false);
            setPlanetPosition(img, i, planets.getId());
            fondo.addView(img);

            Log.i("PlanetXY", img.getX() + "," + img.getY());

            Planets planet = planets;
            img.setOnClickListener(v -> {
                if (planet.getExplore() == 1) {
                    Intent intent = new Intent(this, PlanetManager.class);
                    intent.putExtra("planet", planet);
                    startActivity(intent);
                } else {
                    View customToastroot = View.inflate(this, R.layout.custom_toast, null);
                    TextView msg = customToastroot.findViewById(R.id.toastMsg);
                    msg.setText(R.string.explore);
                    Toast customtoast = new Toast(getApplicationContext());
                    customtoast.setView(customToastroot);
                    customtoast.setDuration(Toast.LENGTH_LONG);
                    customtoast.show();
                    Log.i("XY", planet.getName() + ":" + img.getX() + ",:" + img.getY());
                }
            });
        }
    }

    private void drawShips() {

        AtomicReference<Ships> ships = new AtomicReference<>(new Ships());
        List<Ships> shipList = ships.get().getSistemShips(this, star.getId());
        for (Ships ship : shipList) {
            ImageButton img = new ImageButton(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int resImage = Game.getResId("ship0", R.drawable.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeShip = Bitmap.createScaledBitmap(bitmap, 160, 80, true);
            img.setImageBitmap(resizeShip);
            img.setBackgroundColor(Color.TRANSPARENT);
            img.setAdjustViewBounds(false);
            img.setX(ship.getX());
            img.setY(ship.getY());
            fondo.addView(img);

            Log.i("ShipXY", img.getX() + "," + img.getY());

            img.setOnClickListener(v -> {
                menu.setVisibility(View.VISIBLE);
                activeShip = img;
                Log.i("XY", ship.getName() + ":" + img.getX() + ",:" + img.getY());
            });
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            moveShip(activeShip, x, y);
            Log.i("ShipXY", x + "," + y);
        }

        return false;
    }

    public void moveShip(ImageButton img, float x, float y) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(img, "translationX", x);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img, "translationY", y);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(3000);
        animSetXY.start();
        play();
        disableButtons();
        menu.setVisibility(View.INVISIBLE);


        //img.setX(planet.getX());
        //img.setY(planet.getY());
    }

    protected void play() {
        super.onStart();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.ship_move);
        mp.start();
    }

    private void setPlanetPosition(ImageButton img, int numPlanet, int id) {
        float x=0, y =0;
        if (numPlanet == 0) {
            x = -300;
            y = -100;
        }
        if (numPlanet == 1) {
            x = 300;
            y = 200;
        }
        if (numPlanet == 2) {
            x = 300;
            y = -400;
        }
        if (numPlanet == 3) {
            x = -300;
            y = -100;
        }
        img.setX(x);
        img.setY(y);
        planet.setPlanetXY(id, x, y, this);
    }


    public void runMove(View view){
        disableButtons();
        ImageButton move = findViewById(R.id.move);
        move.setBackgroundResource(R.drawable.border_yellow);
    }

    public void runFire(View view){
        disableButtons();
        ImageButton fire = findViewById(R.id.fire);
        fire.setBackgroundResource(R.drawable.border_yellow);

    }
    public void runShield(View view){
        disableButtons();
        ImageButton shield = findViewById(R.id.shield);
        shield.setBackgroundResource(R.drawable.border_yellow);

    }
    public void runTecno(View view){
        disableButtons();
        ImageButton tecno = findViewById(R.id.tecno);
        tecno.setBackgroundResource(R.drawable.border_yellow);

    }

    public void runExit(View view){
        LinearLayout menu = findViewById(R.id.menu_ships_layout);
        menu.setVisibility(View.INVISIBLE);
    }

    public void disableButtons(){
        ImageButton move = findViewById(R.id.move);
        ImageButton fire = findViewById(R.id.fire);
        ImageButton shield = findViewById(R.id.shield);
        ImageButton tecno = findViewById(R.id.tecno);
        move.setBackgroundResource(alpha(0));
        fire.setBackgroundResource(alpha(0));
        shield.setBackgroundResource(alpha(0));
        tecno.setBackgroundResource(alpha(0));
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