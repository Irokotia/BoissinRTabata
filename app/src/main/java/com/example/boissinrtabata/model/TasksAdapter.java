package com.example.boissinrtabata.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boissinrtabata.db.SeanceEntrainement;
import com.example.boissinrtabata.R;

import java.util.List;

public class TasksAdapter extends ArrayAdapter<SeanceEntrainement>{
    public TasksAdapter(Context mCtx, List<SeanceEntrainement> taskList) {
        super(mCtx, R.layout.template_task, taskList);
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final SeanceEntrainement seanceEntrainement = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_task, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textViewTask = (TextView) rowView.findViewById(R.id.textViewTask);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        //
        // imageView.setImageURI(Uri.parse(user.getImage()));
        textViewTask.setText(seanceEntrainement.getNomSéance());

        //
        return rowView;
    }
}
