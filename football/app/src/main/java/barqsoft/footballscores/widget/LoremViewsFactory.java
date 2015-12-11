/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

   
package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.model.Match;

/* CLASS BASED ON: https://github.com/commonsguy/cw-advandroid/tree/master/AppWidget/LoremWidget */
public class LoremViewsFactory implements RemoteViewsService.RemoteViewsFactory {
  private static ArrayList<Match> items = new ArrayList<>();
  private Context ctxt=null;
  private int appWidgetId;

  public LoremViewsFactory(Context ctxt, Intent intent) {
      this.ctxt=ctxt;
      appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                      AppWidgetManager.INVALID_APPWIDGET_ID);

      Cursor cursor = DatabaseContract.scores_table.buildScore(ctxt);
      String home, away, home_goals, away_goals, time;

      while (cursor.moveToNext()){
          home = cursor.getString(cursor.getColumnIndex("home"));
          away = cursor.getString(cursor.getColumnIndex("away"));
          home_goals = cursor.getString(cursor.getColumnIndex("home_goals"));
          away_goals = cursor.getString(cursor.getColumnIndex("away_goals"));
          time = cursor.getString(cursor.getColumnIndex("time"));

          items.add(new Match(home,away,home_goals,away_goals,time));
      }
      cursor.close();
  }
  
  @Override
  public void onCreate() {
    // no-op
  }
  
  @Override
  public void onDestroy() {
    // no-op
  }

  @Override
  public int getCount() {
    return(items.size());
  }

  @Override
  public RemoteViews getViewAt(int position) {
    RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                                 R.layout.widget_row);
    Match match = items.get(position);

    row.setTextViewText(R.id.home_name, match.getHomeName());
    row.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(match.getHomeName()));
    row.setTextViewText(R.id.away_name, match.getAwayName());
    row.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(match.getAwayName()));
    if (Integer.parseInt(match.getHomeScore()) >= 0 && Integer.parseInt(match.getAwayScore()) >= 0) {
      row.setTextViewText(R.id.score_textview, match.getHomeScore() + " - " + match.getAwayScore());
    }else {
      row.setTextViewText(R.id.score_textview, " - ");
    }
    row.setTextViewText(R.id.data_textview, match.getTime());

    Intent i=new Intent();
    Bundle extras=new Bundle();

    extras.putString("home_name", match.getHomeName());
    extras.putString("home_score", match.getHomeScore());
    extras.putString("away_name", match.getAwayName());
    extras.putString("away_score", match.getAwayScore());
    extras.putString("data", match.getTime());

    i.putExtras(extras);
    row.setOnClickFillInIntent(R.id.ll_container, i);

    return(row);
  }

  @Override
  public RemoteViews getLoadingView() {
    return(null);
  }
  
  @Override
  public int getViewTypeCount() {
    return(1);
  }

  @Override
  public long getItemId(int position) {
    return(position);
  }

  @Override
  public boolean hasStableIds() {
    return(true);
  }

  @Override
  public void onDataSetChanged() {
    // no-op
  }
}