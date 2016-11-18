package com.poster.danbilap.project_yeobo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ShortenUrlGoogle {

    public static final String SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=";

    public static final String API_KEY = "AIzaSyCxW4DTyYELsWzEvPlpkxelPSrweeHO1TU"; // 새로운 키 등록 필요


    public static String getShortenUrl(String originalUrl) {

        System.out.println("[DEBUG] INPUT_URL : " + originalUrl );


        String resultUrl = originalUrl;

        String originalUrlJsonStr = "{\"longUrl\":\"" + originalUrl + "\"}";
        System.out.println("[DEBUG] INPUT_JSON : " + originalUrlJsonStr);

        URL                 url         = null;
        HttpURLConnection   connection  = null;
        OutputStreamWriter  osw         = null;
        BufferedReader      br          = null;
        StringBuffer        sb          = null;
        JSONObject          jsonObj     = null;

        try {
            url = new URL(SHORTENER_URL + API_KEY);
            System.out.println("[DEBUG] DESTINATION_URL : " + url.toString() );

        }catch(Exception e){
            System.out.println("[ERROR] URL set Failed");
            e.printStackTrace();
            return resultUrl;
        }

        // 성냥   : 지정된 URL로 연결 설정
        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "toolbar");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
        }catch(Exception e){
            System.out.println("[ERROR] Connection open Failed");
            e.printStackTrace();
            return resultUrl;
        }

        try{
            osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(originalUrlJsonStr);
            osw.flush();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            sb = new StringBuffer();
            String buf = "";
            while ((buf = br.readLine()) != null) {
                sb.append(buf);
            }
            System.out.println("[DEBUG] RESULT_JSON_DATA : " + sb.toString());

            jsonObj = new JSONObject(sb.toString());

            resultUrl = jsonObj.getString("id");

        }catch (Exception e) {
            System.out.println("[ERROR] Result JSON Data(From Google) set JSONObject Failed");
            e.printStackTrace();
            return resultUrl;
        }finally{
            if (osw != null)    try{ osw.close();   } catch(Exception e) { e.printStackTrace(); }
            if (br  != null)    try{ br.close();    } catch(Exception e) { e.printStackTrace(); }
        }

        System.out.println("[DEBUG] RESULT_URL : " + resultUrl);
        return resultUrl;
    }

    }
