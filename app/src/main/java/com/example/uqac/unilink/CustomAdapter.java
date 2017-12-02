package com.example.uqac.unilink;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lorane on 02/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] mDataSet;
    private int[] mDataSetTypes;

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
        TextView textViewSortie;

        public SortieViewHolder(View v){
            super(v);
            this.textViewSortie = (TextView) v.findViewById(R.id.sortie);
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

    public CustomAdapter(String[] dataSet, int[] dataSetTypes){
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
            TableViewHolder holder = (TableViewHolder) viewHolder;
            holder.textViewTable.setText(mDataSet[position]);
        }
        else if (viewHolder.getItemViewType() == SORTIE){
            SortieViewHolder holder = (SortieViewHolder) viewHolder;
            holder.textViewSortie.setText(mDataSet[position]);
        }
        else if (viewHolder.getItemViewType() == TRAJET){
            TrajetViewHolder holder = (TrajetViewHolder) viewHolder;
            holder.textViewTrajet.setText(mDataSet[position]);
        }
        else {
            CovoiturageViewHolder holder = (CovoiturageViewHolder) viewHolder;
            holder.textViewCovoiturage.setText(mDataSet[position]);
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
