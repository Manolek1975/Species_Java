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

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class BuildsActivity extends AppCompatActivity implements Serializable {

    private Species specie;
    private Stars star;
    private Planets planet;
    private Recursos res;
    private final Boolean canBuild = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.builds_activity);
        hideView();

        Intent i = getIntent();
        specie = (Species)i.getSerializableExtra("specie");
        star = (Stars)i.getSerializableExtra("star");
        planet = (Planets)i.getSerializableExtra("planet");
        res = (Recursos)i.getSerializableExtra("recursos");

        drawBuilds();

    }

    private void drawBuilds() {
        // Add Buttons to scroll
        LinearLayout buttonsLayout = findViewById(R.id.buildButtons);
        IBuilds builds = new Builds();
        List<Builds> buildList = builds.getBuilds(this);

        for(Builds build: buildList){
            Button btn = new Button(this);
            int resImage = this.getResources().getIdentifier(build.getImage(), "drawable", this.getPackageName());
            btn.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            btn.setCompoundDrawablesWithIntrinsicBounds(resImage, 0, 0, 0);
            btn.setPadding(50,20,0,20);
            btn.setCompoundDrawablePadding(50);
            btn.setAllCaps(false);
            btn.setText(build.getName() + " (30 turnos)" + "\n" + build.getDescription());
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTextColor(Color.WHITE);
            btn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

            btn.setOnClickListener(v -> {
                //Intent i = new Intent(this, PlanetManager.class);
                Intent i = new Intent(this, PlanetManager.class);
                //i.putExtra("specie", specie);
                //i.putExtra("star", star);
                i.putExtra("planet", planet);
                i.putExtra("build", build);
                //i.putExtra("recursos", res);
                i.putExtra("canBuild", true);
                startActivity(i);
            });
            //Log.i("BUILD", build.getName());
            buttonsLayout.addView(btn);
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

    private void hideView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }
}
