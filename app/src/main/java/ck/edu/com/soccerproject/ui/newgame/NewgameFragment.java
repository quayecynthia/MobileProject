package ck.edu.com.soccerproject.ui.newgame;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.Calendar;

import ck.edu.com.soccerproject.model.DatabaseHelper;
import ck.edu.com.soccerproject.model.PlaceAutoSuggestAdapter;
import ck.edu.com.soccerproject.R;

//Add a new game in the database
public class NewgameFragment extends Fragment{
    private DatabaseHelper myDatabase;
    private ImageButton button_addData;
    private ImageView editPhoto;
    private Button button_addPhotos;
    private EditText editFirstTeam, editSecondTeam, editScore, editDate;
    private AutoCompleteTextView editLocation;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    private Bitmap bitmap;

    //private PlacesClient placesClient;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);

        myDatabase = new DatabaseHelper(getActivity());

        editFirstTeam = root.findViewById(R.id.edit_firstTeam);
        editSecondTeam = root.findViewById(R.id.edit_secondTeam);
        editScore = root.findViewById(R.id.edit_score);
        editDate = root.findViewById(R.id.edit_date);
        editLocation = root.findViewById(R.id.edit_location);
        //set AutoComplete
        editLocation.setAdapter(new PlaceAutoSuggestAdapter(getActivity(), android.R.layout.simple_list_item_1));

        editPhoto = root.findViewById(R.id.new_photo);
        button_addData = root.findViewById(R.id.button_addData);
        button_addPhotos = root.findViewById(R.id.button_addPhotos);

        newDate();

        button_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty=false;

                if (editFirstTeam.getText().toString().matches("") || editSecondTeam.getText().toString().matches("") ||
                        editScore.getText().toString().matches("") || editDate.getText().toString().matches("") ||
                        editLocation.getText().toString().matches("") || editPhoto.getDrawable()==null) {
                    isEmpty=true;
                }

                if(!isEmpty){
                    boolean isInserted = myDatabase.insertData(editFirstTeam.getText().toString(),
                            editSecondTeam.getText().toString(),
                            editScore.getText().toString(),
                            editDate.getText().toString(),
                            editLocation.getText().toString(),
                            imageViewToByte(editPhoto));
                    myDatabase.exportData();
                    if( isInserted == true){
                        editFirstTeam.setText("");
                        editSecondTeam.setText("");
                        editScore.setText("");
                        editDate.setText("");
                        editLocation.setText("");
                        editPhoto.setImageResource(R.mipmap.ic_launcher);
                        Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Fill the fields", Toast.LENGTH_LONG).show();
                }


            }
        });


        button_addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission to access to camera
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    editPhoto.setEnabled(false);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},SELECT_PHOTO);
                }
                else{
                    editPhoto.setEnabled(true);
                }
                //Create AlertDialog to choose from gallery or camera
                final CharSequence[] items = {"Gallery", "Camera"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        //Open your gallery
                        if(item == 0){
                            Intent photoPickerIntent = new Intent (Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                        }
                        //Open the camera
                        else if(item == 1){
                            Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAPTURE_PHOTO);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return root;
    }

    //Convert the image to Byte to save it in the database
    public byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        return byteArray;
    }

    //Access permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                editPhoto.setEnabled(true);
            }
        }
    }

    //ProgressBar to open the photo
    public void setProgressBar(){
        //Set the progress bar
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading ...");
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        //Create a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressBarStatus <100) {
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Post the message to the queue
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if(progressBarStatus >= 100){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }

        }).start();
    }

    //add the photo to the imageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO && resultCode == getActivity().RESULT_OK) {
            final Uri imageUri = data.getData();
            try {
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                setProgressBar();
                editPhoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == CAPTURE_PHOTO && resultCode == getActivity().RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            //setProgressBar();
            editPhoto.setMaxWidth(100);
            editPhoto.setImageBitmap(bitmap);
        }
    }

    //Add Calendar to edit the date
    public void newDate() {
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current date
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //Create the dateDialog
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int newYear, int newMonth, int newDayOfMonth) {
                newMonth ++;
                editDate.setText(newYear + "/" + newMonth + "/" + newDayOfMonth);
            }
        };

    }
}