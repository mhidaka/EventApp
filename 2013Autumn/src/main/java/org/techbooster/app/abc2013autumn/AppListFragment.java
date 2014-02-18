
package org.techbooster.app.abc2013autumn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int padding = (int) (getResources().getDisplayMetrics().density * 8); // 8dip
        ListView listView = getListView();
        listView.setPadding(padding, 0, padding, 0);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setDivider(null);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View header = inflater.inflate(R.layout.list_header_footer, listView, false);
        View footer = inflater.inflate(R.layout.list_footer, listView, false);

        TextView tv = (TextView) footer.findViewById(R.id.footer);
        String str = getResources().getString(R.string.footer);
        MovementMethod method = LinkMovementMethod.getInstance();
        tv.setMovementMethod(method);
        CharSequence html = Html.fromHtml(str);
        tv.setText(html);

        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer, null, false);

        setTrack(7);
    }

    public class CardListAdapter extends ArrayAdapter<Track> {

        LayoutInflater mInflater;
        int mLastAnimationPosition = 0;

        public CardListAdapter(Context context) {
            super(context, 0);
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_card, parent, false);
            }

            Track info = getItem(position);

            ImageView iv = (ImageView) convertView.findViewById(R.id.icon);

            TextView tv = (TextView) convertView.findViewById(R.id.title);
            tv.setText(info.title);

            tv = (TextView) convertView.findViewById(R.id.time);
            tv.setText(info.time);

            if(position==0){
                iv.setImageResource(android.R.drawable.ic_menu_info_details);
                tv = (TextView) convertView.findViewById(R.id.sub);
                tv.setText("");
            }else{
                iv.setImageResource(R.drawable.ic_launcher);
                tv = (TextView) convertView.findViewById(R.id.sub);
                String body = new String();
                for(Person person  : info.persons){
                    body += person.name + "\n" + person.org + "\n" ;
                }
                tv.setText(body + "\n" + info.disc);
            }

            if (mLastAnimationPosition < position) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.motion);
                convertView.startAnimation(animation);
                mLastAnimationPosition = position;
            }

            return convertView;
        }
    }

    public List<Track> setTrack(int index){

        int res = R.array.track7;
        if(index == 0)res = R.array.track0;
        if(index == 1)res = R.array.track1;
        if(index == 2)res = R.array.track2;
        if(index == 3)res = R.array.track3;
        if(index == 4)res = R.array.track4;
        if(index == 5)res = R.array.track5;
        if(index == 6)res = R.array.track6;
        if(index == 7)res = R.array.track7;


        List<Track> trackInfo = new ArrayList();
        TypedArray typedArray = getResources().obtainTypedArray(res);
        int length = typedArray.length();

        for(int i=0; i<length; i++){
            int resourceId = typedArray.getResourceId(i, 0);
            String[] array = getResources().getStringArray(resourceId);

            Track track = new Track();
            track.time = array[0];
            track.title = array[1];

            if(array.length != 2){

                track.disc = array[2];
                track.persons.add(new Person(array[3], array[4]));

                for(int j=5; j < array.length ; j+=2){
                    track.persons.add(new Person(array[j], array[j+1]));
                }
            }

            trackInfo.add(track);
        }

        CardListAdapter adapter = new CardListAdapter(getActivity());

        if (trackInfo != null) {
            for (Track info : trackInfo) {
                adapter.add(info);
            }
        }

        setListAdapter(adapter);

        return trackInfo;
    }

    public class Track{

        public String time;
        public String title;
        public String disc;
        public List<Person> persons = new ArrayList();
    }

    public class Person{
        public String name;
        public String org;

        Person(String n, String o){
            name = n;
            org = o;
        }
    }
}
