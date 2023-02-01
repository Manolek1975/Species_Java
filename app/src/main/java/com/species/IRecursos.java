package com.species;

import android.content.Context;

import java.util.List;

public interface IRecursos {

    void insertRecursos(Context context, Planets planet);
    void increaseRecursos(Context context, Planets planet, Builds build, int color);

    List<Recursos> getRecursos(Context context, Planets planet);
    Recursos getRecursosByPlanet(Context context, Planets planet);

}
