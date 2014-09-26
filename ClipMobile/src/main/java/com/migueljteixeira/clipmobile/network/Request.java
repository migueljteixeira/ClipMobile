package com.migueljteixeira.clipmobile.network;

import android.content.Context;

import com.migueljteixeira.clipmobile.settings.ClipSettings;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class Request {

    protected static final String COOKIE_NAME = "JServSessionIdroot1112";
    private static final String INITIAL_REQUEST = "https://clip.unl.pt/utente/eu";

    protected static Document initialRequest(Context context, String username, String password) {

        try {
            Connection.Response response = Jsoup.connect(INITIAL_REQUEST)
                    .timeout(40000)
                    .data("identificador", username)
                    .data("senha", password)
                    .method(Connection.Method.POST)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .header("Accept-Language", "en-US,en;q=0.8,pt-PT;q=0.6,pt;q=0.4")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    //.header("Content-Length", "39")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Host", "clip.unl.pt")
                    .header("Origin", "https://clip.unl.pt")
                    .header("Referer", "https://clip.unl.pt/utente/eu")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36")
                    .execute();

            // save cookie
            ClipSettings.saveCookie(context, response.cookie(COOKIE_NAME));

            return response.parse();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    protected static Document request(Context context, String url) throws IOException {
        String cookie = ClipSettings.getCookie(context);

        Document response = Jsoup.connect(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,pt-PT;q=0.6,pt;q=0.4")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .header("Cookie", COOKIE_NAME + "=" + cookie)
                .header("Host", "clip.unl.pt")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36")
                .timeout(40000)
                .get();

        return response;
    }

}
