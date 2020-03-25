package ck.edu.com.soccerproject.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
                Toast.makeText(getActivity(), "Switch to French", Toast.LENGTH_LONG).show();
                Locale locale = new Locale("fr");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;

                /*getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
                Fragment frg = getFragmentManager().findFragmentByTag(getTag());
                //Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*/
            }

        });

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;*/

            }
        });
        return v;
    }
}
