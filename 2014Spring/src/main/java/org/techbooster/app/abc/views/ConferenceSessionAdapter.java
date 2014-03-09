package org.techbooster.app.abc.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.models.ConferenceSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConferenceSessionAdapter extends BindableAdapter<ConferenceSession> {

    private List<ConferenceSession> mConferenceSessions = new ArrayList<ConferenceSession>();

    public ConferenceSessionAdapter(Context context) {
        super(context);
    }

    public void add(ConferenceSession entry) {
        mConferenceSessions.add(entry);
    }

    public void addAll(List<ConferenceSession> entries) {
        mConferenceSessions.addAll(entries);
    }

    @Override
    public int getCount() {
        return mConferenceSessions.size();
    }

    @Override
    public ConferenceSession getItem(int position) {
        return mConferenceSessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mConferenceSessions.get(position).hashCode();
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.listitem_conference_session, null, false);
        new ViewHolder(view);
        return view;
    }

    @Override
    public void bindView(ConferenceSession item, int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mBeginTime.setText(item.getBeginTime());
        holder.mTitle.setText(item.getSessionTitle());
        holder.mSpeaker.setText(item.getSpeakerName());
        holder.mSpeakerProfile.setText(item.getSpeakerProfile());
        holder.mDescription.setText(item.getDescription());
    }

    public static class ViewHolder {
        @InjectView(R.id.begin_time)
        TextView mBeginTime;
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.speaker)
        TextView mSpeaker;
        @InjectView(R.id.speaker_profile)
        TextView mSpeakerProfile;
        @InjectView(R.id.description)
        TextView mDescription;
        public final View root;

        public ViewHolder(View root) {
            ButterKnife.inject(this, root);
            this.root = root;
            root.setTag(this);
        }
    }
}
