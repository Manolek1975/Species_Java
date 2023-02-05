package com.species;

import android.content.Context;

import java.util.List;

public interface IBuilds {

    List<Builds> getBuilds(Context context);
    void buildClear(Context context);
    Builds getBuildById(Context context, int id);
    String getImageBuild(Context context, int id);
}
