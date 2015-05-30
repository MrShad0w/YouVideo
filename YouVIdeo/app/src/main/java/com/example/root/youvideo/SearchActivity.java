package com.example.root.youvideo;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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
public class SearchActivity extends ActionBarActivity {



    private EditText searchInput;
    private ListView videosFound;
   // String query;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);

            // get the action bar
          //  ActionBar actionBar = getActionBar();

            // Enabling Back navigation on Action Bar icon
           // actionBar.setDisplayHomeAsUpEnabled(true);


            //handleIntent(getIntent());
           searchInput = (EditText)findViewById(R.id.search_input);
            videosFound = (ListView) findViewById(R.id.videos_found);

           handler = new Handler();

            searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        searchOnYoutube(v.getText().toString());
                        return false;
                    }
                    return true;
                }
            });

            addClickListener();



    }
   /* @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
   /* private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            searchOnYoutube(query);

            // txtQuery.setText("Search Query: " + query);

        }

    } */

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

    private void addClickListener(){
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                String result = searchResults.get(pos).getId();
                intent.putExtra("video_id", result);
                startActivity(intent);
            }

        });
    }





}

