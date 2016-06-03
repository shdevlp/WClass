package ru.wclass.wclass;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import ru.wclass.wclass.fragments.GymsFragment;
import ru.wclass.wclass.fragments.ProfileFragment;
import ru.wclass.wclass.fragments.SheduleFragment;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    public static final String TAG = "MainActivity";

    private Toolbar myToolbar;
    private Drawer.Result drawerResult;
    private ProgressDialog dialog;

    private GymsFragment gymsFragment;
    private SheduleFragment sheduleFragment;
    private ProfileFragment profileFragment;

    private Handler handler;
    private static Activity activity = null;

    public static final int MESSAGE_DIALOG_DISMISS = 1;
    public static final int MESSAGE_GOTO_PROFILE = 2;
    public static final int MESSAGE_GOTO_GYMS = 3;
    public static final int MESSAGE_GOTO_SHEDULE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_main));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        createDrawer();

        handler = new Handler(this);

        gymsFragment = new GymsFragment();
        sheduleFragment = new SheduleFragment();
        profileFragment = new ProfileFragment();

        gymsFragment.setHandler(handler);
        sheduleFragment.setHandler(handler);
        profileFragment.setHandler(handler);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.waiting));
        dialog.setTitle(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        getFragmentManager().beginTransaction().add(R.id.fragmentId,
                gymsFragment).commit();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_DIALOG_DISMISS:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } break;
            case MESSAGE_GOTO_PROFILE: {
                dialog.show();
                getFragmentManager().beginTransaction().replace(R.id.fragmentId,
                        profileFragment).commit();
            } break;
            case MESSAGE_GOTO_GYMS: {
                dialog.show();
                drawerResult.setSelection(0);
                getFragmentManager().beginTransaction().replace(R.id.fragmentId,
                        gymsFragment).commit();
            } break;
            case MESSAGE_GOTO_SHEDULE: {
                dialog.show();
                GymClass gc = (GymClass) msg.obj;
                sheduleFragment.setGymClass(gc);
                getFragmentManager().beginTransaction().replace(R.id.fragmentId,
                        sheduleFragment).commit();
            } break;

        }
        return false;
    }

    private void createDrawer() {
        if (drawerResult == null) {
            drawerResult = new Drawer()
                    .withActivity(this)
                    .withToolbar(myToolbar)
                    .withActionBarDrawerToggle(true)
                    .withHeader(R.layout.drawer_header)
                    .addDrawerItems(
                            new PrimaryDrawerItem()
                                    .withName(R.string.item_gyms)
                                    .withIcon(FontAwesome.Icon.faw_home)
                                    .withIdentifier(1),
                            new PrimaryDrawerItem()
                                    .withName(R.string.item_profile)
                                    .withIcon(FontAwesome.Icon.faw_user)
                                    .withIdentifier(2)
                    )
                    .withOnDrawerListener(new Drawer.OnDrawerListener() {
                        @Override
                        public void onDrawerOpened(View drawerView) {
                            InputMethodManager inputMethodManager = (InputMethodManager)
                                    MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(
                                    MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {
                        }
                    })
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id, IDrawerItem drawerItem) {
                            if (drawerItem instanceof Nameable) {
                                final int resid = ((Nameable) drawerItem).getNameRes();
                                switch (resid) {
                                    case R.string.item_gyms: {
                                        if (!isFragment(GymsFragment.TAG)) {
                                            dialog.show();
                                            getFragmentManager().beginTransaction().replace(R.id.fragmentId,
                                                    gymsFragment).commit();
                                        };
                                    }
                                    break;
                                    case R.string.item_profile: {
                                        if (!isFragment(ProfileFragment.TAG)) {
                                            dialog.show();
                                            getFragmentManager().beginTransaction().replace(R.id.fragmentId,
                                                    profileFragment).commit();
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }).build();
        }
    }

    /**
     *
     * @param tag
     * @return
     */
    public static boolean isFragment(String tag) {
        Fragment currentFragment = activity.getFragmentManager()
                .findFragmentById(R.id.fragmentId);
        if (tag.compareTo(GymsFragment.TAG) == 0) {
            return (currentFragment instanceof GymsFragment);
        }
        if (tag.compareTo(ProfileFragment.TAG) == 0) {
            return (currentFragment instanceof ProfileFragment);
        }
        if (tag.compareTo(SheduleFragment.TAG) == 0) {
            return (currentFragment instanceof SheduleFragment);
        }

        return false;
    }

    public interface AlertCallback {
        public void callbackFunc();
    };

    public static void alertDialog(String title, String message, int icon, View view, final int btnTextSize,
                                   final String neuBtnText, final AlertCallback neuBtnCallBack,
                                   final String negBtnText, final AlertCallback negBtnCallBack,
                                   final String posBtnText, final AlertCallback posBtnCallBack) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        if (icon != 0) {
            builder.setIcon(icon);
        }
        if (view != null) {
            builder.setView(view);
        }
        builder.setCancelable(false);

        if (neuBtnText != null) {
            builder.setNeutralButton(neuBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (neuBtnCallBack != null) {
                        neuBtnCallBack.callbackFunc();
                    }
                    dialog.cancel();
                }
            });
        }
        if (negBtnText != null) {
            builder.setNegativeButton(negBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (negBtnCallBack != null) {
                        negBtnCallBack.callbackFunc();
                    }
                    dialog.cancel();
                }
            });
        }
        if (posBtnText != null) {
            builder.setPositiveButton(posBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (posBtnCallBack != null) {
                        posBtnCallBack.callbackFunc();
                    }
                    dialog.cancel();
                }
            });
        }

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                if (neuBtnText != null) {
                    alert.getButton(DialogInterface.BUTTON_NEUTRAL)
                            .setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                    if (btnTextSize != 0) {
                        alert.getButton(DialogInterface.BUTTON_NEUTRAL).setTextSize(btnTextSize);
                    }
                }
                if (negBtnText != null) {
                    alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                    if (btnTextSize != 0) {
                        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(btnTextSize);
                    }
                }
                if (posBtnText != null) {
                    alert.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                    if (btnTextSize != 0) {
                        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(btnTextSize);
                    }
                }
            }
        });
        alert.show();
    }
}
