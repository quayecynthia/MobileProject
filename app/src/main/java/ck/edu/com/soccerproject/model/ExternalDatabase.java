package ck.edu.com.soccerproject.model;

import android.database.Cursor;
import android.os.AsyncTask;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ExternalDatabase extends AsyncTask<Cursor, Void, Cursor> {

    Connection con;

    @Override
    protected Cursor doInBackground(Cursor... nothing) {
        try{

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con= DriverManager.getConnection("jdbc:mysql://10.0.2.2:8889/soccer", "app", "app");
            System.out.println("connection established");
        }
        catch(Exception e){
            System.out.println(("ERROR"));
            System.out.println(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Cursor data) {
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("DELETE from matches_table");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            String query = "INSERT INTO matches_table (ID, FIRST_TEAM , SECOND_TEAM, SCORE, DATE, LOCATION, IMAGE) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            if(data.moveToFirst()){

                ps.setInt(1, data.getInt(0));
                ps.setString(2, data.getString(1));
                ps.setString(3, data.getString(2));
                ps.setString(4, data.getString(3));
                ps.setString(5, data.getString(4));
                ps.setString(6, data.getString(5));
                ps.setString(7, data.getString(6));
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (Exception e){
            System.out.println(("ERROR"));
            System.out.println(e);
        }

    }
}

