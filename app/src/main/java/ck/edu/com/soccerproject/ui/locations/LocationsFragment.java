package ck.edu.com.soccerproject.ui.locations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ck.edu.com.soccerproject.DatabaseHelper;
import ck.edu.com.soccerproject.Game;
import ck.edu.com.soccerproject.ListGames;
import ck.edu.com.soccerproject.R;

public class LocationsFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private TextView info_match;

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper myDatabase;
    private Cursor cursor;

    private ArrayList<Game> gamesTable;
    private ArrayList<LatLng> latLngArrayList;

    private Geocoder geocoder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        gamesTable = new ArrayList<Game>();
        latLngArrayList = new ArrayList<LatLng>();
        geocoder = new Geocoder(getActivity());
        ListGames listGames = new ListGames (getContext(), gamesTable);
        info_match = root.findViewById(R.id.info_match);

        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        //instantiate the database
        myDatabase = new DatabaseHelper(getActivity());
        sqLiteDatabase = myDatabase.getReadableDatabase();
        //retrieve all data from cursor
        cursor = myDatabase.getData();
        if(cursor.moveToFirst()){
            do{
                String first_team, second_team, score, date, location;
                LatLng address;
                byte[] image;
                first_team = cursor.getString(1);
                second_team = cursor.getString(2);
                score = cursor.getString(3);
                date = cursor.getString(4);
                location = cursor.getString(5);
                image = cursor.getBlob(6);
                Game game = new Game(first_team, second_team, score, date, location, image);
                gamesTable.add(game);
                address = getLatLng(location);
                latLngArrayList.add(address);

            }while(cursor.moveToNext());
        }
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        List <Address> addresses;
        String address = null;
        for(int i = 0; i<latLngArrayList.size(); i++){
                try {
                     addresses = geocoder.getFromLocation(latLngArrayList.get(i).latitude, latLngArrayList.get(i).longitude, 1);
                     address = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(address));
                map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(i)));
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                info_match.setText("");
                for(int i = 0; i<gamesTable.size(); i++){
                    LatLng location = getLatLng(gamesTable.get(i).getLocation());
                    if(location.latitude == marker.getPosition().latitude && location.longitude == marker.getPosition().longitude ){
                        info_match.setText(info_match.getText() + gamesTable.get(i).getFirst_team() + " VS " +gamesTable.get(i).getSecond_team() +" : "+gamesTable.get(i).getScore() +"\n");
                    }
                }
            return false;
            }
        });
    }

    private LatLng getLatLng (String address){

        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleAddress = addressList.get(0);
                LatLng latLng = new LatLng(singleAddress.getLatitude(), singleAddress.getLongitude());
                return latLng;
            }else{
                return null;
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
