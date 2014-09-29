package com.migueljteixeira.clipmobile.network;

import android.content.Context;

import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class Request {

    protected static final String COOKIE_NAME = "JServSessionIdroot1112";
    private static final String INITIAL_REQUEST = "https://clip.unl.pt/utente/eu";

    protected static Document initialRequest(Context context, String username, String password)
            throws ServerUnavailableException {

        try {
            Connection.Response response = Jsoup.connect(INITIAL_REQUEST)
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
                    .timeout(30000)
                    .execute();

            // Save cookie
            ClipSettings.saveCookie(context, response.cookie(COOKIE_NAME));

            // Save login time
            ClipSettings.saveLoginTime(context);

            return response.parse();

        } catch (IOException e) {
            throw new ServerUnavailableException();
        }

    }

    protected static Document request(Context context, String url) throws ServerUnavailableException {
        String cookie = ClipSettings.getCookie(context);

        try {
            Connection connection = Jsoup.connect(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .header("Accept-Language", "en-US,en;q=0.8,pt-PT;q=0.6,pt;q=0.4")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .header("Host", "clip.unl.pt")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36")
                    .timeout(30000);


            // If the cookie has expired, we need to request a new one
            Connection.Response response;
            if( ClipSettings.isTimeForANewCookie(context) ) {
                System.out.println("Requesting new cookie!");
                connection.data("identificador", ClipSettings.getLoggedInUserName(context));
                connection.data("senha", ClipSettings.getLoggedInUserPw(context));

                // Execute the request
                response = connection.execute();

                // Save cookie
                ClipSettings.saveCookie(context, response.cookie(COOKIE_NAME));

                // Save login time
                ClipSettings.saveLoginTime(context);
            }
            else {
                connection.header("Cookie", COOKIE_NAME + "=" + cookie);

                // Execute the request
                response = connection.execute();
            }

            System.out.println("response : " + response.parse().body());
            return response.parse();

        } catch (IOException e) {
            throw new ServerUnavailableException();
        }

    }

}
