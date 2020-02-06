package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cse110_project.R;
import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

public class CreateRouteActivity extends AppCompatActivity {
    private RouteEntryDatabase database;
    private RouteEntryDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        database= Room.databaseBuilder(getApplicationContext(),RouteEntryDatabase.class,RouteEntryDatabase.DB_NAME).allowMainThreadQueries().build();
        dao=database.getRouteEntryDAO();

        final EditText name=findViewById(R.id.name_field);
        final EditText start=findViewById(R.id.start_field);
        final EditText step=findViewById(R.id.step_field);

        final RadioGroup runtype=findViewById(R.id.run_type);
        RadioButton runtype1=findViewById(R.id.loop_button);
        RadioButton runtype2=findViewById(R.id.out_and_back_button);

        final RadioGroup f_h=findViewById(R.id.flat_hilly);
        RadioButton f_h1=findViewById(R.id.flat_button);
        RadioButton f_h2=findViewById(R.id.hilly_button);

        final RadioGroup routetype=findViewById(R.id.route_type);
        RadioButton routetype1=findViewById(R.id.streets_button);
        RadioButton routetype2=findViewById(R.id.trail_button);

        final RadioGroup surfacetype=findViewById(R.id.surface_type);
        RadioButton surfacetype1=findViewById(R.id.even_button);
        RadioButton surfacetype2=findViewById(R.id.uneven_button);

        final RadioGroup difficulity=findViewById(R.id.difficulty);
        RadioButton difficulity1=findViewById(R.id.easy_button);
        RadioButton difficulity2=findViewById(R.id.moderate_button);
        RadioButton difficulity3=findViewById(R.id.hard_button);

        MultiAutoCompleteTextView note=findViewById(R.id.note_field);

        Button save=findViewById(R.id.save_buttton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")||start.getText().toString().equals("")||step.getText().toString().equals("")
                ||runtype.getCheckedRadioButtonId()==-1||f_h.getCheckedRadioButtonId()==-1||routetype.getCheckedRadioButtonId()==-1||
                surfacetype.getCheckedRadioButtonId()==-1||difficulity.getCheckedRadioButtonId()==-1){
                    Toast.makeText(CreateRouteActivity.this,"Invalid Input",Toast.LENGTH_LONG).show();
                }
                else{
                    RouteEntry routeEntry=new RouteEntry(name.getText().toString(),start.getText().toString());
                    
                    routeEntry.setRun(runtype.getCheckedRadioButtonId());
                    routeEntry.setTerrain(f_h.getCheckedRadioButtonId());
                    routeEntry.setRoadType(routetype.getCheckedRadioButtonId());
                    routeEntry.setRoadCondition(surfacetype.getCheckedRadioButtonId());
                    routeEntry.setLevel(difficulity.getCheckedRadioButtonId());

                    dao.insertRoute(routeEntry);
                }
            }
        });



    }
}
