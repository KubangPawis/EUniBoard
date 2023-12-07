package com.example.euniboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class SubjectsFragment extends Fragment {
    public SubjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //BLUR BG METHOD
        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);

        // Get the main activity's content as a bitmap
        Bitmap backgroundBitmap = getScreenShot(getActivity().getWindow().getDecorView().getRootView());

        // Apply blur effect to the captured bitmap
        Bitmap blurredBitmap = blurBitmap(getContext(), backgroundBitmap, 20); // Adjust blur radius as needed

        // Create a layer drawable with the blurred bitmap and semi-transparent color
        Drawable[] layers = new Drawable[2];
        layers[0] = new BitmapDrawable(getResources(), blurredBitmap);
        layers[1] = new ColorDrawable(Color.parseColor("#713232"));
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setAlpha(230);
        rootView.setBackground(layerDrawable);

        //EVENTS
        ImageView btnClose = rootView.findViewById(R.id.btnClose);
        Button btnViewSubjects = rootView.findViewById(R.id.btnViewSubjects);
        btnClose.setOnClickListener(e -> exitFragment());
        btnViewSubjects.setOnClickListener(e -> goToViewSubjects(e));

        return rootView;
    }
    public static Bitmap getScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return screenshot;
    }

    public static Bitmap blurBitmap(Context context, Bitmap inputBitmap, float radius) {
        if (inputBitmap == null) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(
                inputBitmap.getWidth(), inputBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(tmpIn);
        scriptIntrinsicBlur.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        renderScript.destroy();

        return outputBitmap;
    }
    public void goToViewSubjects(View v) {
        Intent intent = new Intent(getActivity(), ViewSubjects.class);
        startActivity(intent);
    }

    public void exitFragment() {
        getActivity().onBackPressed();
    }
}