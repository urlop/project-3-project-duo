package barqsoft.footballscores;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import barqsoft.footballscores.widget.WidgetProvider;

public class DetailActivity extends ActionBarActivity {
    TextView home_name, away_name, score_textview, data_textview;
    String hName="", hScore="", aName="", aScore="", data="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        home_name = (TextView) findViewById(R.id.home_name);
        score_textview = (TextView) findViewById(R.id.score_textview);
        away_name = (TextView) findViewById(R.id.away_name);
        data_textview = (TextView) findViewById(R.id.data_textview);


        /*If called from WIDGET*/
        String hName=getIntent().getStringExtra("home_name");
        String hScore=getIntent().getStringExtra("home_score");
        String aName=getIntent().getStringExtra("away_name");
        String aScore=getIntent().getStringExtra("away_score");
        String data=getIntent().getStringExtra("data");

        home_name.setText(hName);
        score_textview.setText(hScore + " - " +aScore);
        away_name.setText(aName);
        data_textview.setText(data);
    }
}
