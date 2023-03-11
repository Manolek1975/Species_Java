package com.species.ui.ships;

import android.content.Context;

import java.util.List;

public interface IShips {

    List<Ships> getOwnShips(Context context, int id);
    List<Ships> getPlanetShips(Context context, int id);
    List<Ships> getSistemShips(Context context, int id);
    void updateShipXY(Context context, float x, float y, int id);
    void updateShipLocation(Context context, int id, int location);

}
