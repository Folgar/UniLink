package com.example.uqac.unilink;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lorane on 02/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<GeneralStructure> mDataSet;
    private List<Integer> mDataSetTypes;

    private Fragment mParentFragment;

    public static final int TABLE = 1;
    public static final int SORTIE = 2;
    public static final int TRAJET = 3;
    public static final int COVOITURAGE = 4;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class TableViewHolder extends ViewHolder{

        View view;
        TableStructure currentItem;
        TextView heure;
        TextView date;
        TextView nbParticipants;
        TextView nbMax;

        public TableViewHolder(View v){
            super(v);view = v;
            view.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    ((MainActivity)mParentFragment.getActivity()).onDetailsTable(currentItem);
                }
            });
            this.heure = (TextView) v.findViewById(R.id.heure);
            this.date = (TextView) v.findViewById(R.id.date);
            this.nbParticipants = (TextView) v.findViewById(R.id.nbParticipants);
            this.nbMax = (TextView) v.findViewById(R.id.nbMax);
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

    public CustomAdapter(Fragment parentFragment, List<GeneralStructure> dataSet, List<Integer> dataSetTypes){
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
            tableViewHolder.currentItem = (TableStructure) mDataSet.get(position);

            tableViewHolder.heure.setText(mDataSet.get(position).heure);
            tableViewHolder.date.setText(mDataSet.get(position).date);
            tableViewHolder.nbParticipants.setText(mDataSet.get(position).nombreParticipants);
            tableViewHolder.nbMax.setText(mDataSet.get(position).nombreMax);

        }
        else
        if (viewHolder.getItemViewType() == SORTIE){
            SortieViewHolder sortieViewHolder = (SortieViewHolder) viewHolder;
            sortieViewHolder.currentItem = (SortieStructure) mDataSet.get(position);
            sortieViewHolder.heure.setText(mDataSet.get(position).heure);
            sortieViewHolder.date.setText(mDataSet.get(position).date);
            sortieViewHolder.nbParticipants.setText(mDataSet.get(position).nombreParticipants);
            sortieViewHolder.nbMax.setText(mDataSet.get(position).nombreMax);
        }
        else if (viewHolder.getItemViewType() == TRAJET){
            TrajetViewHolder trajetViewHolder = (TrajetViewHolder) viewHolder;
            //holder.textViewTrajet.setText(mDataSet.get(position).getClass().toString());
        }
        else {
            CovoiturageViewHolder covoiturageViewHolder = (CovoiturageViewHolder) viewHolder;
            //holder.textViewCovoiturage.setText(mDataSet.get(position).getClass().toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes.get(position);
    }
}
