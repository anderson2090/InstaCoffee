package com.sweetdeveloper.instacoffee.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class NewsIntentService extends IntentService {

    Document doc;
    StringBuilder newsStringBuilder = new StringBuilder();

    public NewsIntentService() {
        super("NewsIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            doc = Jsoup.connect("https://www.baristamagazine.com/events/").get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                newsStringBuilder.append(link.text()+"\n");
                newsStringBuilder.append(link.attr("href")+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent newsIntent = new Intent();
        newsIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        newsIntent.setAction("com.instacoffee.news");
        newsIntent.putExtra("news", newsStringBuilder.toString());
        sendBroadcast(newsIntent);
    }
}
