package com.teamvinay.sketch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import helper.Resolutions;
import java.util.ArrayList;

public class ResolutionsAdapter extends ArrayAdapter<Resolutions> {
    public ResolutionsAdapter(Context context, ArrayList<Resolutions> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        return initView(i, view, viewGroup);
    }

    public View getDropDownView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        return initView(i, view, viewGroup);
    }

    private View initView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_row, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.title);
        Resolutions resolutions = (Resolutions) getItem(i);
        if (resolutions != null) {
            textView.setText(resolutions.getResolution());
        }
        return view;
    }
}
