package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class DienTich_Activity extends AppCompatActivity {
    Button btnXacNhan;
    RangeSeekBar rangeSeekBar;
    TextView textViewDTMin,textViewDTMax;
    int min,max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_tich_);
        btnXacNhan = findViewById(R.id.btnXacNhac_DienTich);
        rangeSeekBar = findViewById(R.id.range_seekbar_DT);
        textViewDTMin = findViewById(R.id.textViewDTMin);
        textViewDTMax = findViewById(R.id.TextViewDTMax);


        rangeSeekBar.setRangeValues(0,1000);
        rangeSeekBar.setSelectedMinValue(0);
        rangeSeekBar.setSelectedMaxValue(1000);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();
                min = (int)min_value;
                max = (int)max_value;
                textViewDTMin.setText(min+" m2 đến ");
                textViewDTMax.setText(max+"+ m2");

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("dientichMin",min);
                intent.putExtra("dientichMax",max);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

