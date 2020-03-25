package ck.edu.com.soccerproject.ui.allgames;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ck.edu.com.soccerproject.DatabaseHelper;
import ck.edu.com.soccerproject.Game;
import ck.edu.com.soccerproject.ListGames;
import ck.edu.com.soccerproject.R;

public class AllgamesFragment extends Fragment {
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper myDatabase;
    Cursor cursor;

    private RecyclerView recyclerView;
    private ArrayList<Game> gamesTable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_allgames, container, false);
        recyclerView = root.findViewById(R.id.recycler_allgames);
        gamesTable = new ArrayList<Game>();
        ListGames listGames = new ListGames (getContext(), gamesTable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listGames);

        //instantiate the database
        myDatabase = new DatabaseHelper(getActivity());
        sqLiteDatabase = myDatabase.getReadableDatabase();
        //retrieve all data from cursor
        cursor = myDatabase.getData();
        if(cursor.moveToFirst()){
            do{
                String opponent, score, date, location;
                opponent = cursor.getString(1);
                score = cursor.getString(2);
                date = cursor.getString(3);
                location = cursor.getString(4);
                Game game = new Game(opponent, score, date, location);
                gamesTable.add(game);

            }while(cursor.moveToNext());
        }
        return root;
    }

}