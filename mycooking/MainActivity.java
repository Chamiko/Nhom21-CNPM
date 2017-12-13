package com.example.banhnhandau.mycooking;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.bookmark.FragmentBookmark;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.example.banhnhandau.mycooking.eating.FragmentEating;
import com.example.banhnhandau.mycooking.search.FragmentSearch;
import com.example.banhnhandau.mycooking.showEating.FragmentTotal;
import com.example.banhnhandau.mycooking.type.FragmentType;
import com.example.banhnhandau.mycooking.video.FragmentPlayVideo;
import com.example.banhnhandau.mycooking.video.FragmentVideo;
import com.example.banhnhandau.mycooking.video.Video;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static DataBaseHelper dataBaseHelper;
    public static SQLiteDatabase database;
    public static DrawerLayout drawLayout;
    Typeface custom_font;
    TextView txtMBookmark, txtMHome, txtMSearch, txtMUs, txtMQuit, txtMVideo;
    ImageView iconBookmark, iconHome, iconSearch, iconUs, iconQuit, iconVideo;
    String nameLoad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappedOfNavigation();
        custom_font = Typeface.createFromAsset(getAssets(), "UVF DK Crayon Crumble.ttf");

        try {
            dataBaseHelper = new DataBaseHelper(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = dataBaseHelper.getWritableDatabase();

        drawLayout = (DrawerLayout) findViewById(R.id.drawLayout);

        loadFragment("type");

        clickBookmark();
        clickHome();
        clickSearch();
        cickVideo();
        clickAboutUs();
        clickQiut();
    }



    public void loadFragment(String name) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        Fragment fragment;
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;

        if (!nameLoad.equals(name)) {
            nameLoad = name;
            if (name.equals("type")) {
                fragment = FragmentType.newInstance();
                transaction.add(R.id.container, fragment, name);
            } else if (name.equals("bookmark")) {
                fragment = FragmentBookmark.newInstance();
                transaction.add(R.id.container, fragment, name);
            } else if (name.equals("search")) {
                fragment = FragmentSearch.newInstance();
                transaction.add(R.id.container, fragment, name);
            } else if (name.equals("video")) {
                fragment = FragmentVideo.newInstance();
                transaction.add(R.id.container, fragment, name);
            }
            transaction.addToBackStack(name);
            if (index > -1) {
                FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(tag);
                transaction.hide(fragment2);
            }
            transaction.commit();
        } else {
            if (drawLayout.isDrawerOpen(Gravity.LEFT)) {
                drawLayout.closeDrawer(Gravity.LEFT);
            }
        }
    }

    public void loadFragment(String name, int idType, String nameType) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment;
        if (name.equals("eating")) {
            fragment = FragmentEating.newInstance(idType, nameType);
            transaction.add(R.id.container, fragment, "eating");
            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(tag);
            transaction.hide(fragment2);
            transaction.addToBackStack("eating");
            transaction.commit();
        }
    }

    public void loadFragment(String name, Eating obj) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (name.equals("total")) {
            fragment = FragmentTotal.newInstance(obj);
            transaction.add(R.id.container, fragment, "total");
            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(tag);
            transaction.hide(fragment2);
            transaction.addToBackStack("total");
            transaction.commitAllowingStateLoss();
        }
    }

    public void loadFragment(String name, Video obj) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (name.equals("play")) {
            fragment = FragmentPlayVideo.newInstance(obj);
            transaction.add(R.id.container, fragment, "play");
            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(tag);
            transaction.hide(fragment2);
            transaction.addToBackStack("play");
            transaction.commitAllowingStateLoss();
        }
    }

    public void mappedOfNavigation() {
        iconBookmark = (ImageView) findViewById(R.id.iconBookmark);
        txtMBookmark = (TextView) findViewById(R.id.txtMBookmark);
        iconHome = (ImageView) findViewById(R.id.iconHome);
        txtMHome = (TextView) findViewById(R.id.txtMHome);
        iconSearch = (ImageView) findViewById(R.id.iconSearch);
        txtMSearch = (TextView) findViewById(R.id.txtMSearch);
        iconVideo = (ImageView) findViewById(R.id.iconVideo);
        txtMVideo = (TextView) findViewById(R.id.txtMVideo);
        iconUs = (ImageView) findViewById(R.id.iconUs);
        txtMUs = (TextView) findViewById(R.id.txtMUs);
        iconQuit = (ImageView) findViewById(R.id.iconQuit);
        txtMQuit = (TextView) findViewById(R.id.txtMQuit);
    }

    public void clickBookmark() {
        iconBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment("bookmark");
                drawLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txtMBookmark.setTypeface(custom_font);
        txtMBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconBookmark.performClick();
            }
        });
    }

    public void clickHome() {
        iconHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment("type");
                drawLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txtMHome.setTypeface(custom_font);
        txtMHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconHome.performClick();

            }
        });
    }

    public void clickSearch() {
        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment("search");
                drawLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txtMSearch.setTypeface(custom_font);
        txtMSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconSearch.performClick();
            }
        });
    }

    private void cickVideo() {
        iconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment("video");
                drawLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txtMVideo.setTypeface(custom_font);
        txtMVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconVideo.performClick();
            }
        });
    }

    public void clickAboutUs() {
        iconUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawLayout.closeDrawer(Gravity.LEFT);
                dialogAboutUs();
            }
        });
        txtMUs.setTypeface(custom_font);
        txtMUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconUs.performClick();

            }
        });
    }

    public void clickQiut() {
        iconQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawLayout.closeDrawer(Gravity.LEFT);
                dialogQuit();
            }
        });
        txtMQuit.setTypeface(custom_font);
        txtMQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconQuit.performClick();

            }
        });
    }

    private void dialogAboutUs() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about_us);
        dialog.setCanceledOnTouchOutside(false);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.copyFrom(dialogWindow.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void dialogQuit() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quit);
        dialog.setCanceledOnTouchOutside(false);
        TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        Log.d("index", index + "");
        String tag = "";
        if (index > 0) {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index - 1);
            tag = backEntry.getName();
        }
        FragmentManager.BackStackEntry backEntry2 = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag2 = backEntry2.getName();

        nameLoad = tag;
        if (tag2.equals("type")) {
            dialogQuit();
        } else {
            super.onBackPressed();
        }
    }
}
