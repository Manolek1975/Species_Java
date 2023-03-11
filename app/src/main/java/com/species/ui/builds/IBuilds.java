package com.species.ui.builds;

import android.content.Context;

import com.species.ui.builds.Builds;

import java.util.List;

public interface IBuilds {

    List<Builds> getBuilds(Context context);
    Builds getBuildById(Context context, int id);
    String getImageBuild(Context context, int id);
}
