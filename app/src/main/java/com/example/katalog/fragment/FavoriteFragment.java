package com.example.katalog.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.katalog.R;
import com.example.katalog.adapter.DbTeamAdapter;
import com.example.katalog.db.DbHelper;
import com.example.katalog.model.Team;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private DbTeamAdapter teamAdapter;
    private ArrayList<Team> teamArrayList;
    private DbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvTeam);
        teamAdapter = new DbTeamAdapter(getActivity());

        dbHelper = new DbHelper(getActivity().getApplicationContext());
        teamArrayList = dbHelper.getAllTeams();
        teamAdapter.setListTeam(teamArrayList);

        if (teamAdapter.getItemCount() != 0) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(teamAdapter);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No favorite team", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public void OnResume() {
        super.onResume();
        teamArrayList = dbHelper.getAllTeams();
        teamAdapter.setListTeam(teamArrayList);
        teamAdapter.notifyDataSetChanged();
    }
}
