package com.example.cse110_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.R;
import com.example.cse110_project.SessionDetailsActivity;
import com.example.cse110_project.StudentDetailActivity;
import com.example.cse110_project.databases.session.Session;

import java.util.List;

public class SessionsViewAdapter extends RecyclerView.Adapter<SessionsViewAdapter.ViewHolder> {
    private final List<Session> sessions;

    public SessionsViewAdapter(List<Session> sessions) {
        super();
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public SessionsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsViewAdapter.ViewHolder holder, int position) {
        holder.setSession(sessions.get(position));
    }

    @Override
    public int getItemCount() {
        return this.sessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView sessionTextView;
        private Session session;

        ViewHolder(View itemView) {
            super(itemView);
            this.sessionTextView = itemView.findViewById(R.id.session_name);
            itemView.setOnClickListener(this);
        }

        public void setSession(Session session) {
            this.session = session;
            this.sessionTextView.setText(session.getSessionName());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            Intent intent = new Intent(context, SessionDetailsActivity.class);
            intent.putExtra("sessionName", this.session.getSessionName());

            context.startActivity(intent);
        }
    }
}
