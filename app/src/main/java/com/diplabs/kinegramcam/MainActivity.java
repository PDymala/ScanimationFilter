package com.diplabs.kinegramcam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Audio;
import com.otaliastudios.cameraview.controls.Engine;

public class MainActivity extends AppCompatActivity implements Settings.SettingDialogListener {
    private static final String TAG = "kinegramcam";
    CameraView cameraView;
    private MyView myView;
    ImageButton buttonSettings;
    ImageButton buttonCameraSwitch;
    ImageButton buttonCloseApp;
    Settings settings;
    ImageButton buttonZoomUp;
    ImageButton buttonZoomDown;
    boolean visibleUI = true;
    int screenStatus = 0;  // 0 camera , 1 = screen

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


        initalizeUI();
        initalizeCamera();
        initalizeVariables();

    }

    public void initalizeCamera() {
        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);
        cameraView.setEngine(Engine.CAMERA2);
        cameraView.setAudio(Audio.OFF);
        cameraView.setRequestPermissions(true);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void initalizeUI() {
        buttonZoomUp = findViewById(R.id.zoomUp);
        buttonZoomDown = findViewById(R.id.zoomDown);

        myView = new MyView(this);
        myView = findViewById(R.id.viewTouch);
        myView.setOnTouchListener(new View.OnTouchListener() {
            private final GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onDoubleTap(MotionEvent e) {
//                    Log.d("TEST", "onDoubleTap");
//                    hideSystemUI();
                    if (visibleUI) {
                        hideUI();

                        visibleUI = false;
                    } else {
                        showUI();
                        visibleUI = true;
                    }


                    return super.onDoubleTap(e);
                }

            });


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                gestureDetector.onTouchEvent(event);
                return true;
            }


        });

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonCameraSwitch = findViewById(R.id.buttonScreen);

        buttonCloseApp = findViewById(R.id.buttonCloseApp);

    }

    public void initalizeVariables() {
        screenStatus = 0;
        visibleUI = true;
    }

    public void openSettings(View view) {
        settings = new Settings(myView.getAnimationSpeedRatio(), myView.getPhase(), myView.getSegment());
        settings.show(getSupportFragmentManager(), "Settings");
    }


    @Override
    protected void onStart() {
        super.onStart();
        cameraView.open();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {

        super.onPause();
        cameraView.close();

    }



    @Override
    protected void onStop() {

        super.onStop();
        cameraView.close();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        cameraView.destroy();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // camera can be turned on
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                cameraView.setRequestPermissions(true);
//
//            } else {
//                // camera will stay off
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//                cameraView.setRequestPermissions(false);
//
//            }
//        }
//    }


    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }





    public void screenChange(View view){

        if (screenStatus == 0){
//          cameraView.open();
            myView.setBackgroundColor(Color.parseColor("#ffffffff"));

            screenStatus = 1;
        } else{

//            cameraView.close();
            myView.setBackgroundColor(Color.parseColor("#00ffffff"));
            screenStatus = 0;

        }



    }

    public void closeApp(View view){
        cameraView.close();
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

    public void zoomUp(View view) {
        if (cameraView.getZoom() >= 0 && cameraView.getZoom() <= 1) {
            cameraView.setZoom(cameraView.getZoom() + 0.1f);
        }

    }

    public void zoomDown(View view) {
        if (cameraView.getZoom() >= 0.1 && cameraView.getZoom() <= 1) {
            cameraView.setZoom(cameraView.getZoom() - 0.1f);
        }
    }

    public void showUI() {

        buttonZoomUp.setVisibility(View.VISIBLE);
        buttonZoomDown.setVisibility(View.VISIBLE);
        buttonSettings.setVisibility(View.VISIBLE);
        buttonCameraSwitch.setVisibility(View.VISIBLE);
        buttonCloseApp.setVisibility(View.VISIBLE);

    }

    public void hideUI() {


        buttonZoomUp.setVisibility(View.INVISIBLE);
        buttonZoomDown.setVisibility(View.INVISIBLE);
        buttonSettings.setVisibility(View.INVISIBLE);
        buttonCameraSwitch.setVisibility(View.INVISIBLE);
        buttonCloseApp.setVisibility(View.INVISIBLE);
    }


}