package ru.wclass.wclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.wclass.wclass.GymClass;
import ru.wclass.wclass.R;

/**
 * Created by dave on 02.06.16.
 */
public class GymsAdapter extends BaseAdapter {
    private final List<Object> list;
    private final Context context;

    private LayoutInflater inflater;
    private TextView tvName;
    private TextView tvAddress;

    public GymsAdapter(Context context, List<Object> list) {
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.gyms_list_adapter_item, parent, false);
            GymClass gc = (GymClass)list.get(position);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvName.setText(gc.getName());
            tvAddress.setText(gc.getGymAddress());
        }

        return view;
    }
}
