package com.example.android.pets.Data;

import android.provider.BaseColumns;

/**
 * Created by Salvador on 24/10/2016.
 */

public class ShelterContract {

    private ShelterContract () {

    }

    public static abstract class Pets implements BaseColumns {


        public static final String TABLE_NAME ="pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        /**
         * Possible values for the pets sex
         */

        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_UNKNOWN = 0;


    }





}
