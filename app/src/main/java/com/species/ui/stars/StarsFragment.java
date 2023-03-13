package com.species.ui.stars;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.species.Game;
import com.species.R;
import com.species.SistemFragment;
import com.species.Species;
import com.species.ui.planets.PlanetsFragment;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StarsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private android.view.MenuItem MenuItem;

    public StarsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StarsFragment newInstance(String param1, String param2) {
        StarsFragment fragment = new StarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Context context;
    private Species specie = new Species();
    private Stars star = new Stars();
    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getContext();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        int specieId = data.getInt("specieId", 0);
        specie = specie.getSpecieById(context, specieId);
        star = star.getMainStar(context);
        Log.i("StarActivity", specie.getName() + ", " +star.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View decorView = requireActivity().getWindow().getDecorView();
        //Game.hideview(decorView);
        //requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
/*        View decorView = requireActivity().getWindow().getDecorView();
        Game.hideview(decorView);
        requireActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_stars, container, false);

        drawSector();

        return rootView;
    }

    private void drawSector() {
        //TODO Draw solamente Estrellas exploradas
        ImageView image = (ImageView) rootView.findViewById(R.id.fondoView);
        // Crear fondo con medidas
        Point dimen = Game.getMetrics(context);
        Bitmap fondo = Bitmap.createBitmap(dimen.x, dimen.y, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = Game.getResId("fondo_sector", R.drawable.class);
        Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        canvas.drawBitmap(fondoView, new Matrix(), null);
        image.setImageBitmap(bitmap);
        // Draw Stars
        List<Stars> starList = star.getStars(context);
        //TODO Draw saltos
        //drawJumps(canvas, starList);
        for(Stars star : starList){
            Paint paint = new Paint();
            if (star.getId() == specie.getStar()){
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.WHITE);
            }
            paint.setTextSize(24);
            int resImage = Game.getResId(star.getImage(), R.drawable.class);
            Bitmap drawStar = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeStar = Bitmap.createScaledBitmap(drawStar, 100 , 100, true);
            canvas.drawBitmap(resizeStar, (star.getX()), (star.getY()), new Paint());
            canvas.drawText(star.getName(), star.getX() - star.getName().length(), star.getY(), paint);
        }
        image.setImageBitmap(bitmap);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getRawX();
                int y = (int)event.getRawY();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Stars star = new Stars();
                    List<Stars> starList = star.getStars(context);
                    for (Stars val : starList) {
                        Range<Integer> rangoX = Range.create(x-50, x + 50);
                        Range<Integer> rangoY = Range.create(y-50, y + 50);
                        if (rangoX.contains(val.getX()) && rangoY.contains(val.getY())) {
                            final NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                                    .getSupportFragmentManager()
                                    .findFragmentById(R.id.nav_host_fragment_content_sidebar);
                            final NavController navController = navHostFragment.getNavController();
                            NavigationView nv = requireActivity().findViewById(R.id.nav_view);
                            MenuItem item = nv.getMenu().getItem(0);
                            NavigationUI.onNavDestinationSelected(item, navController);
                            Log.i("CHANGE", "OK");
                        }
                        Paint myPaint = new Paint();
                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setColor(Color.YELLOW);
                        myPaint.setStrokeWidth(4);
                        canvas.drawRect(rangoX.getLower(), rangoY.getLower(), rangoX.getUpper(), rangoY.getUpper(), myPaint);
                        image.setImageBitmap(bitmap);
                        Log.i("XY", x + "," + y);
                    }
                }
                return false;
            }
        });
    }

}