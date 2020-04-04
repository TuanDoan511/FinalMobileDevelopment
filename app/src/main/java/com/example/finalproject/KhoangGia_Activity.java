package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class KhoangGia_Activity extends AppCompatActivity {
    Button btnXacNhan;
    RangeSeekBar rangeSeekBar;
    TextView textViewGiaMin,textViewGiaMax;
    int min,max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoang_gia_);

        btnXacNhan = findViewById(R.id.buttonXacNhan);
        rangeSeekBar = findViewById(R.id.range_seekbar);
        textViewGiaMin = findViewById(R.id.textViewGiaMin);
        textViewGiaMax = findViewById(R.id.TextViewGiaMax);


        rangeSeekBar.setRangeValues(0,300);
        rangeSeekBar.setSelectedMinValue(0);
        rangeSeekBar.setSelectedMaxValue(300);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();
                 min = (int)min_value;
                 max = (int)max_value;
                textViewGiaMin.setText(min+"00,000,000VNĐ đến ");
                textViewGiaMax.setText(max+"00,000,000VNĐ");

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("giaMin",min);
                intent.putExtra("giaMax",max);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
