package com.species;

import android.content.Context;
import android.graphics.Point;

import java.util.List;

public interface IPlanets {

    List<Planets> getStarPlanets(Context context, int star);

    Planets getPlanetById(Context context, Integer id);
    List<Planets> getOwnPlanets(Context context, int specieId);

    String getImagePlanet(Context context, Integer type);

    Planets getPlanetTarget(Context context, String planetName);

    void setPlanetXY(int id, float x, float y, Context context);


}
