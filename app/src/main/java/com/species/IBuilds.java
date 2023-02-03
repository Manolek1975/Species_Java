package com.species;

import android.content.Context;

import java.util.List;

public interface IBuilds {
    String getImageBuild(Context context, String name);
    List<Builds> getBuilds(Context context);

    void buildClear(Context context);
    Builds getBuildByName(Context context, String name);
}
