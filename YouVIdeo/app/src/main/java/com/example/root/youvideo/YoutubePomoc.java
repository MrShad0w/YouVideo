package com.example.root.youvideo;

import android.content.Context;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 21.05.15..
 */
public class YoutubePomoc {


    YouTube.Search.List trazi;

    public static final String KEY = "AIzaSyDFVmx4eqviTbaM13VteE4mSq1I5jULT94";


        public  YoutubePomoc(Context context) {


            try  {
                YouTube  youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {} }).setApplicationName("YouVideo").build();




                trazi = youtube.search().list("id,snippet");
                trazi.setKey(KEY);
                trazi.setType("video");
                trazi.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            } catch (IOException e) {
                //TODO catch
            }
        }


    public List<VideoMoja> search(String rijec) {
        trazi.setQ(rijec);

        try {

            SearchListResponse odgovor = trazi.execute();
            List<SearchResult> rezultati = odgovor.getItems();

            List<VideoMoja> artikli = new ArrayList<>();

            for (SearchResult rezultat : rezultati) {

                VideoMoja artikl = new VideoMoja();

                artikl.setNaslov(rezultat.getSnippet().getTitle());
                artikl.setOpis(rezultat.getSnippet().getDescription());
                artikl.setURL(rezultat.getSnippet().getThumbnails().getDefault().getUrl());
                artikl.setId(rezultat.getId().getVideoId());
                artikli.add(artikl);

            }
            return  artikli;
        } catch (IOException e) {
            //TODO catch
            return null;
        }
    }
}
