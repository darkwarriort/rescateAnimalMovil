package com.fundacionrescate.rescata.cnx;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
                        System.out.println("POST :" +response);
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
    public static void POST (final JSONArray jsonRequest, final String url, final CallBackConsulta callback ){
        final ProgressDialog mProgressDialog ;
        mProgressDialog = new ProgressDialog(callback.getContext());

        mProgressDialog.getWindow().setBackgroundDrawable(new

                ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.my_progress);

        System.out.println(jsonRequest.toString());
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                url,jsonRequest,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        System.out.println("POST :" +response);
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

    public static void POSTMultiPart (final Bitmap image, final String url, final CallBackConsulta callback ){
        final ProgressDialog mProgressDialog ;
        mProgressDialog = new ProgressDialog(callback.getContext());

        mProgressDialog.getWindow().setBackgroundDrawable(new

                ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.my_progress);



//        PhotoMultipartRequest imageUploadReq = new PhotoMultipartRequest(url, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//
//            }
//        }, image);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }
                        System.out.println("POST IMAGE :" +response);
                        callback.onSuccess(response);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//                params.put("name", mNameInput.getText().toString());
//                params.put("location", mLocationInput.getText().toString());
//                params.put("about", mAvatarInput.getText().toString());
//                params.put("contact", mContactInput.getText().toString());
//                return params;
//            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView


                params.put("file", new DataPart(System.currentTimeMillis()+".jpg", getFileDataFromDrawable(image), "image/jpeg"));


                //                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(multipartRequest, "GET");

    }

    public static byte[] getFileDataFromDrawable( Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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

        JsonObjectRequest strReq  = new JsonObjectRequest(Request.Method.GET, url,null,
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

        JsonArrayRequest strReq  = new JsonArrayRequest(Request.Method.GET, url,null,
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