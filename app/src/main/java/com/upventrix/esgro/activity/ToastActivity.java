package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.rafaelbarbosatec.archivimentview.iterface.ShowListern;
import com.upventrix.esgro.R;

public class ToastActivity {


    public void showOK(final AchievementView achievementView, String title, String body, Context packageContext, Class<?> cls){

        achievementView
                .setTitle(title)
                .setMensage(body)
                //.setBorderRetangle()
                .setColor(R.color.colorAccent)
                .setIcon(R.drawable.ok)
                //.setScaleTypeIcon(ImageView.ScaleType.CENTER_INSIDE)
                .setDuration(3000) // or time in milliseconds
//                .setClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(packageContext,"Click AchievementView", Toast.LENGTH_SHORT).show();
//                        //if duration TIMER_INDETERMINATE call dimiss() to hide achievement
//                        achievementView.dimiss();
//                    }
//                })
                .setShowListern(new ShowListern() {
                    @Override
                    public void start() {
                        Log.i("LOG","start");
                    }

                    @Override
                    public void show() {
                        Log.i("LOG","show");
                    }

                    @Override
                    public void dimiss() {
                        Log.i("LOG","dimiss");

                        Intent mainIntent = new Intent(packageContext,cls);
                        packageContext.startActivity(mainIntent);
                    }

                    @Override
                    public void end() {
                        Log.i("LOG","end");
                    }
                })
                .show();
    }


    public void showFailed(final AchievementView achievementView, String title, String body){

        achievementView
                .setTitle(title)
                .setMensage(body)
                //.setBorderRetangle()
                .setColor(R.color.red)
                .setIcon(R.drawable.canceld)
                //.setScaleTypeIcon(ImageView.ScaleType.CENTER_INSIDE)
                .setDuration(3000) // or time in milliseconds

                .setShowListern(new ShowListern() {
                    @Override
                    public void start() {
                        Log.i("LOG","start");
                    }

                    @Override
                    public void show() {
                        Log.i("LOG","show");
                    }

                    @Override
                    public void dimiss() {
                        Log.i("LOG","dimiss");
                    }

                    @Override
                    public void end() {
                        Log.i("LOG","end");
                    }
                })
                .show();
    }


    public void show(final AchievementView achievementView, String title, String body){

        achievementView
                .setTitle(title)
                .setMensage(body)
                //.setBorderRetangle()
                .setColor(R.color.colorAccent)
                .setIcon(R.drawable.ok)
                //.setScaleTypeIcon(ImageView.ScaleType.CENTER_INSIDE)
                .setDuration(3000) // or time in milliseconds
//                .setClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(packageContext,"Click AchievementView", Toast.LENGTH_SHORT).show();
//                        //if duration TIMER_INDETERMINATE call dimiss() to hide achievement
//                        achievementView.dimiss();
//                    }
//                })
                .setShowListern(new ShowListern() {
                    @Override
                    public void start() {
                        Log.i("LOG","start");
                    }

                    @Override
                    public void show() {
                        Log.i("LOG","show");
                    }

                    @Override
                    public void dimiss() {
                        Log.i("LOG","dimiss");

                    }

                    @Override
                    public void end() {
                        Log.i("LOG","end");
                    }
                })
                .show();
    }
}
