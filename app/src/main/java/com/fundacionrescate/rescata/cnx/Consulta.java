package com.fundacionrescate.rescata.cnx;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import  com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppController;
import com.fundacionrescate.rescata.util.Traces;

/**
 * Created by DANIEL TUMBACO on 6/9/2017.
 */

public class Consulta {

    public interface CallBackConsulta{
        public void onError(Object response);
        public void onSuccess(Object response);
        public Context getContext();

    }

   public static void POST (final JSONObject jsonRequest, final String url, final CallBackConsulta callback ){
       final ProgressDialog mProgressDialog ;
       mProgressDialog = new ProgressDialog(callback.getContext());

       mProgressDialog.getWindow().setBackgroundDrawable(new

               ColorDrawable(android.graphics.Color.TRANSPARENT));
       mProgressDialog.setIndeterminate(true);
       mProgressDialog.setCancelable(false);
       mProgressDialog.show();
       mProgressDialog.setContentView(R.layout.my_progress);

       System.out.println(jsonRequest.toString());
       JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
               url,jsonRequest,
               new Response.Listener() {
                   @Override
                   public void onResponse(Object response) {
                       if(mProgressDialog!=null){
                           mProgressDialog.dismiss();
                       }
                       System.out.println("GET :" +response);
                       callback.onSuccess(response);

                   }

               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       //Failure Callback
                       if(mProgressDialog!=null){
                           mProgressDialog.dismiss();
                       }
                       callback.onError(null);

                   }
               });

       // Adding request to request queue
       AppController.getInstance().addToRequestQueue(req, "POST");

   }

      public static void GET (final String url, final CallBackConsulta callback ){
        final ProgressDialog mProgressDialog ;
        mProgressDialog = new ProgressDialog(callback.getContext());

        mProgressDialog.getWindow().setBackgroundDrawable(new

                ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.my_progress);

        JsonObjectRequest strReq  = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        System.out.println("GET :" +response);
                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }

                        callback.onError(null);
                    }
                }
        );
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "GET");

    }

    public static void GETARRAY(final String url, final CallBackConsulta callback ){
        final ProgressDialog mProgressDialog ;
        mProgressDialog = new ProgressDialog(callback.getContext());

        mProgressDialog.getWindow().setBackgroundDrawable(new

                ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.my_progress);

        JsonArrayRequest strReq  = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        System.out.println("GET :" +response);
                        callback.onSuccess(response);
                    }


                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }

                        callback.onError(null);
                    }
                }
        );
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "GET");

    }

}