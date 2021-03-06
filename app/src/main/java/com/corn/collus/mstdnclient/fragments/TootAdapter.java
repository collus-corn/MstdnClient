package com.corn.collus.mstdnclient.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corn.collus.mstdnclient.R;
import com.corn.collus.mstdnclient.models.Toot;
import com.corn.collus.mstdnclient.presenters.APIPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;


/**
 * Created by mitsu on 2017/05/08.
 */

public class TootAdapter extends BaseAdapter{
    private LayoutInflater inflater=null;
    private List<Toot> tootList;
    private APIPresenter api = APIPresenter.getInstance();

    public TootAdapter(Context context){
        tootList = new ArrayList<Toot>();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Completable refreshHome(){
        return Completable.create(o->{
            api.readHome().subscribe(toots -> {
                tootList.addAll(0,toots);
                super.notifyDataSetChanged();
                o.onComplete();
            });
        });
    }

    public Completable refreshPublic(){
        return Completable.create(o->{
            api.readPublic().subscribe(toots -> {
                tootList.addAll(0,toots);
                super.notifyDataSetChanged();
                o.onComplete();
            });
        });
    }

    @Override
    public int getCount(){
        return tootList.size();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = inflater.inflate(R.layout.toot,parent,false);
        TextView textView = (TextView) convertView.findViewById(R.id.toot_text);
        textView.setText(tootList.get(position).getContent());
        return convertView;
    }

    @Override
    public long getItemId(int position){
        return Long.parseLong(tootList.get(position).getId());
    }

    public Object getItem(int position){
        return tootList.get(position);
    }
}
