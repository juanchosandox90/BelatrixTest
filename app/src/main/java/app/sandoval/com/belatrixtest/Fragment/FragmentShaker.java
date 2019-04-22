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
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import app.sandoval.com.belatrixtest.R;

public class FragmentShaker extends Fragment {

    private SensorManager sm;

    private SeekBar seekBar;

    private float shakeValue;

    private float acelVal; // CURRENT ACCELERATION VALUE AND GRAVITY
    private float acelLast; // LAST ACCELERATION VALUE AND GRAVITY
    private float shake; // ACCELERATION VALUE DIFFER FROM GRAVITY

    private boolean gotSensor = false;

    int colorChange = 0;

    TextView txtView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shaker_fragment, container, false);

        txtView = view.findViewById(R.id.shaker_color_txt_view);

        seekBar = view.findViewById(R.id.seek_bar);

        seekBarSetUp();

        shakeValue = 8;

        sm = (SensorManager) Objects.requireNonNull(getContext()).getSystemService(Context.SENSOR_SERVICE);

        accelerometerSetUp();

        return view;
    }

    private void seekBarSetUp() {

        seekBar.setProgress(20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // INVERTING PROGRESSBAR VALUE

                shakeValue = (float) (100 - progress) / 10;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void accelerometerSetUp() {

        gotSensor = true;

        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0f;

    }


    private SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > shakeValue) {


                if (colorChange == 0) {

                    txtView.setTextColor(getResources().getColor(R.color.colorAccent));

                    colorChange++;
                } else {

                    txtView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    colorChange--;
                }


            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) accelerometerSetUp();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (gotSensor) sm.unregisterListener(sensorListener);
    }

}
