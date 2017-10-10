package com.example.shaheed.andelachallengeproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    TextView cardTitle;
    String etheurString;
    String ethusdString;
    String btceurString;
    String btcusdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new async_method().execute();

    }


    class async_method extends AsyncTask<Void, Void, String> {

        String jsonResponse;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Connecting!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //TODO: Networking

            HTTPHandler httpHandler = new HTTPHandler();

            // Making a request to url and getting response
            jsonResponse = null;
            String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";
            jsonResponse = httpHandler.makeServiceCall(url);

            if (jsonResponse != null) {
                try {
                    //JSONObject object = new JSONObject(jsonStr);

                    //getting the items node out of the whole response
                    //JSONArray jsonArray = object.getJSONArray("items");
                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject btcobject = jsonObject.getJSONObject("BTC");
                        JSONObject ethobject = jsonObject.getJSONObject("ETH");

                        //assigning value to variables in responseModel class
                        ResponseModel btcreport = new ResponseModel();
                        btcreport.setEUR((float) btcobject.getDouble("EUR"));
                        btcreport.setUSD((float) btcobject.getDouble("USD"));

                        ResponseModel ethreport = new ResponseModel();
                        ethreport.setEUR((float) ethobject.getDouble("EUR"));
                        ethreport.setUSD((float) ethobject.getDouble("USD"));

                        etheurString = String.valueOf(ethreport.getEUR());
                        ethusdString = String.valueOf(ethreport.getUSD());
                        btceurString = String.valueOf(btcreport.getEUR());
                        btcusdString = String.valueOf(btcreport.getUSD());
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return null;
            } else {
                Toast.makeText(MainActivity.this,
                        "No response!", Toast.LENGTH_SHORT).show();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            cardTitle = (TextView) findViewById(R.id.cardTitle);
            cardTitle.setText(etheurString + " " + ethusdString + " " + btceurString + " " + btcusdString);
            cardTitle.setTextSize(25);
        }
    }

}
