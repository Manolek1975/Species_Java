package com.species.ui.sistem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.species.Game;
import com.species.R;
import com.species.Species;
import com.species.ui.stars.Stars;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SistemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SistemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SistemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SistemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SistemFragment newInstance(String param1, String param2) {
        SistemFragment fragment = new SistemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private View rootView;
    private Stars star = new Stars();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sistem, container, false);

        Species specie = new Species();
        star = new Stars();
        int starId = 0;
        Intent i= getActivity().getIntent();
        if (i.getExtras() != null) {
            starId = (int)i.getSerializableExtra("starId");
        } else {
            SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(requireActivity());
            int specieId = data.getInt("specieId", 0);
            specie = specie.getSpecieById(requireActivity(), specieId);
            starId = specie.getStar();

        }
        star = star.getStarById(container.getContext(), starId);
        //TextView sistemName = rootView.findViewById(R.id.sistemName);
        //sistemName.setText(star.getName());

        drawSistem();

        return rootView;
    }

    private void drawSistem() {
        LinearLayout lin = rootView.findViewById(R.id.sistemLayout);
        //ImageView sistemView = rootView.findViewById(R.id.sistemView);
        ImageView sistemView = new ImageView(getActivity());
        WindowManager window = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        Point position = new Point();
        display.getSize(position);
        int ancho = position.x;
        int alto = position.y;
        // Crear fondo con medidas
        Bitmap fondo = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = Game.getResId("fondo_sistema", R.drawable.class);
        //Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        //canvas.drawBitmap(fondoView, new Matrix(), null);
        lin.setBackgroundResource(resImageFondo);
        drawStar(canvas, ancho, alto);
        sistemView.setImageBitmap(bitmap);
        lin.addView(sistemView);


    }

    public void drawStar(Canvas canvas, int ancho, int alto){
        TextView sistemName = rootView.findViewById(R.id.sistemName);
        sistemName.setText(star.getName());
        String starImage = star.getImage();
        int resImage = Game.getResId(starImage, R.drawable.class);
        Bitmap starCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeStar = Bitmap.createScaledBitmap(starCenter, 200, 200, true);
        int centerX = ancho - 200 >> 1;
        int centerY = alto - 200 >> 1;
        canvas.drawBitmap(resizeStar, centerX, centerY, new Paint());
    }
}