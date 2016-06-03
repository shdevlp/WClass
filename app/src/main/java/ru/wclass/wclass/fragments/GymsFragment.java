package ru.wclass.wclass.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ru.wclass.wclass.GymClass;
import ru.wclass.wclass.MainActivity;
import ru.wclass.wclass.Network;
import ru.wclass.wclass.R;
import ru.wclass.wclass.adapters.GymsAdapter;

public class GymsFragment extends Fragment {
    public static final String TAG = "GymsFragment";

    private ListView lvGyms;
    private Network network;
    private GymsAdapter gymsAdapter;

    private List<Object> objectList;
    private Handler handler;

    public GymsFragment() {
        handler = null;
        objectList = null;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gyms, container, false);
        lvGyms = (ListView) v.findViewById(R.id.lvGyms);
        lvGyms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (objectList != null) {
                    GymClass gc = (GymClass) objectList.get(position);
                    if (handler != null) {
                        handler.obtainMessage(MainActivity.MESSAGE_GOTO_SHEDULE, gc).sendToTarget();
                    }
                }
            }
        });

        final Context context = getActivity().getApplicationContext();
        network = Network.getInstance(getActivity());
        network.postGyms(new Network.ListCallback() {
            @Override
            public void onSuccess(List<Object> list) {
                objectList = list;
                lvGyms.setAdapter(new GymsAdapter(context, list));
                if (handler != null) {
                    handler.obtainMessage(MainActivity.MESSAGE_DIALOG_DISMISS).sendToTarget();
                }
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
                        return;
                    }
                }, null, null, null, null);
            }
        });

        return v;
    }

}
