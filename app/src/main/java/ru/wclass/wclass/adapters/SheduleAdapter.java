package ru.wclass.wclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ru.wclass.wclass.R;
import ru.wclass.wclass.SheduleClass;

/**
 * Created by dave on 03.06.16.
 */
public class SheduleAdapter extends BaseAdapter {
    private final List<Object> list;
    private final Context context;
    private LayoutInflater inflater;

    private TextView tvSheduleId;
    private TextView tvSheduleGroup;
    private TextView tvSheduleTime;
    private TextView tvSheduleName;
    private TextView tvSheduleDescription;
    private TextView tvShedulePlace;
    private TextView tvSheduleTrainer;
    private TextView tvSheduleTrainerId;
    private TextView tvSheduleStudentLevel;
    private TextView tvSheduleDuration;
    private TextView tvSheduleAgeGroup;
    private CheckBox cbShedulePreBooked;
    private CheckBox cbShedulePrePaid;


    public SheduleAdapter(Context context, List<Object> list) {
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.shedule_list_adapter_item, parent, false);
            tvSheduleId = (TextView)view.findViewById(R.id.tvSheduleId);
            tvSheduleGroup = (TextView)view.findViewById(R.id.tvSheduleGroup);
            tvSheduleTime = (TextView)view.findViewById(R.id.tvSheduleTime);
            tvSheduleName = (TextView)view.findViewById(R.id.tvSheduleName);
            tvSheduleDescription = (TextView)view.findViewById(R.id.tvSheduleDescription);

            tvShedulePlace = (TextView)view.findViewById(R.id.tvShedulePlace);
            tvSheduleTrainer = (TextView)view.findViewById(R.id.tvSheduleTrainer);
            tvSheduleTrainerId = (TextView)view.findViewById(R.id.tvSheduleTrainerId);
            tvSheduleStudentLevel = (TextView)view.findViewById(R.id.tvSheduleStudentLevel);
            tvSheduleDuration = (TextView)view.findViewById(R.id.tvSheduleDuration);
            tvSheduleAgeGroup = (TextView)view.findViewById(R.id.tvSheduleAgeGroup);
            cbShedulePreBooked = (CheckBox) view.findViewById(R.id.cbShedulePreBooked);
            cbShedulePrePaid = (CheckBox) view.findViewById(R.id.cbShedulePrePaid);

            SheduleClass sc = (SheduleClass) list.get(position);
            tvSheduleId.setText(sc.getId());
            tvSheduleGroup.setText(sc.getGroup());
            tvSheduleTime.setText(sc.getTime());
            tvSheduleName.setText(sc.getName());
            tvSheduleDescription.setText(sc.getDescription());
            tvShedulePlace.setText(sc.getPlace());
            tvSheduleTrainer.setText(sc.getTrainer());
            tvSheduleTrainerId.setText(sc.getTrainerId());
            tvSheduleStudentLevel.setText(sc.getStudentLevel());
            tvSheduleDuration.setText(sc.getDuration());
            tvSheduleAgeGroup.setText(sc.getAgeGroup());
            cbShedulePreBooked.setChecked(sc.isPreBooked());
            cbShedulePreBooked.setEnabled(false);
            cbShedulePrePaid.setChecked(sc.isPrePaid());
            cbShedulePrePaid.setEnabled(false);
        }

        return view;
    }
}
