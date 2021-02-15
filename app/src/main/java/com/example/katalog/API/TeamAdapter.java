package com.example.katalog.API;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.katalog.R;
import com.example.katalog.fragment.DetailFragment;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<TeamData> listOfTeamData;
    private Context context;

    public TeamAdapter(List<TeamData> listOfTeamData, Context context){
        this.listOfTeamData = listOfTeamData;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_catalog, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamData teamData = listOfTeamData.get(position);

        holder.teamName.setText(teamData.getStrTeam());
        holder.teamInformedYear.setText(teamData.getIntFormedYear());
        holder.teamDescription.setText(teamData.getStrDescriptionEN());

        Glide.with(context)
                .load(teamData.getStrTeamBadge())
                .into(holder.teamImage);

        holder.cvTeam.setOnClickListener(v -> {
            DetailFragment detailFragment = new DetailFragment();

            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.NAME, teamData.getStrTeam());
            bundle.putString(DetailFragment.YEAR, teamData.getIntFormedYear());
            bundle.putString(DetailFragment.DESCRIPTION, teamData.getStrDescriptionEN());
            bundle.putString(DetailFragment.IMAGE, teamData.getStrTeamBadge());
            detailFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return listOfTeamData.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName, teamInformedYear, teamDescription;
        ImageView teamImage;
        CardView cvTeam;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.tv_name);
            teamInformedYear = itemView.findViewById(R.id.tv_informedyear);
            teamDescription = itemView.findViewById(R.id.tv_description);
            teamImage = itemView.findViewById(R.id.image_team);
            cvTeam = itemView.findViewById(R.id.cv_team);
        }
    }
}
