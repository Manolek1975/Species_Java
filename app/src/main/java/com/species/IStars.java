package com.species;

import android.content.Context;

import java.util.List;

public interface IStars {

    List<Stars> getStars (Context context);

    Stars getStarById(Context context, int id);

    Stars getMainStar(Context context);

    default void setMainStar(Context context) {

    }

}
