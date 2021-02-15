package com.example.katalog.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.katalog.API.TeamData;
import com.example.katalog.R;
import com.example.katalog.db.DbHelper;
import com.example.katalog.fragment.FavoriteFragment;
import com.example.katalog.model.Team;

import java.util.ArrayList;
import java.util.List;

public class DbTeamAdapter extends RecyclerView.Adapter<DbTeamAdapter.DbTeamViewHolder> {

    private DbHelper dbHelper;
    private final ArrayList<Team> listTeam = new ArrayList<>();
    private Context context;

    public DbTeamAdapter(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Team> getListTeam() {
        return listTeam;
    }

    public void setListTeam(ArrayList<Team> listNotes) {
        if (listNotes.size() > 0) {
            this.listTeam.clear();
        }

        this.listTeam.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DbTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new DbTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DbTeamViewHolder holder, int position) {
        holder.tvName.setText(listTeam.get(position).getStrTeam());
        Glide.with(context)
                .load(listTeam.get(position).getStrTeamBadge())
                .into(holder.ivTeam);

        holder.btnDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure to remove " + listTeam.get(position).getStrTeam() + " team from your favorite list?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper.deleteTeam(listTeam.get(position).getIdTeam());
                    Toast.makeText(context,  listTeam.get(position).getStrTeam() + " team removed", Toast.LENGTH_SHORT).show();

                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoriteFragment).addToBackStack(null).commit();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    public static class DbTeamViewHolder extends RecyclerView.ViewHolder {
        final CardView cvTeam;
        final ImageView ivTeam;
        final TextView tvName;
        final Button btnDelete;

        public DbTeamViewHolder(@NonNull View itemView) {
            super(itemView);

            cvTeam = itemView.findViewById(R.id.cv_item_team);
            ivTeam = itemView.findViewById(R.id.iv_item_team);
            tvName = itemView.findViewById(R.id.tv_item_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
