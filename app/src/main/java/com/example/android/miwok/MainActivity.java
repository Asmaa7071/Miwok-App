/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // find numbers text view by id and intent to numbers activity
        TextView numbers = (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new  Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }//end onClick Method numbers textView
        });//End onclickListener numbers textView

        // find Family members text view by id and intent to family activity
        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FamilyIntent = new  Intent(MainActivity.this,FamilyActivity.class);
                startActivity(FamilyIntent);
            }//end onClick Method family textView
        });//End onclickListener family textView

        // find colors members text view by id and intent to colors activity
        TextView colors = (TextView) findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent colorsIntent = new  Intent(MainActivity.this,ColorsActivity.class);
                startActivity(colorsIntent);
            }//end onClick Method colors textView
        });//end onClick Method colors textView

        // find phrases members text view by id and intent to phrases activity
        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phrasesIntent = new  Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(phrasesIntent);
            }//end onClick Method phrases textView
        });//end onClick Method phrases textView

    }//End onCreate method
}//End Main class
