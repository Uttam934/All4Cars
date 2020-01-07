package com.allforcars.all4cars.Test.MaterialShowcaseSequence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.allforcars.all4cars.R;

public class MaterialShowcaseSequence extends AppCompatActivity {

    ImageView img_logo_en_in,Dob_btn;
    private static final String SHOWCASE_ID = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcase_sequence);
        img_logo_en_in =findViewById(R.id.img_logo_en_in);
        Dob_btn =findViewById(R.id.Dob_btn);



    }


    public  void test()

    {

//        ShowcaseConfig config = new ShowcaseConfig();
//        config.setDelay(500); // half second between each showcase view
//
//        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(MaterialShowcaseSequence.this, SHOWCASE_ID);
//
//        sequence.setConfig(config);
//        sequence.addSequenceItem(img_logo_en_in, "This is button one", "GOT IT");
//        sequence.addSequenceItem(Dob_btn, "This is button two", "GOT IT");
//        sequence.start();


    }
}
