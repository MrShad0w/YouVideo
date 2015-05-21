package com.example.root.youvideo;

import android.content.Context;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;


/**
 * Created by root on 21.05.15..
 */
public class YoutubePomoc {
    private YouTube youtube;
    private YouTube.Search.List lista;

    public static final String KEY
            = "AIzaSyCECaLvrqDV7iZfg1KRxsn_Tj3DnOtBmfs";

    public YoutubePomoc (Context context) {

        youtube = YouTube.Builder()




        }


    }
}
