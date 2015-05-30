package com.example.root.youvideo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import android.os.Handler;


import com.squareup.picasso.Picasso;

/**
 * Created by root on 21.05.15..
 */
public class SearchActivity extends  MainActivity {



        private EditText searchInput;
        private ListView videosFound;

        Handler handler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);

            searchInput = (EditText)findViewById(R.id.search_input);
            videosFound = (ListView)findViewById(R.id.videos_found);

           handler = new Handler();

            searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        searchOnYoutube(v.getText().toString());
                        return false;
                    }
                    return true;
                }
            });

        }

    private List<VideoMoja> searchResults;

    private void searchOnYoutube(final String rijec){
        new Thread(){
            public void run(){
                YoutubePomoc yc = new YoutubePomoc(SearchActivity.this);
                searchResults = yc.search(rijec);
                handler.post(new Runnable(){
                    public void run(){
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoMoja> adapter = new ArrayAdapter<VideoMoja>(getApplicationContext(), R.layout.video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                VideoMoja searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getURL()).into(thumbnail);
                title.setText(searchResult.getNaslov());
                description.setText(searchResult.getOpis());
                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }
}

