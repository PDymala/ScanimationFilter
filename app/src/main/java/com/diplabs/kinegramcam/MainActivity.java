package com.diplabs.kinegramcam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;

public class MainActivity extends AppCompatActivity implements Settings.SettingDialogListener {
    private CameraKitView cameraKitView;
    private MyView myView;
    ImageButton buttonSettings;
    ImageButton buttonCameraSwitch;
    ImageButton buttonCloseApp;

    Settings settings;


    @Override
    public void applySettings(int speed, int phase, int segment) {
        myView.setAnimationSpeed(speed);
        myView.setPhase(phase);
        myView.setSegment(segment);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);


        cameraKitView = findViewById(R.id.camera);
        cameraKitView.requestPermissions(this);
        myView = new MyView(this);
        myView = findViewById(R.id.viewTouch);

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonCameraSwitch = findViewById(R.id.buttonScreen);

        buttonCloseApp = findViewById(R.id.buttonCloseApp);
        screenStatus = 0;


    }
    public void openSettings(View view){
        settings = new Settings(myView.getAnimationSpeedRatio(), myView.getPhase(), myView.getSegment());
        settings.show(getSupportFragmentManager(), "Settings");

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }


    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        cameraKitView.onPause();

    }



    @Override
    protected void onStop() {

        super.onStop();
        cameraKitView.onStop();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }




    int screenStatus = 0;  // 0 camera , 1 = screen
    public void screenChange(View view){

        if (screenStatus == 0){
            cameraKitView.onPause();
            myView.setBackgroundColor(Color.parseColor("#ffffffff"));

            screenStatus = 1;
        } else{

            cameraKitView.onResume();
            myView.setBackgroundColor(Color.parseColor("#00ffffff"));
            screenStatus = 0;

        }



    }

    public void closeApp(View view){
        cameraKitView.onStop();
        this.finishAndRemoveTask();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("speed", myView.getAnimationSpeedRatio());
        outState.putInt("phase", myView.getPhase());
        outState.putInt("segment", myView.getSegment());

      //  outState.putInt("screenStatus", screenStatus);




    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        myView.setAnimationSpeed(savedInstanceState.getInt("speed", myView.getAnimationSpeedRatio()));

        myView.setPhase(savedInstanceState.getInt("phase", myView.getPhase()));

        myView.setSegment(savedInstanceState.getInt("segment", myView.getSegment()));
      //  screenStatus = savedInstanceState.getInt("screenStatus", screenStatus);

    }
}