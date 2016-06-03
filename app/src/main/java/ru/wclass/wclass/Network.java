package ru.wclass.wclass;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.ParseException;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by dave on 01.06.16.
 */
public class Network {
    private static String TAG = "AskNetwork:";

    private static final String urlGyms = "https://personal.wclass.ru/services/api/gyms";
    private static final String urlShedule = "http://personal.wclass.ru/co/schedule.php";
    private static final String urlAuth = "https://personal.wclass.ru/services/api/customer/auth";
    private static final String urlCustomer = " https://personal.wclass.ru/services/api/customer";

    public static final String P_ID = "id";
    public static final String P_NAME = "name";
    public static final String P_DIV = "division";
    public static final String P_ADDR = "gymAddress";

    public static final String P_LOGIN = "login";
    public static final String P_PASSWORD = "password";
    public static final String P_REMEMBER = "remember";
    public static final String P_RESULT = "result";
    public static final String P_OK = "Ok";
    public static final String P_CID = "cid";
    public static final String P_CUSTOMER = "customer";
    public static final String P_CUSTOMER_TYPE = "customerType";
    public static final String P_CUSTOMER_STATUS = "customerStatus";
    public static final String P_FIRST_NAME = "firstName";
    public static final String P_LAST_NAME = "lastName";
    public static final String P_SECOND_NAME = "secondName";
    public static final String P_BIRTHDAY_DATE = "birthdayDate";
    public static final String P_PHONE_NUMBER = "phoneNumber";
    public static final String P_EMAIL = "email";
    public static final String P_SUBSCRIPTION_EMAIL = "subscriptionEmail";
    public static final String P_SUBSCRIPTION_SMS = "subscriptionSms";
    public static final String P_GYM_ID = "gymId";
    public static final String P_PASSWORD_EXPIRATION_DATE = "passwordExpirationDate";
    public static final String P_HOME_ADDRESS = "homeAddress";
    public static final String P_CAN_RECOMMEND = "canRecommend";
    public static final String P_MANAGER = "manager";
    public static final String P_MANAGER_NAME = "name";
    public static final String P_MANAGER_PHONE_NUMBER = "phoneNumber";
    public static final String P_MANAGER_EMAIL = "email";
    public static final String P_AUTH_TOKEN = "authToken";
    public static final String P_EXERCISE_GROUPS = "exerciseGroups";
    public static final String P_SHEDULE = "schedule";
    public static final String P_GROUP_ID = "groupId";
    public static final String P_TIME = "time";
    public static final String P_DESCRIPTION = "description";
    public static final String P_PLACE = "place";
    public static final String P_TRAINER = "trainer";
    public static final String P_TRAINER_ID = "trainerId";
    public static final String P_STUDENT_LEVEL = "studentLevel";
    public static final String P_DURATION = "duration";
    public static final String P_PRE_BOOKED = "isPreBooked";
    public static final String P_PRE_PAID = "isPrePaid";
    public static final String P_AGE_GROUPS = "ageGroups";
    public static final String P_AGE_GROUP = "ageGroup";


    public static final String P_START_DATE = "startdate";
    public static final String P_END_DATE = "enddate";

    public static final String testUser = "000755685";
    public static final String testPassword = "#teddyBear";

    private static Network instance;
    private final Activity activity;

    static {
        instance = null;
    }

    public interface ListCallback {
        void onSuccess(List<Object> list);
        void onError(String error);
    }

    public interface MapCallback {
        void onSuccess(Map<String, String> map);
        void onError(String error);
    }

    public interface JSONCallback {
        void onSuccess(JSONArray response);
        void onError(String error);
    }

    /**
     * создание экземляра только через getInstance
     */
    private Network(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return Единственный экземпляр класса
     */
    public static Network getInstance(Activity activity) {
        if (instance == null) {
            synchronized (Network.class) {
                if (instance == null) {
                    instance = new Network(activity);
                }
            }
        }
        return instance;
    }

    /**
     * Посылает пост запрос на получение списка залов
     * @param callback
     */
    public void postGyms(final ListCallback callback) {
        HttpsTrustManager.allowAllSSL();
        HttpRequest jsObjRequest = new HttpRequest(Request.Method.POST,
                urlGyms, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response != null) {
                        List list = new ArrayList();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = response.getJSONObject(i);
                            GymClass gc = new GymClass();

                            if (json.has(P_ID)) {
                                gc.setId(json.get(P_ID).toString());
                            }
                            if (json.has(P_NAME)) {
                                gc.setName(json.get(P_NAME).toString());
                            }
                            if (json.has(P_DIV)) {
                                gc.setDivision(json.get(P_DIV).toString());
                            }
                            if (json.has(P_ADDR)) {
                                gc.setGymAddress(json.get(P_ADDR).toString());
                            }

                            list.add(gc);
                        }

                        if (list.size() == 0) {
                            callback.onError(response.toString());
                        } else {
                            callback.onSuccess(list);
                        }
                    }
                }catch(JSONException ex){
                    ex.printStackTrace();
                    callback.onError(ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError(getErrorResponseMessage(error));
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        mRequestQueue.add(jsObjRequest);
    }

    /**
     * Получение данных по аутентификации
     * @param login
     * @param password
     * @param remember
     * @param callback
     */
    public void postAuth(String login, String password, boolean remember, final MapCallback callback) {
        HttpsTrustManager.allowAllSSL();

        Map<String, String> params = new HashMap<>();
        params.put(P_LOGIN, login);
        params.put(P_PASSWORD, password);
        params.put(P_REMEMBER, String.valueOf(remember));

        HttpRequest jsObjRequest = new HttpRequest(Request.Method.POST,
                urlAuth, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response != null) {
                        Map<String, String> map = new HashMap<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = response.getJSONObject(i);

                            if (json.has(P_RESULT)) {
                                map.put(P_RESULT, json.get(P_RESULT).toString());
                            }

                            if (json.has(P_CUSTOMER)) {
                                JSONObject json2 = json.getJSONObject(P_CUSTOMER);

                                if (json2.has(P_CID)) {
                                    map.put(P_CID, json2.get(P_CID).toString());
                                }

                                if (json2.has(P_CUSTOMER_TYPE)) {
                                    map.put(P_CUSTOMER_TYPE, json2.get(P_CUSTOMER_TYPE).toString());
                                }

                                if (json2.has(P_CUSTOMER_STATUS)) {
                                    map.put(P_CUSTOMER_STATUS, json2.get(P_CUSTOMER_STATUS).toString());
                                }

                                if (json2.has(P_LOGIN)) {
                                    map.put(P_LOGIN, json2.get(P_LOGIN).toString());
                                }

                                if (json2.has(P_FIRST_NAME)) {
                                    map.put(P_FIRST_NAME, json2.get(P_FIRST_NAME).toString());
                                }

                                if (json2.has(P_LAST_NAME)) {
                                    map.put(P_LAST_NAME, json2.get(P_LAST_NAME).toString());
                                }

                                if (json2.has(P_SECOND_NAME)) {
                                    map.put(P_SECOND_NAME, json2.get(P_SECOND_NAME).toString());
                                }

                                if (json2.has(P_BIRTHDAY_DATE)) {
                                    map.put(P_BIRTHDAY_DATE, json2.get(P_BIRTHDAY_DATE).toString());
                                }

                                if (json2.has(P_PHONE_NUMBER)) {
                                    map.put(P_PHONE_NUMBER, json2.get(P_PHONE_NUMBER).toString());
                                }

                                if (json2.has(P_EMAIL)) {
                                    map.put(P_EMAIL, json2.get(P_EMAIL).toString());
                                }

                                if (json2.has(P_SUBSCRIPTION_EMAIL)) {
                                    map.put(P_SUBSCRIPTION_EMAIL, json2.get(P_SUBSCRIPTION_EMAIL).toString());
                                }

                                if (json2.has(P_SUBSCRIPTION_SMS)) {
                                    map.put(P_SUBSCRIPTION_SMS, json2.get(P_SUBSCRIPTION_SMS).toString());
                                }

                                if (json2.has(P_GYM_ID)) {
                                    map.put(P_GYM_ID, json2.get(P_GYM_ID).toString());
                                }

                                if (json2.has(P_PASSWORD_EXPIRATION_DATE)) {
                                    map.put(P_PASSWORD_EXPIRATION_DATE, json2.get(P_PASSWORD_EXPIRATION_DATE).toString());
                                }

                                if (json2.has(P_HOME_ADDRESS)) {
                                    map.put(P_HOME_ADDRESS, json2.get(P_HOME_ADDRESS).toString());
                                }

                                if (json2.has(P_CAN_RECOMMEND)) {
                                    map.put(P_CAN_RECOMMEND, json2.get(P_CAN_RECOMMEND).toString());
                                }

                                if (json2.has(P_AUTH_TOKEN)) {
                                    map.put(P_AUTH_TOKEN, json2.get(P_AUTH_TOKEN).toString());
                                }

                                if (json2.has(P_MANAGER)) {
                                    JSONObject json3 = json2.getJSONObject(P_MANAGER);

                                    if (json3.has(P_MANAGER_NAME)) {
                                        map.put(P_MANAGER_NAME, json3.get(P_MANAGER_NAME).toString());
                                    }

                                    if (json3.has(P_MANAGER_PHONE_NUMBER)) {
                                        map.put(P_MANAGER_PHONE_NUMBER, json3.get(P_MANAGER_PHONE_NUMBER).toString());
                                    }

                                    if (json3.has(P_MANAGER_EMAIL)) {
                                        map.put(P_MANAGER_EMAIL, json3.get(P_MANAGER_EMAIL).toString());
                                    }
                                }
                            }
                        }

                        if (map.size() == 0) {
                            callback.onError(response.toString());
                        } else {
                            if (map.get(P_RESULT).toString().compareTo(P_OK) == 0) {
                                callback.onSuccess(map);
                            } else {
                                callback.onError(map.get(P_RESULT).toString());
                            }
                        }
                    }
                } catch(JSONException ex){
                    ex.printStackTrace();
                    callback.onError(ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError(getErrorResponseMessage(error));
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        mRequestQueue.add(jsObjRequest);
    }


    private String getStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private String getEndDate(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);
        return sdf.format(c.getTime());
    }

    /**
     * Запрос расписания по клубу
     * @param gymId
     * @param callback
     */
    public void getShedule(String gymId, final JSONCallback callback) {
        final String url = urlShedule + "?" + P_GYM_ID + "=" + gymId + "&" +
                P_START_DATE + "=" + getStartDate() + "&" + P_END_DATE + "=" + getEndDate(10);

        HttpsTrustManager.allowAllSSL();
        HttpRequest jsObjRequest = new HttpRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response){
                    if (response != null) {
                        callback.onSuccess(response);
                    } else {
                        callback.onError(response.toString());
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError(getErrorResponseMessage(error));
            }
        });


        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        mRequestQueue.add(jsObjRequest);
    }

    /**
     * Получить описание ошибки
     * @return Описание
     */
    public String getErrorResponseMessage(VolleyError error) {
        String json = null;
        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            json = new String(response.data);
        }

        return json;
    }
}
