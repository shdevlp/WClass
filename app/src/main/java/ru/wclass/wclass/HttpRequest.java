package ru.wclass.wclass;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dave on 01.06.16.
 */
public class HttpRequest extends Request<JSONArray> {
    private Response.Listener<JSONArray> listener;
    private Map<String, String> params;

    public HttpRequest(int method, String url, Map<String, String> params,
                          Response.Listener<JSONArray> reponseListener,
                          Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray == null) {
            jsonArray = new JSONArray();
            try {
                JSONObject json = null;
                json = new JSONObject(jsonString);
                jsonArray.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Response.success(jsonArray,
                    HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }
}