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
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.Data.PetDbHelper;
import com.example.android.pets.Data.PetsContract;
import com.example.android.pets.Data.PetsContract.Pets;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);

        displayDatabaseInfo();



        // SQLiteDatabase db = mDbHelper.getReadableDatabase();

    }

    @Override
    public void onResume() {

        super.onResume();

        displayDatabaseInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        PetDbHelper mDbHelper = new PetDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        //Cursor cursor = db.rawQuery("SELECT * FROM " + PetsContract.Pets.TABLE_NAME, null);

        // Make a Query using the query method from the SQLiteDatabase Class

        Cursor cursor = db.query(PetsContract.Pets.TABLE_NAME,
                null,       // The table to query
                null,       // The columns to return
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE clause
                null,       // don't group the rows
                null        // don't filter by row groups
        );


        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            /*TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());*/

            /* Create a header in the Text View that looks like this:

                The pets table contains <number of rows in Cursor> pets.

                In the while loop below, iterate through the rows of the cursor and display
                the information from each column

             */

            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(Pets._ID + " - " +
                    Pets.COLUMN_PET_NAME + " - " +
                    Pets.COLUMN_PET_BREED + " - " +
                    Pets.COLUMN_PET_GENDER + " - " +
                    Pets.COLUMN_PET_WEIGHT + " \n ");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Pets._ID);
            int nameColumnIndex = cursor.getColumnIndex(Pets.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(Pets.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(Pets.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(Pets.COLUMN_PET_WEIGHT);



            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int CurrentId = cursor.getInt(idColumnIndex);
                String CurrentName = cursor.getString(nameColumnIndex);
                String CurrentBreed = cursor.getString(breedColumnIndex);
                int CurrentGender = cursor.getInt(genderColumnIndex);
                int CurrentWeight = cursor.getInt(weightColumnIndex);

 /*

                CharSequence gender;

                switch (CurrentGender) {
                    case Pets.GENDER_UNKNOWN :
                        gender =  getText(R.string.gender_unknown);
                    case Pets.GENDER_FEMALE:
                        gender = getText(R.string.gender_female);
                    case Pets.GENDER_MALE:
                        gender = getText(R.string.gender_male);

                }*/

                displayView.append("\n" + CurrentId + "   " +
                        CurrentName + "   " +
                        CurrentBreed + "   " +
                        CurrentGender + "   " +
                        CurrentWeight

                    );

            }


        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertPet() {

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetsContract.Pets.COLUMN_PET_NAME, "Toto" );
        values.put(PetsContract.Pets.COLUMN_PET_BREED, "Terrier" );
        values.put(PetsContract.Pets.COLUMN_PET_GENDER, PetsContract.Pets.GENDER_MALE);
        values.put(PetsContract.Pets.COLUMN_PET_WEIGHT, 7);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PetsContract.Pets.TABLE_NAME, null, values);

    }
}
