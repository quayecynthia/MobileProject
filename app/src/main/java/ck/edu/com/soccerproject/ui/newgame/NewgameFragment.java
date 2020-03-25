package ck.edu.com.soccerproject.ui.newgame;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import ck.edu.com.soccerproject.DatabaseHelper;
import ck.edu.com.soccerproject.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewgameFragment extends Fragment{
    DatabaseHelper myDatabase;
    ImageButton button_addData;
    EditText editOpponent, editScore, editDate, editLocation;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);

        myDatabase = new DatabaseHelper(getActivity());

        editOpponent = root.findViewById(R.id.edit_oppenent);
        editScore = root.findViewById(R.id.edit_score);
        editDate = root.findViewById(R.id.edit_date);
        editLocation = root.findViewById(R.id.edit_location);
        button_addData = root.findViewById(R.id.button_addData);

        newDate();

        button_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDatabase.insertData(editOpponent.getText().toString(),
                        editScore.getText().toString(),
                        editDate.getText().toString(),
                        editLocation.getText().toString());
                if( isInserted == true)
                    Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;

    }

    public void newDate() {
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int newYear, int newMonth, int newDayOfMonth) {
                newMonth ++;
                Log.d(TAG, "onDateSet: yyyy:mm:dd:" + newYear + "/" + newMonth + "/" + newDayOfMonth);
                editDate.setText(newYear + "/" + newMonth + "/" + newDayOfMonth);
            }
        };

    }
}