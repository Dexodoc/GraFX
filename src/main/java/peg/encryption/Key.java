package peg.encryption;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Key {
    public static int[][] transformKey(String key){
        if (key.length() % 2 != 0) key = key + "0";
        List<String> splitValues = new ArrayList<>();
        for (int i = 0; i < key.length(); i+=2) {
            splitValues.add(key.substring(i, i + 2));
        }
        int[][] keys = new int[splitValues.size()][2];
        for (int i = 0; i < splitValues.size(); i++) {
            String s = splitValues.get(i);
            keys[i][0] = Integer.valueOf(String.valueOf(s.charAt(0)), 16);
            keys[i][1] = Integer.valueOf(String.valueOf(s.charAt(1)), 16);
        }
        return keys;
    }

    public static boolean keyValidity(String key){
        if (key.toLowerCase().matches("^[a-f0-9]+$") && key.length() % 2 == 0) return true;
        else return false;
    }

    public static String generateKey(int bits) throws IOException {
        String urlForUse = String.format("https://www.fourmilab.ch/cgi-bin/Hotbits.api?nbytes=%d&fmt=hex&npass=1&lpass=8&pwtype=3&apikey=&pseudo=pseudo", bits);
        URL url = new URL(urlForUse);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        Document doc = Jsoup.parse(content.toString());
        Elements val = doc.select("pre");
        return val.text();
    }
}
