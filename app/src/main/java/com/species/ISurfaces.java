package com.species;

import android.content.Context;
import android.graphics.Color;

import java.util.List;

public interface ISurfaces {

    void insertSurface(Context context, int planet, int build, int cost, int resource);
    List<Surfaces> getTurns(Context context);
    Surfaces getProyecto(Context context, int id);

}
