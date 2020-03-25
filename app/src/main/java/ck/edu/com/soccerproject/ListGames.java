package ck.edu.com.soccerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListGames extends RecyclerView.Adapter<ListGames.MyViewHolder> {

    Context context;
    ArrayList<Game> gamesList;

    public ListGames(Context context, ArrayList<Game> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    @Override
    public ListGames.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_game, parent, false);
        MyViewHolder vHolder = new MyViewHolder (v);
        return  vHolder;
    }

    @Override
    public void onBindViewHolder(ListGames.MyViewHolder holder, int position) {
        holder.display_date.setText(gamesList.get(position).getDate());
        holder.display_opponent.setText(gamesList.get(position).getOpponent());
        holder.display_score.setText(gamesList.get(position).getScore());
        holder.display_location.setText(gamesList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private TextView display_score;
        private TextView display_opponent;
        private TextView display_date;
        private TextView display_location;

        public MyViewHolder (View itemView){
            super(itemView);

            display_date =  itemView.findViewById(R.id.display_date);
            display_opponent =  itemView.findViewById(R.id.display_opponent);
            display_score =  itemView.findViewById(R.id.display_score);
            display_location =  itemView.findViewById(R.id.display_location);
        }
    }
}
