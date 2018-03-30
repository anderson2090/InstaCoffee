package com.sweetdeveloper.instacoffee;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.sweetdeveloper.instacoffee.database.DBAdapter;

public class CoffeeAppWidget extends AppWidgetProvider {

    DBAdapter dbAdapter;



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        dbAdapter = DBAdapter.getDbAdapterInstance(context);
        String coffeeMenuItems = dbAdapter.getAllNames().toString();
        update(context, appWidgetManager, appWidgetIds,
                coffeeMenuItems);

    }

    public void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                       String itemName) {
        for (int appWidgetId : appWidgetIds) {


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.coffee_app_widget);

            remoteViews.setTextViewText(R.id.widget_favourite_items, itemName);





            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

