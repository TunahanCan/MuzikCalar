package com.example.cann.muzikcalar.Envarter;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cann on 22.11.2016.
 */

public class SarkiBulGetir {

    String media_path = Environment.getExternalStorageDirectory().getPath();
    String[] splitPath= media_path .split("/");
    final String MEDIA_PATH = "/" + splitPath[1] + "/";

    public  ArrayList<HashMap<String, String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String, String>> fileList = new ArrayList<>();
        Log.d("aranilan yer->",rootPath);
        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("songPath", file.getAbsolutePath());
                    song.put("songTitle", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public SarkiBulGetir(){

    }
    public String getMedia_path()
    {
        return MEDIA_PATH;
    }


    public  ArrayList<HashMap<String,String>> SarkiGetir()
    {
        songsList = getPlayList(getMedia_path());
        return songsList;
    }














}
