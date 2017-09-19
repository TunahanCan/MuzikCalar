package com.example.cann.muzikcalar.Envarter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cann on 3.12.2016.
 */

 public class VeriTabani extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";//database adı

    private ArrayList<HashMap<String,String>> Sarkiyerleri;
    private static final String TABLE_NAME = "sarki_listesi";
    private static String Sarki_Adi = "sarki_Adi";
    private static String Sarki_ID = "id";
    private static String Sarki_YER ="sarki_yer";



    public VeriTabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SarkiBulGetir getir = new SarkiBulGetir();
        this.Sarkiyerleri = getir.SarkiGetir();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + Sarki_ID + " INTEGER PRIMARY KEY,"
                + Sarki_Adi + " TEXT,"
                + Sarki_YER + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SarkiSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Sarki_Adi + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void SarkiEkle(String ktpad, String ktpyer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Sarki_Adi, ktpad);
        values.put(Sarki_YER, ktpyer);
        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public void SarkilariVeritabaninaKaydet()
    {
        HashMap<String,String> gecici;
        for(int i =0 ; i<Sarkiyerleri.size() ; i++)
        {
            gecici = Sarkiyerleri.get(i);
            SarkiEkle(gecici.get("songTitle").toString(),gecici.get("songPath"));
        }

    }


    public  ArrayList<SongSinifi>  VeriTabanindanGetir(){
        ArrayList<SongSinifi> SongList = new ArrayList<SongSinifi>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SongSinifi sngclas = new SongSinifi();
                sngclas.setSongID(Integer.parseInt(cursor.getString(0)));
                sngclas.setSongTitle(cursor.getString(1));
                sngclas.setSongTitle(cursor.getString(2));

                // Adding contact to list
                SongList.add(sngclas);
            } while (cursor.moveToNext());
        }
        return SongList;
    }


    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        //Login uygulamasında kullanacağız
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }


    public void resetTables(){
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    public int getSarkiCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }




}
