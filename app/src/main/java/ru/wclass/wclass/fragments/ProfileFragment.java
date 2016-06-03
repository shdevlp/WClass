package ru.wclass.wclass.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Map;

import ru.wclass.wclass.MainActivity;
import ru.wclass.wclass.Network;
import ru.wclass.wclass.R;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    private Handler handler;
    private Network network;

    private TextView tvCid;
    private TextView tvCustomerType;
    private TextView tvCustomerStatus;
    private TextView tvLogin;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvSecondName;
    private TextView tvBirthdayDate;
    private TextView tvPhoneNumber;
    private TextView tvEmail;
    private CheckBox cbSubscriptionEmail;
    private CheckBox cbSubscriptionSms;
    private TextView tvGymId;
    private TextView tvPasswordExpirationDate;
    private TextView tvHomeAddress;
    private CheckBox cbCanRecommend;
    private TextView tvManagerName;
    private TextView tvManagerPhoneNumber;
    private TextView tvManagerEmail;
    private TextView tvAuthToken;

    public ProfileFragment() {
        this.handler = null;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tvCid = (TextView) v.findViewById(R.id.tvCid);
        tvCustomerType = (TextView) v.findViewById(R.id.tvCustomerType);
        tvCustomerStatus = (TextView) v.findViewById(R.id.tvCustomerStatus);
        tvLogin = (TextView) v.findViewById(R.id.tvLogin);
        tvFirstName = (TextView) v.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) v.findViewById(R.id.tvLastName);
        tvSecondName = (TextView) v.findViewById(R.id.tvSecondName);
        tvBirthdayDate = (TextView) v.findViewById(R.id.tvBirthdayDate);
        tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhoneNumber);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        cbSubscriptionEmail = (CheckBox) v.findViewById(R.id.cbSubscriptionEmail);
        cbSubscriptionSms = (CheckBox) v.findViewById(R.id.cbSubscriptionSms);
        tvGymId = (TextView) v.findViewById(R.id.tvGymId);
        tvPasswordExpirationDate = (TextView) v.findViewById(R.id.tvPasswordExpirationDate);
        tvHomeAddress = (TextView) v.findViewById(R.id.tvHomeAddress);
        cbCanRecommend = (CheckBox) v.findViewById(R.id.cbCanRecommend);
        tvManagerName = (TextView) v.findViewById(R.id.tvManagerName);
        tvManagerPhoneNumber = (TextView) v.findViewById(R.id.tvManagerPhoneNumber);
        tvManagerEmail = (TextView) v.findViewById(R.id.tvManagerEmail);
        tvAuthToken = (TextView) v.findViewById(R.id.tvAuthToken);

        network = Network.getInstance(getActivity());
        network.postAuth(Network.testUser, Network.testPassword, false, new Network.MapCallback() {
            @Override
            public void onSuccess(Map<String, String> map) {
                if (map.containsKey(Network.P_CID)) {
                    tvCid.setText(map.get(Network.P_CID).toString());
                }

                if (map.containsKey(Network.P_CUSTOMER_TYPE)) {
                    String type = map.get(Network.P_CUSTOMER_TYPE).toString();
                    if (type.compareTo(Network.P_CUSTOMER) == 0) {
                        tvCustomerType.setText(getString(R.string.customer));
                    } else {
                        tvCustomerType.setText(getString(R.string.employee));
                    }
                }

                if (map.containsKey(Network.P_CUSTOMER_STATUS)) {
                    tvCustomerStatus.setText(map.get(Network.P_CUSTOMER_STATUS).toString());
                }

                if (map.containsKey(Network.P_LOGIN)) {
                    tvLogin.setText(map.get(Network.P_LOGIN).toString());
                }

                if (map.containsKey(Network.P_FIRST_NAME)) {
                    tvFirstName.setText(map.get(Network.P_FIRST_NAME).toString());
                }

                if (map.containsKey(Network.P_LAST_NAME)) {
                    tvLastName.setText(map.get(Network.P_LAST_NAME).toString());
                }

                if (map.containsKey(Network.P_SECOND_NAME)) {
                    tvSecondName.setText(map.get(Network.P_SECOND_NAME).toString());
                }

                if (map.containsKey(Network.P_BIRTHDAY_DATE)) {
                    tvBirthdayDate.setText(map.get(Network.P_BIRTHDAY_DATE).toString());
                }

                if (map.containsKey(Network.P_PHONE_NUMBER)) {
                    tvPhoneNumber.setText(map.get(Network.P_PHONE_NUMBER).toString());
                }

                if (map.containsKey(Network.P_EMAIL)) {
                    tvEmail.setText(map.get(Network.P_EMAIL).toString());
                }

                String str = null;
                if (map.containsKey(Network.P_SUBSCRIPTION_EMAIL)) {
                    str = map.get(Network.P_SUBSCRIPTION_EMAIL).toString();
                    cbSubscriptionEmail.setChecked(Boolean.valueOf(str));
                    cbSubscriptionEmail.setEnabled(false);
                }

                if (map.containsKey(Network.P_SUBSCRIPTION_SMS)) {
                    str = map.get(Network.P_SUBSCRIPTION_SMS).toString();
                    cbSubscriptionSms.setChecked(Boolean.valueOf(str));
                    cbSubscriptionSms.setEnabled(false);
                }

                if (map.containsKey(Network.P_GYM_ID)) {
                    tvGymId.setText(map.get(Network.P_GYM_ID).toString());
                }

                if (map.containsKey(Network.P_PASSWORD_EXPIRATION_DATE)) {
                    tvPasswordExpirationDate.setText(map.get(Network.P_PASSWORD_EXPIRATION_DATE).toString());
                }

                if (map.containsKey(Network.P_HOME_ADDRESS)) {
                    tvHomeAddress.setText(map.get(Network.P_HOME_ADDRESS).toString());
                }

                if (map.containsKey(Network.P_CAN_RECOMMEND)) {
                    str = map.get(Network.P_CAN_RECOMMEND).toString();
                    cbCanRecommend.setChecked(Boolean.valueOf(str));
                    cbCanRecommend.setEnabled(false);
                }

                if (map.containsKey(Network.P_MANAGER_NAME)) {
                    tvManagerName.setText(map.get(Network.P_MANAGER_NAME).toString());
                }

                if (map.containsKey(Network.P_MANAGER_PHONE_NUMBER)) {
                    tvManagerPhoneNumber.setText(map.get(Network.P_MANAGER_PHONE_NUMBER).toString());
                }

                if (map.containsKey(Network.P_MANAGER_EMAIL)) {
                    tvManagerEmail.setText(map.get(Network.P_MANAGER_EMAIL).toString());
                }

                if (map.containsKey(Network.P_AUTH_TOKEN)) {
                    tvAuthToken.setText(map.get(Network.P_AUTH_TOKEN).toString());
                }

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
                                if (handler != null) {
                                    handler.obtainMessage(MainActivity.MESSAGE_GOTO_GYMS).sendToTarget();
                                }
                            }
                        }, null, null, null, null);
            }
        });

        return v;
    }
}
