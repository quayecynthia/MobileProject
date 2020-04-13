package ck.edu.com.soccerproject.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ck.edu.com.soccerproject.R;

//List of all games in a recyclerView
public class ListGames extends RecyclerView.Adapter<ListGames.MyViewHolder> {

    private Context context;
    private ArrayList<Game> gamesList;

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

    //Display all games in the recycler View
    @Override
    public void onBindViewHolder(ListGames.MyViewHolder holder, int position) {
        holder.display_date.setText(gamesList.get(position).getDate());
        holder.display_firstTeam.setText(gamesList.get(position).getFirst_team());
        holder.display_secondTeam.setText(gamesList.get(position).getSecond_team());
        holder.display_score.setText(gamesList.get(position).getScore());
        holder.display_location.setText(gamesList.get(position).getLocation());

        byte[] image = gamesList.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.display_image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    //RecyclerView class
    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private TextView display_score;
        private TextView display_firstTeam;
        private TextView display_secondTeam;
        private TextView display_date;
        private TextView display_location;
        private ImageView display_image;

        public MyViewHolder (View itemView){
            super(itemView);

            display_date =  itemView.findViewById(R.id.display_date);
            display_firstTeam =  itemView.findViewById(R.id.display_firstTeam);
            display_secondTeam =  itemView.findViewById(R.id.display_secondTeam);
            display_score =  itemView.findViewById(R.id.display_score);
            display_location =  itemView.findViewById(R.id.display_location);
            display_image = itemView.findViewById(R.id.display_image);
        }
    }
}
