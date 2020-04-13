package ck.edu.com.soccerproject.ui.locations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ck.edu.com.soccerproject.model.DatabaseHelper;
import ck.edu.com.soccerproject.model.Game;
import ck.edu.com.soccerproject.model.ListGames;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        gamesTable = new ArrayList<Game>();
        latLngArrayList = new ArrayList<LatLng>();
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
        for(int i = 0; i<latLngArrayList.size(); i++){
            for(int j = 0; j < gamesTable.size(); j++){
                map.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(gamesTable.get(j).getLocation()));
                System.out.println(gamesTable.get(j).getLocation());
                map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(i)));
            }

        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String location = marker.getTitle();
                for(int i = 0; i<gamesTable.size() ; i++){
                    System.out.println("Location "+location);
                    System.out.println("Game "+gamesTable.get(i).getLocation());
                    if(gamesTable.get(i).getLocation() != location){
                        System.out.println("ptn de merde");
                        info_match.setText(info_match.getText()+location);
                    }
                }
                return false;
            }
        });
    }

    private LatLng getLatLng (String address){
        Geocoder geocoder = new Geocoder(getActivity());
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
