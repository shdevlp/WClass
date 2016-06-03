package ru.wclass.wclass.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.wclass.wclass.GymClass;
import ru.wclass.wclass.MainActivity;
import ru.wclass.wclass.Network;
import ru.wclass.wclass.R;
import ru.wclass.wclass.SheduleClass;
import ru.wclass.wclass.adapters.SheduleAdapter;

public class SheduleFragment extends Fragment {
    public static final String TAG = "SheduleFragment";

    private Handler handler;
    private Network network;
    private GymClass gymClass;
    private ListView lvShedule;

    public SheduleFragment() {
        this.handler = null;
        this.gymClass = null;
    }

    public void setGymClass(GymClass gc) {
        this.gymClass = gc;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Парсинг групп
     * @param response
     * @return
     */
    private Map<String, String> parseGroups(JSONArray response) {
        Map<String, String> mapGroups = new HashMap<>();
        if (response != null) {

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    if (obj.has(Network.P_EXERCISE_GROUPS)) {
                        JSONArray obj2 = obj.getJSONArray(Network.P_EXERCISE_GROUPS);

                        for (int j = 0; j < obj2.length(); j++) {
                            JSONObject obj3 = obj2.getJSONObject(j);

                            if (obj3.has(Network.P_ID) && obj3.has(Network.P_NAME)) {
                                mapGroups.put(obj3.getString(Network.P_ID).toString(),
                                        obj3.get(Network.P_NAME).toString());
                            }
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return mapGroups;
    }

    private List<Object> parseShedule(JSONArray response) {
        Map<String, String> mapGroups = parseGroups(response);

        List<Object> list = new ArrayList<>();

        if (response != null)
            //Формирование расписания
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    if (obj.has(Network.P_SHEDULE)) {
                        JSONArray obj2 = obj.getJSONArray(Network.P_SHEDULE);

                        for (int j = 0; j < obj2.length(); j++) {
                            JSONObject obj3 = obj2.getJSONObject(j);

                            SheduleClass sc = new SheduleClass();

                            if (obj3.has(Network.P_ID)) {
                                sc.setId(obj3.get(Network.P_ID).toString());
                            }

                            if (obj3.has(Network.P_GROUP_ID)) {
                                String groupId = obj3.get(Network.P_GROUP_ID).toString();
                                if (mapGroups.containsKey(groupId)) {
                                    sc.setGroup(mapGroups.get(groupId).toString());
                                } else {
                                    sc.setGroup(groupId);
                                }
                            }

                            if (obj3.has(Network.P_TIME)) {
                                sc.setTime(obj3.get(Network.P_TIME).toString());
                            }

                            if (obj3.has(Network.P_NAME)) {
                                sc.setName(obj3.get(Network.P_NAME).toString());
                            }

                            if (obj3.has(Network.P_DESCRIPTION)) {
                                sc.setDescription(obj3.get(Network.P_DESCRIPTION).toString());
                            }

                            if (obj3.has(Network.P_PLACE)) {
                                sc.setPlace(obj3.get(Network.P_PLACE).toString());
                            }

                            if (obj3.has(Network.P_TRAINER)) {
                                sc.setTrainer(obj3.get(Network.P_TRAINER).toString());
                            }

                            if (obj3.has(Network.P_TRAINER_ID)) {
                                sc.setTrainerId(obj3.get(Network.P_TRAINER_ID).toString());
                            }

                            if (obj3.has(Network.P_STUDENT_LEVEL)) {
                                sc.setStudentLevel(obj3.get(Network.P_STUDENT_LEVEL).toString());
                            }

                            if (obj3.has(Network.P_DURATION)) {
                                sc.setDuration(obj3.get(Network.P_DURATION).toString());
                            }

                            if (obj3.has(Network.P_PRE_BOOKED)) {
                                sc.setPreBooked(Boolean.parseBoolean(obj3.get(Network.P_PRE_BOOKED).toString()));
                            }

                            if (obj3.has(Network.P_PRE_PAID)) {
                                sc.setPrePaid(Boolean.parseBoolean(obj3.get(Network.P_PRE_PAID).toString()));
                            }

                            if (obj3.has(Network.P_AGE_GROUPS)) {
                                JSONArray obj4 = obj3.getJSONArray(Network.P_AGE_GROUPS);
                                for (int k = 0; k < obj4.length(); k++) {
                                    JSONObject obj5 = obj4.getJSONObject(k);
                                    if (obj5.has(Network.P_AGE_GROUP)) {
                                        sc.setDuration(obj5.get(Network.P_AGE_GROUP).toString());
                                    }
                                }
                            }

                            list.add(sc);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shedule, container, false);
        lvShedule = (ListView) v.findViewById(R.id.lvShedule);

        network = Network.getInstance(getActivity());
        if (gymClass != null) {
            final Context context = getActivity().getApplicationContext();
            network.getShedule(gymClass.getId(), new Network.JSONCallback() {
                        @Override
                        public void onSuccess(final JSONArray response) {
                            if (handler != null) {
                                handler.obtainMessage(MainActivity.MESSAGE_DIALOG_DISMISS).sendToTarget();
                            }

                            Thread th = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final List<Object> list = parseShedule(response);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            lvShedule.setAdapter(new SheduleAdapter(context, list));
                                        }
                                    });
                                }
                            });
                            th.start();
                        }

                        @Override
                        public void onError(String error) {
                            if (handler != null) {
                                handler.obtainMessage(MainActivity.MESSAGE_DIALOG_DISMISS).sendToTarget();
                            }
                            MainActivity.alertDialog(getString(R.string.error), error, 0, null, 0, getString(R.string.ok),
                                    new MainActivity.AlertCallback() {
                                        @Override
                                        public void callbackFunc() {
                                            if (handler != null) {
                                                handler.obtainMessage(MainActivity.MESSAGE_GOTO_GYMS).sendToTarget();
                                            }
                                        }
                                    }, null, null, null, null);
                        }
                    }
            );
        }

        return v;
    }

}
