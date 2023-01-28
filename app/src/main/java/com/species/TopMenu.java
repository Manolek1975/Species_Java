package com.species;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TopMenu extends AppCompatActivity {

    private final Context context;
    private final Species specie;
    private final Stars star;
    private final Planets planet;
    private final Recursos res;

    public TopMenu(Context context, Species specie, Stars star, Planets planet, Recursos res){
        this.context = context;
        this.specie = specie;
        this.star = star;
        this.planet = planet;
        this.res = res;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option) {
        int id = option.getItemId();
        if (id == R.id.menu_stars || id == R.id.icon_menu_stars){
            runGalaxyMenu(null);
            return true;
        }
        if (id == R.id.menu_planets || id == R.id.icon_menu_planets){
            runPlanetasMenu(null);
            return true;
        }
        if(id == R.id.menu_ships || id == R.id.icon_menu_ships){
            runShipsMenu(null);
            return true;
        }
        if(id == R.id.menu_science || id == R.id.icon_menu_science){
            runSciencieMenu(null);
            return true;
        }
        if(id == R.id.menu_diplomacy || id == R.id.icon_menu_diplomacy){
            runDiplomacyMenu(null);
            return true;
        }

        if(id == R.id.menu_exit){
            runExitMenu(null);
            return true;
        }

        return super.onOptionsItemSelected(option);
    }

    private void runExitMenu(Object o) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.menu_exit)
                .setMessage("¿Desea salir de la aplicación?")
                //set positive button
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        finishAndRemoveTask();
                        System.exit(0);
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        //Toast.makeText(getApplicationContext(),"Acción cancelada",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void runGalaxyMenu(Object o) {
        Intent i =  new Intent(context, StarsActivity.class);
        //i.putExtra("specie", specie);
        //i.putExtra("recursos", res);
        context.startActivity(i);
    }

    private void runPlanetasMenu(Object o) {
        Intent i =  new Intent(context, PlanetsActivity.class);
        i.putExtra("specie", specie);
        //i.putExtra("star", star);
        //i.putExtra("recursos", res);
        context.startActivity(i);
    }

    private void runShipsMenu(Object o) {
        Intent i =  new Intent(context, ShipsActivity.class);
        i.putExtra("specie", specie);
        context.startActivity(i);
    }

    private void runSciencieMenu(Object o) {
        Intent i =  new Intent(context, ScienceActivity.class);
        i.putExtra("specie", specie);
        context.startActivity(i);
    }

    private void runDiplomacyMenu(Object o) {
        Intent i =  new Intent(context, DiplomacyActivity.class);
        i.putExtra("specie", specie);
        context.startActivity(i);
    }

}
