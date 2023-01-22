package com.species;

import android.content.Context;

import java.util.List;

public interface ISpecies {

    List<Species> getSpecies(Context context);

    void setMainSpecie(Context context, int id);

    Species getMainSpecie(Context context);

}
