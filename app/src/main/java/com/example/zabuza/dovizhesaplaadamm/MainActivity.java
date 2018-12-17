package com.example.zabuza.dovizhesaplaadamm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView jpyText;
    TextView chfText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        chfText = findViewById(R.id.chfText);

    }
//İşlem kısmı
    public void getRates(View view){

        DownLoadData downLoadData = new DownLoadData();

        try{

            String url = "http://data.fixer.io/api/latest?access_key=28a5866d5d70f1072471bcad25e4fb5b&format=1";
            downLoadData.execute(url);

        }catch (Exception e){

        }

    }
//Verileri arka planda indirir.
    private class DownLoadData extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            String result ="";
            URL url;
            HttpURLConnection httpURLConnection;
            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data > 0){

                    char character = (char)data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("alınan veri" + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base: "+base);

                String rates = jsonObject.getString("rates");
                //System.out.println("rates: "+ rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String cad = jsonObject1.getString("CAD");
                cadText.setText("Kanada Doları-CAD: " + cad);


                String chf = jsonObject1.getString("CHF");
                chfText.setText("İsviçre Frangı-CHF: " + chf);

                String usd = jsonObject1.getString("USD");
                usdText.setText("Amerikan Doları-USD: " + usd);


                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("Japon Yeni-JPY: " + jpy);


                String tl = jsonObject1.getString("TRY");
                tryText.setText("Türk Lirası-TRY: " + tl);
            }catch (Exception e){

            }
        }
    }

}
