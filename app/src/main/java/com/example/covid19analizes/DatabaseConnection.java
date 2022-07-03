
package com.example.covid19analizes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 class DBHandler extends SQLiteOpenHelper {
    public  String str[];





    private static final String DB_NAME = "CovidDatabaseAlgo";
    private static final int DB_VERSION = 1;
    private static final String TABLE_WORLD = "World";
     private static final String TABLE_COUNTRY = "CtTable";
     private static final String TABLE_UserID = "userTable";
     private static final String TABLE_State = "State";
     private static final String ID_COL = "Id";
    private static final String updated = "updated";
    private static final String country = "country";
  //  private static final String countryInfo = "countryInfo";
    private static final String cases = "cases";
    private static final String todayCases = "todayCases";
    private static final String deaths = "deaths";
    private static final String todayDeaths = "todayDeaths";
    private static final String recovered = "recovered";
    private static final String todayRecovered = "todayRecovered";
    private static final String active = "active";
    private static final String critical = "critical";
    private static final String casesPerOneMillion = "casesPerOneMillion";
    private static final String deathsPerOneMillion = "deathsPerOneMillion";
    private static final String tests = "tests";
    private static final String testsPerOneMillion = "testsPerOneMillion";
    private static final String population = "population";
    private static final String continent = "continent";
    private static final String oneCasePerPeople = "oneCasePerPeople";
    private static final String oneDeathPerPeople = "oneDeathPerPeople";
    private static final String oneTestPerPeople = "oneTestPerPeople";
    private static final String undefined = "undefined";
    private static final String activePerOneMillion = "activePerOneMillion";
    private static final String recoveredPerOneMillion = "recoveredPerOneMillion";
   private static final String criticalPerOneMillion = "criticalPerOneMillion";
    private static final String affectedCountries = "affectedCountries";




    //Register
     private static final String name = "name";
     private static final String email = "email";
     private static final String password = "password";
     private static final String confirmPass = "confirmPass";

     //State
     private static final String stateName = "stateName";
     private static final String stateCases = "stateCases";

     private static final String stateNewCases = "stateNewCases";
     private static final String stateActiveCases = "stateActiveCases";
     private static final String stateRecoveredCases = "stateRecoveredCases";
     private static final String stateNewRecoveredCases = "stateNewRecoveredCases";
     private static final String stateDeathCases = "stateDeathCases";
     private static final String stateNewDeathCases = "stateNewDeathCases";



     public DBHandler(Context context) {

        super(context, DB_NAME, null, 10000);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String registerQuery = "CREATE TABLE " + TABLE_UserID + " ("
                + name + " TEXT , "
                + email + " TEXT , "
                + password + " TEXT , "
                + confirmPass + " TEXT )"
                ;


        String stateQuery = "CREATE TABLE " + TABLE_State + " ("
                + stateName + " TEXT , "
                + stateCases + " TEXT , "
                + stateNewCases + " TEXT , "
                + stateActiveCases + " TEXT , "
                + stateRecoveredCases + " TEXT , "
                + stateNewRecoveredCases + " TEXT , "
                + stateDeathCases + " TEXT , "
                + stateNewDeathCases + " TEXT )"
                ;




        String query = "CREATE TABLE " + TABLE_WORLD + " ("

                + updated + " TEXT , "
                + cases + " TEXT , "
                + todayCases + " TEXT , "
                + deaths + " TEXT , "
                + todayDeaths + " TEXT , "
                + recovered + " TEXT , "
                + todayRecovered + " TEXT , "
                + active + " TEXT , "
                + critical + " TEXT , "
                + casesPerOneMillion + " TEXT , "
                + deathsPerOneMillion + " TEXT , "
                + tests + " TEXT , "
                + testsPerOneMillion + " TEXT , "
                + population + " TEXT , "
                + oneCasePerPeople + " TEXT , "
                + oneDeathPerPeople + " TEXT , "
                + oneTestPerPeople + " TEXT , "
                + activePerOneMillion + " TEXT , "
                + recoveredPerOneMillion + " TEXT , "
                + criticalPerOneMillion + " TEXT , "
                + affectedCountries + " TEXT )"
                ;

        String query1 = "CREATE TABLE " + TABLE_COUNTRY + " ("

                + updated + " TEXT , "
                + country + " TEXT ,"
                + cases + " TEXT , "
                + todayCases + " TEXT , "
                + deaths + " TEXT , "
                + todayDeaths + " TEXT , "
                + recovered + " TEXT , "
                + todayRecovered + " TEXT , "
                + active + " TEXT , "
                + critical + " TEXT , "
                + casesPerOneMillion + " TEXT , "
                + deathsPerOneMillion + " TEXT , "
                + tests + " TEXT , "
                + testsPerOneMillion + " TEXT , "
                + population + " TEXT , "
                + continent + " TEXT , "
                + oneCasePerPeople + " TEXT , "
                + oneDeathPerPeople + " TEXT , "
                + oneTestPerPeople + " TEXT , "
                + activePerOneMillion + " TEXT , "
                + recoveredPerOneMillion + " TEXT , "
                + criticalPerOneMillion + " TEXT ) "

                ;







        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(registerQuery);
        db.execSQL(stateQuery);
    }


    public  void addState(ArrayList<String> entryList)
    {
        str = new String[]{
                "stateName",
                "stateCases","stateNewCases","stateActiveCases","stateRecoveredCases","stateNewRecoveredCases","stateDeathCases","stateNewDeathCases"};


        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();


        for(int i = 0 ; i< str.length;  i++)
        {
            values.put(str[i], entryList.get(i));

        }



        db.insert(TABLE_State,null,values);


        db.close();

    }


     public void addUser(ArrayList<String> key) {
         str = new String[]{
                 "name",
                 "email",
                 "password",
                 "confirmPass"};


         SQLiteDatabase db = this.getReadableDatabase();

         ContentValues values = new ContentValues();

         for(int i = 0 ; i < 4 ; i++)
         {

             values.put(str[i], key.get(i));
         }
         db.insert(TABLE_UserID,null,values);


         db.close();

     }



    public void addWorldData(ArrayList<String> key) {
        str = new String[]{
                "updated",
                "cases",
                "todayCases",
                "deaths",
                "todayDeaths",
                "recovered",
                "todayRecovered",
                "active",
                "critical",
                "casesPerOneMillion",
                "deathsPerOneMillion",
                "tests",
                "testsPerOneMillion",
                "population",
                "oneCasePerPeople",
                "oneDeathPerPeople",
                "oneTestPerPeople",
                "activePerOneMillion",
                "recoveredPerOneMillion",
                "criticalPerOneMillion",
                "affectedCountries"};
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

            for(int i = 0 ; i < 21 ; i++)
            {

                values.put(str[i], key.get(i));
            }






        db.insert(TABLE_WORLD,null,values);


        db.close();
    }
     public void addCountryData(ArrayList<String> key) {
         str = new String[]{
                 "updated",
                 "country",
                 "cases",
                 "todayCases",
                 "deaths",
                 "todayDeaths",
                 "recovered",
                 "todayRecovered",
                 "active",
                 "critical",
                 "casesPerOneMillion",
                 "deathsPerOneMillion",
                 "tests",
                 "testsPerOneMillion",
                 "population",
                 "continent",
                 "oneCasePerPeople",
                 "oneDeathPerPeople",
                 "oneTestPerPeople",
                 "activePerOneMillion",
                 "recoveredPerOneMillion",
                 "criticalPerOneMillion"
               };
         SQLiteDatabase db = this.getReadableDatabase();

         ContentValues values = new ContentValues();

         for(int i = 0 ; i < 22 ; i++)
         {

             values.put(str[i], key.get(i));
         }






         db.insert(TABLE_COUNTRY,null,values);


         db.close();
     }

     public  void update(String state, String cases)

     {


         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();

         values.put(stateName, state);
         values.put(stateCases ,cases);

         db.update(TABLE_State, values, stateName+"=?", new String[]{state});
         db.close();
     }


     public  void updateAllStateInfo(String divName, ArrayList<String> infoList)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();

         str = new String[]{
                 "stateName",
                 "stateCases","stateNewCases","stateActiveCases","stateRecoveredCases","stateNewRecoveredCases","stateDeathCases","stateNewDeathCases"};

         for(int i = 0; i< infoList.size();  i++)
         {

             values.put(str[i], infoList.get(i));
         }

         db.update(TABLE_State, values, stateName+"=?", new String[]{divName});
         db.close();

     }




     public void deleteStatedata()
     {
         SQLiteDatabase db = this.getWritableDatabase();

         db.execSQL("delete from "+ TABLE_State);
         db.close();
     }
    public void deleteWorldData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_WORLD);
        db.close();
    }
     public void deleteCountryData()
     {
         SQLiteDatabase db = this.getWritableDatabase();

         db.execSQL("delete from "+ TABLE_COUNTRY);
         db.close();
     }
     public  Cursor readState()
     {
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_State, null);
         return cursorCourses;
     }
     public  Cursor readOneState(String divName)
     {

         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_State + " WHERE " + stateName + "=" + "?",new String[]{divName},null);
         return cursorCourses;
     }


    public Cursor readWorldData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_WORLD, null);
        return cursorCourses;
    }
     public Cursor readCountryData() {
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_COUNTRY, null);
         return cursorCourses;
     }

     public Cursor readAdminData() {
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_UserID, null);
         return cursorCourses;
     }

     public String checkEmail(String email)
     {
         SQLiteDatabase db = this.getWritableDatabase();

         Cursor cursor = db.query(this.TABLE_UserID,null,this.email+ "=?", new String[]{email},null,null,null);

         while (cursor.moveToNext()) {

             if (cursor.isFirst()) {

                 cursor.moveToFirst();

                 cursor.close();
                 return "Email Found";


             }
         }
         return "Not found";
     }


     public String loginStatus(String email)
     {
         String ans ;
         SQLiteDatabase db = this.getWritableDatabase();

         Cursor cursor = db.query(this.TABLE_UserID,null,this.email+ "=?", new String[]{email},null,null,null);

         while (cursor.moveToNext()) {

             if (cursor.isFirst()) {

                 cursor.moveToFirst();
                ans =  cursor.getString(cursor.getColumnIndex(this.password));
                 cursor.close();
                 return  ans;
             }
         }
         return "Not found";
     }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORLD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UserID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_State);
        onCreate(db);
    }
}