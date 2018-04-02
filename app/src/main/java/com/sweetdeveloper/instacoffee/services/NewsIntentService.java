package com.sweetdeveloper.instacoffee.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sweetdeveloper.instacoffee.models.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class NewsIntentService extends IntentService {

    Document doc;
    StringBuilder newsStringBuilder = new StringBuilder();
    ArrayList<News> newsList = new ArrayList<>();

    public NewsIntentService() {
        super("NewsIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            doc = Jsoup.connect("https://www.baristamagazine.com/events/").get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {

//                newsStringBuilder.append(link.text()+"\n");
//                newsStringBuilder.append(link.attr("href")+"\n");
                String newsHeading = link.text();
                String newsLink = link.attr("href");
                News news = new News(newsHeading, newsLink);
                newsList.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent newsIntent = new Intent();
        newsIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        newsIntent.setAction("com.instacoffee.news");
        // newsIntent.putExtra("news", newsStringBuilder.toString());
        newsIntent.putParcelableArrayListExtra("newsList", newsList);
        sendBroadcast(newsIntent);
    }
}
