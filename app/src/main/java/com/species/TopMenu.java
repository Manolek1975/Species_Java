package com.species;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TopMenu extends AppCompatActivity {

    private final Context context;

    public TopMenu(Context context){
        this.context = context;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option) {
        int id = option.getItemId();
        if (id == R.id.menu_stars || id == R.id.icon_menu_stars){
            runGalaxyMenu();
            return true;
        }
        if (id == R.id.menu_planets || id == R.id.icon_menu_planets){
            runPlanetasMenu();
            return true;
        }
        if(id == R.id.menu_ships || id == R.id.icon_menu_ships){
            runShipsMenu();
            return true;
        }
        if(id == R.id.menu_science || id == R.id.icon_menu_science){
            runSciencieMenu();
            return true;
        }
        if(id == R.id.menu_diplomacy || id == R.id.icon_menu_diplomacy){
            runDiplomacyMenu();
            return true;
        }

        if(id == R.id.menu_exit){
            runExitMenu();
            return true;
        }

        return super.onOptionsItemSelected(option);
    }

    private void runExitMenu() {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.menu_exit)
                .setMessage("¿Desea salir de la aplicación?")
                //set positive button
                .setPositiveButton("SI", (dialogInterface, i) -> {
                    //set what would happen when positive button is clicked
                    finishAndRemoveTask();
                    System.exit(0);
                })
                //set negative button
                .setNegativeButton("No", (dialogInterface, i) -> {
                    //set what should happen when negative button is clicked
                    //Toast.makeText(getApplicationContext(),"Acción cancelada",Toast.LENGTH_LONG).show();
                })
                .show();
    }

    private void runGalaxyMenu() {
        Intent i =  new Intent(context, SectorActivity.class);
        //i.putExtra("specie", specie);
        //i.putExtra("recursos", res);
        context.startActivity(i);
    }

    private void runPlanetasMenu() {
        Intent i =  new Intent(context, PlanetsActivity.class);
        context.startActivity(i);
    }

    private void runShipsMenu() {
        Intent i =  new Intent(context, ShipsActivity.class);
        context.startActivity(i);
    }

    private void runSciencieMenu() {
        Intent i =  new Intent(context, ScienceActivity.class);
        context.startActivity(i);
    }

    private void runDiplomacyMenu() {
        Intent i =  new Intent(context, DiplomacyActivity.class);
        context.startActivity(i);
    }

}
