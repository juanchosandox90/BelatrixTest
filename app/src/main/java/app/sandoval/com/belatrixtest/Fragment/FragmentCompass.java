package app.sandoval.com.belatrixtest.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import app.sandoval.com.belatrixtest.R;
import app.sandoval.com.belatrixtest.View.CompassView;

public class FragmentCompass extends Fragment implements SensorEventListener {
    private CompassView compassView;

    private SensorManager sm;
    private Sensor rotationVector, accelerometer, magnetometer;
    private int delta;

    private float[] rMat = new float[9];
    private float[] orientation = new float[9];
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean haveSensor = false, haveSecondSensor=false;
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compass_fragment, container, false);

        compassView = view.findViewById(R.id.compass_view);

        sm = (SensorManager) Objects.requireNonNull(getContext()).getSystemService(Context.SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if(event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            delta = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360)%360);

        }

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(event.values, 0, lastAccelerometer, 0 , event.values.length);
            lastAccelerometerSet= true;

        } else if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){

            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            lastMagnetometerSet=true;

        }

        if(lastAccelerometerSet&&lastMagnetometerSet){

            SensorManager.getRotationMatrix(rMat, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            delta = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360)%360);

        }

        delta = Math.round(delta);
        compassView.setRotation(-delta);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSensors(){

        if (sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {

            if(sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)==null || sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null)
                Toast.makeText(getContext(), "No sensors available!", Toast.LENGTH_LONG).show();

            else {

                accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

                haveSensor = sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
                haveSecondSensor = sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
            }

        } else {

            rotationVector = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = sm.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_UI);

        }

    }

    public void stopSensors(){

        if(haveSecondSensor&&haveSensor){

            sm.unregisterListener(this, accelerometer);
            sm.unregisterListener(this, magnetometer);

        } else if(haveSensor) sm.unregisterListener(this, rotationVector);

    }

    @Override
    public void onPause() {
        super.onPause();
        stopSensors();

    }

    @Override
    public void onResume() {
        super.onResume();
        startSensors();

    }

}
