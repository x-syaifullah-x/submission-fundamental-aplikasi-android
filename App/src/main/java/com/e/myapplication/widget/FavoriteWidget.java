package com.e.myapplication.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.e.myapplication.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getBroadcast;
import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.getInstance;
import static android.content.Intent.URI_INTENT_SCHEME;
import static android.net.Uri.parse;
import static android.widget.Toast.LENGTH_SHORT;

public class FavoriteWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.e.myapplication.EXTRA_ITEM";
    private static final String ACTION = "com.e.myapplication.ACTION";

    public static void updateWidget(Context context) {
        int[] appWidgetIds = getInstance(context).getAppWidgetIds(new ComponentName(context.getPackageName(), FavoriteWidget.class.getName()));
        getInstance(context).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.imageView);
        getInstance(context).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
        getInstance(context).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.empty_view);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, StackWidgetService.class);
            intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(parse(intent.toUri(URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setRemoteAdapter(R.id.stack_view, intent);
            views.setEmptyView(R.id.stack_view, R.id.empty_view);

            Intent action = new Intent(context, FavoriteWidget.class);
            action.setAction(ACTION);
            action.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
            action.setData(parse(intent.toUri(URI_INTENT_SCHEME)));

            PendingIntent pendingInten = getBroadcast(context, 0, action, FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.stack_view, pendingInten);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION))
                Toast.makeText(context, intent.getStringExtra(EXTRA_ITEM), LENGTH_SHORT).show();
            if (intent.getAction().equals(ACTION_APPWIDGET_UPDATE))
                updateWidget(context);
        }
    }
}