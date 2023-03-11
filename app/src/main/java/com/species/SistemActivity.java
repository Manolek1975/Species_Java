package com.species;

import static android.graphics.Color.alpha;

import static com.species.Game.width;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SistemActivity extends AppCompatActivity {
    private Stars star = new Stars();
    private Planets planet = new Planets();
    private ImageButton activeShip;

    int ancho, alto;
    //Bitmap fondo;
    LinearLayout menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem);
        View view = getWindow().getDecorView();
        Game.hideview(view);

        //fondo = findViewById(R.id.sistemLayout);
        menu = findViewById(R.id.menu_ships_layout);
        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);
        //int planetId = (int)i.getSerializableExtra("planet");
        //planet = planet.getPlanetById(this, planetId);

        drawSistem();
        //drawStar();
        //drawPlanets();
        //drawShips();

    }

    private void drawSistem() {
        LinearLayout lin = findViewById(R.id.sistemLayout);
        ImageView sistemView = findViewById(R.id.sistemView);
        WindowManager window = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        Point position = new Point();
        display.getSize(position);
        ancho = position.x;
        alto = position.y;
        // Crear fondo con medidas
        Bitmap fondo = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = Game.getResId("fondo_sistema", R.drawable.class);
        //Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        //canvas.drawBitmap(fondoView, new Matrix(), null);
        lin.setBackgroundResource(resImageFondo);

        drawStar(canvas);
        sistemView.setImageBitmap(bitmap);
    }

    public void drawStar(Canvas canvas){
        TextView sistemName = findViewById(R.id.sistemName);
        sistemName.setText(star.getName());
        String starImage = star.getImage();
        int resImage = Game.getResId(starImage, R.drawable.class);
        Bitmap starCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeStar = Bitmap.createScaledBitmap(starCenter, 200, 200, true);
        int centerX = ancho - 200 >> 1;
        int centerY = alto - 200 >> 1;
        canvas.drawBitmap(resizeStar, centerX, centerY, new Paint());
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
            //fondo.addView(img);

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
        Ships ships = new Ships();
        List<Ships> shipList = ships.getSistemShips(this, star.getId());
        for (Ships ship : shipList) {
            ImageButton img = new ImageButton(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int resImage = Game.getResId("ship0", R.drawable.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeShip = Bitmap.createScaledBitmap(bitmap, 160, 80, true);
            img.setImageBitmap(resizeShip);
            img.setBackgroundColor(Color.TRANSPARENT);
            img.setAdjustViewBounds(false);
            //img.setX(ship.getX());
            //img.setY(ship.getY());
            img.setX(0);
            img.setY(0);
            //fondo.addView(img);

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


}