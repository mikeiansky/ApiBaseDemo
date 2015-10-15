package com.github.neowen.apibasedemo.common;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhou on 2014/10/7.
 */
public class ViewHolder {

    SparseArray<View> views = new SparseArray<View>();
    View convertView ;

    public ViewHolder(int layout, View view, ViewGroup viewGroup){
        if(view == null ){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
            view.setTag(this);
        }
        convertView = view ;
    }

    public View getConvertView(){
        return convertView ;
    }

    public static ViewHolder getViewHolder(int layout,View view , ViewGroup viewGroup){
        if(view == null){
            return new ViewHolder(layout,view,viewGroup);
        }
        return (ViewHolder)view.getTag() ;
    }

    public View findViewById(int id){
        View view = views.get(id);
        if(view==null){
            view = convertView.findViewById(id);
            views.put(id,view);
        }
        return view ;
    }

}