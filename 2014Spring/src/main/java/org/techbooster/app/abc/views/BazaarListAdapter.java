package org.techbooster.app.abc.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.models.BazaarEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BazaarListAdapter extends BindableAdapter<BazaarEntry> {

    private List<BazaarEntry> mBazaarEntries = new ArrayList<BazaarEntry>();

    public BazaarListAdapter(Context context) {
        super(context);
    }

    public void add(BazaarEntry entry) {
        mBazaarEntries.add(entry);
    }

    public void addAll(List<BazaarEntry> entries) {
        mBazaarEntries.addAll(entries);
    }

    @Override
    public int getCount() {
        return mBazaarEntries.size();
    }

    @Override
    public long getItemId(int position) {
        return mBazaarEntries.get(position).hashCode();
    }

    @Override
    public BazaarEntry getItem(int position) {
        return mBazaarEntries.get(position);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.listitem_bazaar_entry, null, false);
        new ViewHolder(view);
        return view;
    }

    @Override
    public void bindView(BazaarEntry item, int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(item.getName());
        holder.title.setText(item.getTitle());
        holder.summary.setText(item.getSummary());
    }

    public class ViewHolder {

        @InjectView(R.id.name)
        public TextView name;
        @InjectView(R.id.title)
        public TextView title;
        @InjectView(R.id.summary)
        public TextView summary;
        public final View root;

        public ViewHolder(View root) {
            ButterKnife.inject(this, root);
            this.root = root;
            root.setTag(this);
        }
    }
}
