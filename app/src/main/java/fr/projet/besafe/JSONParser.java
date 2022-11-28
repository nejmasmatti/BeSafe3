package fr.projet.besafe;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import fr.projet.besafe.model.AlertExcel.AlertExcel;

public class JSONParser {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public <A> JSONObject makeHttpRequest(String url, String method, HashMap<String, A> params)
    {
        sbParams = new StringBuilder();

        int i = 0;
        if(params != null){
            System.out.println("size : " + params.size());
                for (Object key : params.keySet())
                {
                    try
                    {
                        if (i != 0)
                        {
                            sbParams.append("&");
                        }

                        if(params.get(key) instanceof ArrayList){
                            StringBuilder alertE = new StringBuilder();
                            A liste = params.get(key);
                            for (AlertExcel a :(ArrayList<AlertExcel>) liste){
                                alertE.append(a.getLibelleAlerte()).append(",").append(a.getNumDepartement()).append(",")
                                        .append(a.getNbCrime()).append(",").append(a.getMois()).append(",")
                                        .append(a.getAnnee()).append(";");
                            }
                            sbParams.append(key).append("=").append(alertE.toString());
                        }

                        sbParams.append(key).append("=").append(URLEncoder.encode((String) params.get(key).toString(), charset));
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    i++;
                }
                i = 0;

        }


        if (method.equals("POST"))
        {
            try
            {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                paramsString = sbParams.toString();
                System.out.println("aaaaaaaaaaaaaa " + paramsString);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET"))
        {
            // request method is GET
            if (sbParams.length() != 0)
            {
                System.out.println("bbbbbbbbbbbbbbb " + sbParams);
                url += "?" + sbParams.toString();
            }
            try
            {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }
            Log.d("JSON Parser", "result: " + result.toString());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        conn.disconnect();

        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(result.toString());
        }
        catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON Object
        System.out.println(jObj);
        return jObj;
    }
}
