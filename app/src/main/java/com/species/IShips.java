package com.species;

import android.content.Context;

import java.util.List;

public interface IShips {

    List<Ships> getOwnShips(Context context, int id);
    List<Ships> getPlanetShips(Context context, int id);
    List<Ships> getStarShips(Context context, int id);
    void updateShipXY(Context context, int x, int y, int id);
    void updateShipLocation(Context context, int id, int location);

}
