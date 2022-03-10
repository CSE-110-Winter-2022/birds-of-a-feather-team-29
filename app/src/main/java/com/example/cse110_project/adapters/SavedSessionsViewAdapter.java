package com.example.cse110_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.R;
import com.example.cse110_project.databases.session.Session;

import java.util.List;

public class SavedSessionsViewAdapter extends RecyclerView.Adapter<SavedSessionsViewAdapter.ViewHolder> {
    private final List<Session> sessions;

    public SavedSessionsViewAdapter(List<Session> sessions) {
        super();
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public SavedSessionsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSessionsViewAdapter.ViewHolder holder, int position) {
        holder.setSession(sessions.get(position));
    }

    @Override
    public int getItemCount() {
        return this.sessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView sessionTextView;
        private Session session;

        ViewHolder(View itemView) {
            super(itemView);
            this.sessionTextView = itemView.findViewById(R.id.session_name);
        }

        public void setSession(Session session) {
            this.session = session;
        }
    }
}
