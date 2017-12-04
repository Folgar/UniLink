package com.example.uqac.unilink;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lorane on 02/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private GeneralStructure[] mDataSet;
    private int[] mDataSetTypes;
    private Fragment mParentFragment;

    public static final int TABLE = 0;
    public static final int SORTIE = 1;
    public static final int TRAJET = 2;
    public static final int COVOITURAGE = 3;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class TableViewHolder extends ViewHolder{
        TextView textViewTable;

        public TableViewHolder(View v){
            super(v);
            this.textViewTable = (TextView) v.findViewById(R.id.table);
        }
    }

    public class SortieViewHolder extends ViewHolder{

        View view;
        SortieStructure currentItem;
        TextView heure;
        TextView date;
        TextView nbParticipants;
        TextView nbMax;

        public SortieViewHolder(View v){
            super(v);
            view = v;
            view.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    ((MainActivity)mParentFragment.getActivity()).onDetailsSortie(currentItem);
                }
            });
            this.heure = (TextView) v.findViewById(R.id.heure);
            this.date = (TextView) v.findViewById(R.id.date);
            this.nbParticipants = (TextView) v.findViewById(R.id.nbParticipants);
            this.nbMax = (TextView) v.findViewById(R.id.nbMax);
        }
    }

    public class TrajetViewHolder extends ViewHolder{
        TextView textViewTrajet;

        public TrajetViewHolder(View v){
            super(v);
            this.textViewTrajet = (TextView) v.findViewById(R.id.trajet);
        }
    }

    public class CovoiturageViewHolder extends ViewHolder{
        TextView textViewCovoiturage;

        public CovoiturageViewHolder(View v){
            super(v);
            this.textViewCovoiturage = (TextView) v.findViewById(R.id.covoiturage);
        }
    }

    public CustomAdapter(Fragment parentFragment, GeneralStructure[] dataSet, int[] dataSetTypes){
        mParentFragment = parentFragment;
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v;
        if (viewType == TABLE){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_tables, viewGroup, false);
            return new TableViewHolder(v);
        } else if (viewType == SORTIE){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_sorties, viewGroup, false);
            return new SortieViewHolder(v);
        } else if (viewType == TRAJET){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_trajets, viewGroup, false);
            return new TrajetViewHolder(v);
        } else{
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_covoiturages, viewGroup, false);
            return new CovoiturageViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        if (viewHolder.getItemViewType() == TABLE) {
            TableViewHolder tableViewHolder = (TableViewHolder) viewHolder;
            //holder.textViewTable.setText(mDataSet[position].getClass().toString());
        }
        else if (viewHolder.getItemViewType() == SORTIE){
            SortieViewHolder sortieViewHolder = (SortieViewHolder) viewHolder;
            sortieViewHolder.currentItem = (SortieStructure) mDataSet[position];
            sortieViewHolder.heure.setText(mDataSet[position].heure);
            sortieViewHolder.date.setText(mDataSet[position].date);
            sortieViewHolder.nbParticipants.setText(mDataSet[position].nombreParticipants);
            sortieViewHolder.nbMax.setText(mDataSet[position].nombreMax);
        }
        else if (viewHolder.getItemViewType() == TRAJET){
            TrajetViewHolder trajetViewHolder = (TrajetViewHolder) viewHolder;
            //holder.textViewTrajet.setText(mDataSet[position].getClass().toString());
        }
        else {
            CovoiturageViewHolder covoiturageViewHolder = (CovoiturageViewHolder) viewHolder;
            //holder.textViewCovoiturage.setText(mDataSet[position].getClass().toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }
}
