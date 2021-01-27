package com.example.habii.sefuda;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karan.churi.PermissionManager.PermissionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {


    List<Sounds> soundsList;

    Context context;

    Activity activity;

    AudioAttributes audioAttributes;

    SoundPool soundPool;

    int sound;

    AlertDialog alertDialog;

    CheckBox alarmset,ringtoneset,notificationset;


    public SoundAdapter(List<Sounds> soundsList, Context context , Activity activity) {
        this.soundsList = soundsList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_model,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SoundAdapter.ViewHolder holder, final int position) {


        Random rnd = new Random();
        final String[] col = {

                "#B71C1C",
                "#880E4F",
                "#4A148C",
                "#311B92",
                "#1A237E",
                "#0D47A1",
                "#01579B",
                "#006064",
                "#004D40",
                "#1B5E20",
                "#33691E",
                "#1B5E20",
                "#827717",
                "#F57F17",
                "#E65100",
                "#BF360C",
                "#3E2723",
                "#212121",
                "#000000",
                "#3E2723"
        };

        holder.playBt.setBackgroundColor(Color.parseColor(col[rnd.nextInt(20)]));


        holder.soundName.setText(soundsList.get(position).getName());


        holder.playBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.playBt.animate().rotation(holder.playBt.getRotation()+360).start();

                MediaPlayer mediaPlayer = new MediaPlayer();

                mediaPlayer = MediaPlayer.create(context, soundsList.get(position).getId());

                if(mediaPlayer.isPlaying()){

                    mediaPlayer.stop();
                }

                if(mediaPlayer != null){


                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;

                        }
                    });


                }else{

                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show();
                }

            }
        });



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.playBt.animate().rotation(holder.playBt.getRotation()+360).start();

                MediaPlayer mediaPlayer = new MediaPlayer();

                mediaPlayer = MediaPlayer.create(context, soundsList.get(position).getId());

                if(mediaPlayer.isPlaying()){

                    mediaPlayer.stop();
                }

                if(mediaPlayer != null){


                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;

                        }
                    });


                }else{

                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show();
                }

            }
        });



        holder.sharebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    PermissionManager permissionManager;
                    permissionManager = new PermissionManager() {};
                    permissionManager.checkAndRequestPermissions(activity);

                }else{

                    holder.sharebt.animate().rotation(holder.sharebt.getRotation()+360).start();

                    InputStream inputStream;
                    FileOutputStream fileOutputStream;
                    try {
                        inputStream = context.getResources().openRawResource(soundsList.get(position).getId());
                        fileOutputStream = new FileOutputStream(
                                new File(Environment.getExternalStorageDirectory(), soundsList.get(position).getName()+".mp3"));

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, length);
                        }

                        inputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/"+soundsList.get(position).getName()+".mp3" ));
                    intent.setType("audio/*");
                    context.startActivity(Intent.createChooser(intent, "Share sound"));


                }

            }
        });











        final AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(context);
        final LayoutInflater inflater1 = LayoutInflater.from(context);
        alertBuilder1.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                InputStream inputStream;
                FileOutputStream fileOutputStream;
                try {
                    inputStream = context.getResources().openRawResource(soundsList.get(position).getId());
                    fileOutputStream = new FileOutputStream(
                            new File(Environment.getExternalStorageDirectory(), soundsList.get(position).getName()+".mp3"));

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, length);
                    }

                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try{

                    if(!alarmset.isChecked()&& !ringtoneset.isChecked() && !notificationset.isChecked()){

                        Toast.makeText(context, "Select to set sound", Toast.LENGTH_SHORT).show();


                    }else{

                        if(alarmset.isChecked()){

                            RingtoneManager.setActualDefaultRingtoneUri(context,
                                    RingtoneManager.TYPE_ALARM,Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/"+soundsList.get(position).getName()+".mp3" ));

                            Toast.makeText(context, "Alarm sound set successfully", Toast.LENGTH_SHORT).show();

                        }

                        if(ringtoneset.isChecked()){

                            RingtoneManager.setActualDefaultRingtoneUri(context,
                                    RingtoneManager.TYPE_RINGTONE,Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/"+soundsList.get(position).getName()+".mp3" ));

                            Toast.makeText(context, "Ringtone set successfully", Toast.LENGTH_SHORT).show();

                        }

                        if(notificationset.isChecked()){

                            RingtoneManager.setActualDefaultRingtoneUri(context,
                                    RingtoneManager.TYPE_NOTIFICATION,Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/"+soundsList.get(position).getName()+".mp3" ));

                            Toast.makeText(context, "Notification sound set successfully", Toast.LENGTH_SHORT).show();
                        }

                    }



                }catch (Exception e){


                    AlertDialog.Builder  alert_builder = new AlertDialog.Builder(context);

                    alert_builder.setMessage("Modify system settings");

                    alert_builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(context, "You can't use this option without permission", Toast.LENGTH_SHORT).show();

                        }
                    });

                    alertDialog = alert_builder.create();
                    alertDialog.setTitle("Permission needed");
                    alertDialog.show();
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });








        holder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.set.animate().rotation(holder.set.getRotation()+360).start();

                final View dialogView1 = inflater1.inflate(R.layout.sound_set, null);

                final AlertDialog alertDialog1;

                alertBuilder1.setView(dialogView1);
                alertDialog1 = alertBuilder1.create();
                
                alertDialog1.show();

                alarmset = alertDialog1.findViewById(R.id.alarmset);
                ringtoneset = alertDialog1.findViewById(R.id.ringtoneset);
                notificationset = alertDialog1.findViewById(R.id.notificationset);

            }
        });




        holder.set.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                holder.set.animate().rotation(holder.set.getRotation()+360).start();

                InputStream inputStream;
                FileOutputStream fileOutputStream;
                try {
                    inputStream = context.getResources().openRawResource(soundsList.get(position).getId());
                    fileOutputStream = new FileOutputStream(
                            new File(Environment.getExternalStorageDirectory(), soundsList.get(position).getName()+".mp3"));

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, length);
                    }

                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try{

                    RingtoneManager.setActualDefaultRingtoneUri(context,
                            RingtoneManager.TYPE_RINGTONE,Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/"+soundsList.get(position).getName()+".mp3" ));

                    Toast.makeText(context, "Ringtone set successfully", Toast.LENGTH_SHORT).show();


                }catch (Exception e){

                    AlertDialog.Builder  alert_builder = new AlertDialog.Builder(context);

                    alert_builder.setMessage("Modify system settings");

                    alert_builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(context, "You can't use this option without permission", Toast.LENGTH_SHORT).show();

                        }
                    });


                    alertDialog = alert_builder.create();
                    alertDialog.setTitle("Permission needed");
                    alertDialog.show();

                }

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {

        return soundsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        TextView soundName;

        ImageView playBt,sharebt,set;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            soundName = (TextView)mView.findViewById(R.id.soundname);
            playBt = (ImageView)mView.findViewById(R.id.play);
            set = (ImageView)mView.findViewById(R.id.set);
            sharebt = (ImageView)mView.findViewById(R.id.sharebt);
        }
    }
}
