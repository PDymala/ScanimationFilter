package com.diplabs.kinegramcam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Settings extends AppCompatDialogFragment {
    private EditText editTextSpeed;
    private EditText editTextPhase;
    private EditText editTextSegment;

    private  SettingDialogListener listener;

    int animationSpeedRatio;
    int phase;
    int segment;

    public Settings(int animationSpeedRatio, int phase, int segment){
        this.animationSpeedRatio = animationSpeedRatio;
        this.phase = phase;
        this.segment = segment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.layout_settings, null);

        builder.setView(view)
                .setTitle("settings")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int speed = Integer.parseInt(editTextSpeed.getText().toString());
                        int phase = Integer.parseInt(editTextPhase.getText().toString()) ;
                        int segment =  Integer.parseInt(editTextSegment.getText().toString());

                        
                        if (segment <=0){
                            Toast.makeText(view.getContext(), "Segment must be greater than 0 px", Toast.LENGTH_SHORT).show();
                        } else {
                            if (phase <=1){

                                Toast.makeText(view.getContext(), "Phase must be greater than 1", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                if (speed <0 ){
                                    Toast.makeText(view.getContext(), "Speed must be greater or equal to 0", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    listener.applySettings(speed,phase,segment);
                                }


                            }


                        }





                    }

                });

        editTextSpeed = view.findViewById(R.id.editTextSpeed);
         editTextPhase = view.findViewById(R.id.editTextPhase);
         editTextSegment = view.findViewById(R.id.editTextSegment);

         editTextSpeed.setText(Integer.toString(animationSpeedRatio));

        editTextPhase.setText(Integer.toString(phase));

        editTextSegment.setText(Integer.toString(segment));


        return builder.create();
    }
    public interface SettingDialogListener{
        void applySettings(int speed, int phase, int segment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SettingDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

}
