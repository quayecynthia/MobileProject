package ck.edu.com.soccerproject.ui.previousgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ck.edu.com.soccerproject.R;
import ck.edu.com.soccerproject.ui.allgames.AllgamesFragment;
import ck.edu.com.soccerproject.ui.locations.LocationsFragment;
import ck.edu.com.soccerproject.ui.statistics.StatisticsFragment;

//Fragment that gives access to locations, all games and statistics
public class PreviousgameFragment extends Fragment {
    private Button locationsButton, allgamesButton, statisticsButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_previousgame, container, false);
        final TextView textView = v.findViewById(R.id.text_previousgame);

        locationsButton = v.findViewById(R.id.button_locations);
        allgamesButton = v.findViewById(R.id.button_allgames);
        statisticsButton = v.findViewById(R.id.button_statistics);

        locationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationsFragment locationsFragment = new LocationsFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment, locationsFragment, locationsFragment.getTag()).commit();
            }
        });

        allgamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllgamesFragment allgamesFragment = new AllgamesFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment, allgamesFragment, allgamesFragment.getTag()).commit();
            }
        });
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsFragment statiticsFragment = new StatisticsFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment, statiticsFragment, statiticsFragment.getTag()).commit();
            }
        });
        return v;
    }

}