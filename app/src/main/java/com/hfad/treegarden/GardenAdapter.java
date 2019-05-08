package com.hfad.treegarden;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GardenAdapter extends ArrayAdapter{
    private Context context;
    private int resource;
    private List<Tree> gardenList;

    public GardenAdapter(@NonNull Context context, int resource, @NonNull List<Tree> gardenList) {
        super(context, resource, gardenList);
        this.context = context;
        this.resource = resource;
        this.gardenList = gardenList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //just like we did with fragments
        //inflate a layout, wire widgets, insert data, return the layout
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gardenlist, parent, false);

        }

        //wiring widgets
        TextView textViewName =
                convertView.findViewById(R.id.textView_garden_name);
        TextView textViewCauseOfDeath=
                convertView.findViewById(R.id.textView_causeOfDeath);

        Tree currentTree = gardenList.get(position);
        textViewName.setText(currentTree.getTree());

        textViewCauseOfDeath.setText(currentTree.getCauseOfDeath());


        return convertView;
    }
}
