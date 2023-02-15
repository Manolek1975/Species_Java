package com.species;

import android.content.Context;

import java.util.List;

public interface IShips {

    List<Ships> getPlanetShips(Context context, int id);
}
