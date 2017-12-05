package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;
import static com.example.uqac.unilink.CustomAdapter.TABLE;

/**
 * Created by Lorane on 01/12/2017.
 */

public class LinksFragment extends GeneralFragment {

    //TODO MultiChild
    //TODO Filtrer
    public LinksFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private GeneralStructure[] mDatasetT;
    private int[] mDatasetTypeT;
    private CustomAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final List<GeneralStructure> mDataset = new ArrayList<>();
        final List<Integer> mDatasetTypes = new ArrayList<>(); //view types
        final View view =  inflater.inflate(R.layout.fragment_links, container, false);
        final Context context = view.getContext();


        mDatasetT = new GeneralStructure[mDataset.size()];
        mDatasetTypeT = new int[mDatasetTypes.size()];

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(this, mDatasetT, mDatasetTypeT);
        mRecyclerView.setAdapter(mAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("table");
        DatabaseReference refS = database.getReference().child("sortie");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tester: ", "passe dedans");

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    TableStructure table = eventSnapshot.getValue(TableStructure.class);
                    SortieStructure sortie = eventSnapshot.getValue(SortieStructure.class);

                    boolean tableOk = false;
                    for(int i = 0; i < table.Participants.size(); i++) {
                        if(table.Participants.get(i).equals(User.getInstance().getUser().getDisplayName()))
                        {
                            tableOk = true;
                        }
                    }
                    if(tableOk) {
                        mDataset.add(table);
                        mDatasetTypes.add(TABLE);
                    }


                    mDataset.add(sortie);
                    mDatasetTypes.add(SORTIE);
                }
                mDatasetT = new GeneralStructure[mDataset.size()];
                mDatasetTypeT = new int[mDatasetTypes.size()];

                for(int i=0; i<mDataset.size();i++) {
                    mDatasetT[i] = mDataset.get(i);
                    mDatasetTypeT[i] = mDatasetTypes.get(i);
                }

                mAdapter = new CustomAdapter(getParentFragment(), mDatasetT, mDatasetTypeT);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
        refS.addListenerForSingleValueEvent(listener);



        ref.removeEventListener(listener);
        refS.removeEventListener(listener);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
