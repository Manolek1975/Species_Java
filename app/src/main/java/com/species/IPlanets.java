package com.species;

import android.content.Context;

import java.util.List;

public interface IPlanets {

    List<Planets> getPlanets(Context context, Stars star);

    List<Planets> getOwnPlanets(Context context, Species specie);

    Planets getPlanetTarget(Context context, String planetName);
}
