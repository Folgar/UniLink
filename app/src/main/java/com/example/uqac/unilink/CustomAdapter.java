package com.example.uqac.unilink;


import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lorane on 02/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] mDataSet;
    private int[] mDataSetTypes;
    private Fragment mParentFragment;
    private GoogleApiClient mGoogleApiClient;

    public static final int TABLE = 0;
    public static final int SORTIE = 1;
    public static final int TRAJET = 2;
    public static final int COVOITURAGE = 3;
    private MapsActivity dialogFragment;
//    private MapsActivity map;

    public void onFinishDialogMap(String address) {
        this.lieu.setText(address);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class TableViewHolder extends ViewHolder {
        TextView textViewTable;

        public TableViewHolder(View v) {
            super(v);
            this.textViewTable = (TextView) v.findViewById(R.id.table);
        }
    }

    public class SortieViewHolder extends ViewHolder {
        TextView textViewSortie;

        public SortieViewHolder(View v) {
            super(v);
            this.textViewSortie = (TextView) v.findViewById(R.id.sortie);
        }
    }

    public class TrajetViewHolder extends ViewHolder {
        TextView textViewTrajet;

        public TrajetViewHolder(View v) {
            super(v);
            this.textViewTrajet = (TextView) v.findViewById(R.id.trajet);
        }
    }

    public class CovoiturageViewHolder extends ViewHolder {
        TextView textViewCovoiturage;

        public CovoiturageViewHolder(View v) {
            super(v);
            this.textViewCovoiturage = (TextView) v.findViewById(R.id.covoiturage);
        }
    }

    public CustomAdapter(Fragment parentFragment, String[] dataSet, int[] dataSetTypes) {
        mParentFragment = parentFragment;
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
    }

    private static final String DIALOG_DATE = "sortie.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialog;
    private EditText nombre;
    private EditText lieu;
    private EditText description;
    private Button ok_button;
    private Button cancel_button;
    private DatabaseReference mRefTable;
    private DatabaseReference mRefLink;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == TABLE) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_tables, viewGroup, false);
            return new TableViewHolder(v);
        } else if (viewType == SORTIE) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_sorties, viewGroup, false);
            /////////////////////////////


            datePickerAlertDialog = (EditText) v.findViewById(R.id.alert_dialog_date_picker);
            timePickerAlertDialog = (EditText) v.findViewById(R.id.alert_dialog_time_picker);
            nombre = (EditText) v.findViewById(R.id.nombre_participants);
            lieu = (EditText) v.findViewById(R.id.lieu);
            description = (EditText) v.findViewById(R.id.description);
            ok_button = (Button) v.findViewById(R.id.ok_button);
            cancel_button = (Button) v.findViewById(R.id.cancel_button);
            mRefTable = FirebaseDatabase.getInstance().getReference("sortie");
            mRefLink = FirebaseDatabase.getInstance().getReference("link");


            datePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment dialog = new DatePickerFragment();
                    dialog.customAdapter = CustomAdapter.this;
                    dialog.show(mParentFragment.getFragmentManager(), DIALOG_DATE);
                }
            });


            timePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TimePickerFragment dialog = new TimePickerFragment();
                    dialog.customAdapter = CustomAdapter.this;
                    dialog.show(mParentFragment.getFragmentManager(), "TimePickerFragment");
                }
            });

            lieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
//                    map = new MapsActivity();
                    final FragmentManager fm = mParentFragment.getFragmentManager();
                    dialogFragment = new MapsActivity();
//                    dialogFragment.show(fm, "Sample Fragment");
                    dialogFragment.context = mParentFragment.getContext();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mParentFragment.getContext());
// Get the layout inflater
                    final LayoutInflater inflater = (LayoutInflater) mParentFragment.getContext().getSystemService(mParentFragment.getContext().LAYOUT_INFLATER_SERVICE);
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
//                    dialogFragment.setContentView(R.layout.activity_maps);
                    dialogFragment.customAdapter=CustomAdapter.this;
                    builder.setView(inflater.inflate(R.layout.activity_maps, viewGroup,false));
                    final AlertDialog ad = builder.create();
                    ad.setTitle("Select starting point");
                    ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                public void onClick(DialogInterface dialog, int which) {
                                    CustomAdapter.this.dialogFragment.finishAndRemoveTask();
                                    dialogFragment.finish();
                                }
                            });
                    ad.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lieu.setText(CustomAdapter.this.dialogFragment.address);
                            CustomAdapter.this.dialogFragment.finishAndRemoveTask();
                            dialogFragment.finish();
                            ad.dismiss();

                        }
                    });

                    ad.show();

                    SupportMapFragment mapFragment = (SupportMapFragment) fm
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(dialogFragment);




                    dialogFragment.finish();


//                    if (ActivityCompat.checkSelfPermission(mParentFragment.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mParentFragment.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        map.mMap.setMyLocationEnabled(true);
//                        return;
//                    }

//                    map.buildGoogleAPIClient();

//                    if (mGoogleApiClient == null) {
//                        mGoogleApiClient = new GoogleApiClient.Builder(map)
//                                .addConnectionCallbacks(map)
//                                .addOnConnectionFailedListener(map)
//                                .addApi(LocationServices.API)
//                                .build();
//                    }

                    // Do other setup activities here too, as described elsewhere in this tutorial.

                    // Build the Play services client for use by the Fused Location Provider and the Places API.
                    // Use the addApi() method to request the Google Places API and the Fused Location Provider.



//                    Intent intent = new Intent(mParentFragment.getContext(),MapsActivity.class);


                }
            });
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datePickerAlertDialog.getText().toString().matches(""))
                        Toast.makeText(mParentFragment.getContext(), "Date manquante", Toast.LENGTH_SHORT).show();
                    else {
                        if (timePickerAlertDialog.getText().toString().matches(""))
                            Toast.makeText(mParentFragment.getContext(), "Heure manquante", Toast.LENGTH_SHORT).show();
                        else {
                            if (lieu.getText().toString().matches(""))
                                Toast.makeText(mParentFragment.getContext(), "Lieu manquant", Toast.LENGTH_SHORT).show();
                            else if (nombre.getText().toString().matches(""))
                                Toast.makeText(mParentFragment.getContext(), "Nombre de participants maximum manquant", Toast.LENGTH_SHORT).show();
                            else {
                                SortieStructure sortie = new SortieStructure(datePickerAlertDialog.getText().toString(), timePickerAlertDialog.getText().toString(), lieu.getText().toString(), description.getText().toString(), nombre.getText().toString());
                                String linkId = mRefLink.push().getKey();
                                mRefLink.child(linkId).setValue("Sortie");
                                mRefTable.child(linkId).setValue(sortie);
                                Toast.makeText(mParentFragment.getContext(), "Sortie enregistrée", Toast.LENGTH_SHORT).show();

//                                finish();
                            }
                        }
                    }
                }

            });

            cancel_button.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {
                    //finish();
                }
            });

            //////////
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

    public void onFinishDialog(Date date) {
        datePickerAlertDialog.setText(formatDate(date));
    }
    public void onFinishDialog(String time) {
        timePickerAlertDialog.setText(time);
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }
}
