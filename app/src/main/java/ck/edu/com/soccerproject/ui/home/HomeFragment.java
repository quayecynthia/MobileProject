package ck.edu.com.soccerproject.ui.home;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;

import ck.edu.com.soccerproject.R;
import ck.edu.com.soccerproject.ui.newgame.NewgameFragment;
import ck.edu.com.soccerproject.ui.previousgame.PreviousgameFragment;

public class HomeFragment extends Fragment {
    private Button newButton, previousButton;
    private Button frenchButton, englishButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        newButton = v.findViewById(R.id.button_newGame);
        previousButton = v.findViewById(R.id.button_previousGame);
        frenchButton = v.findViewById(R.id.button_french);
        englishButton = v.findViewById(R.id.button_english);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewgameFragment newgameFragment = new NewgameFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment, newgameFragment, newgameFragment.getTag()).commit();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviousgameFragment previousgameFragment = new PreviousgameFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment, previousgameFragment, previousgameFragment.getTag()).commit();
            }
        });

        frenchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "En français", Toast.LENGTH_LONG).show();
                setLocale("fr");
                getActivity().recreate();
            }
        })  ;

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Switch to English", Toast.LENGTH_LONG).show();
                setLocale("en");
                getActivity().recreate();
            }
        });
        return v;
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
    }
}
