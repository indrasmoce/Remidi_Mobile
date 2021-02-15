package com.example.katalog.fragment;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.katalog.MainActivity;
import com.example.katalog.R;
import com.example.katalog.db.DbHelper;

public class DetailFragment extends Fragment implements View.OnClickListener {

    DbHelper dbHelper;
    public static String ID = "id";
    public static String IMAGE = "image";
    public static String NAME = "name";
    public static String YEAR = "year";
    public static String DESCRIPTION = "description";

    // Notifikasi
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final CharSequence CHANNEL_NAME = "my channel";

    ImageView ivTeam;
    TextView tvDetailBadge, tvDetailName, tvDetailYear, tvDetailDescription;
    Button btnFavorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        btnFavorite = view.findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(this);
        dbHelper = new DbHelper(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivTeam = view.findViewById(R.id.iv_team);
        tvDetailBadge = view.findViewById(R.id.tv_detail_badge);
        tvDetailName = view.findViewById(R.id.tv_detail_name);
        tvDetailYear = view.findViewById(R.id.tv_detail_year);
        tvDetailDescription = view.findViewById(R.id.tv_full_detail_description);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String url = bundle.getString(IMAGE);
            String badge = bundle.getString(IMAGE);
            String name = bundle.getString(NAME);
            String year = bundle.getString(YEAR);
            String description = bundle.getString(DESCRIPTION);

            Glide.with(this)
                    .load(url)
                    .into(ivTeam);
            tvDetailBadge.setText(badge);
            tvDetailName.setText(name);
            tvDetailYear.setText(year);
            tvDetailDescription.setText(description);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_favorite) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();

            if (tvDetailName.getText().toString().isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: Team name required", Toast.LENGTH_LONG).show();
            } else if (tvDetailBadge.getText().toString().isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: Team Badge required", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), tvDetailName.getText().toString() + " team added successfully", Toast.LENGTH_LONG).show();
                dbHelper.addTeamDetail(tvDetailName.getText().toString(), tvDetailBadge.getText().toString());
                sendNotification(v);
            }
        }
    }

    public void sendNotification(View view) {
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_active_24))
                .setContentTitle(getResources().getString(R.string.content_title))
                .setContentText(tvDetailName.getText().toString() + " " + getResources().getString(R.string.content_text))
                .setSubText(getResources().getString(R.string.subtext))
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        if(mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }

        // Notifikasi channel untuk android Orea keatas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_NAME.toString());
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
    }
}
