package com.example.lacosteplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class AudioAdapter extends ArrayAdapter<AudioList> {
    private final LayoutInflater inflater;
    private final int layout;
    private final ArrayList<AudioList> productList;
    AudioAdapter(Context context, int resource, ArrayList<AudioList> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AudioList product = productList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.countView.setText(product.getAutor());
        viewHolder.imageView.setImageResource(product.getImageResurse());

        return convertView;
    }
    private static class ViewHolder {
        final TextView nameView, countView;
        final ImageView imageView;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.textView);
            countView = view.findViewById(R.id.textView2);
            imageView = view.findViewById(R.id.imageView2);
        }
    }
}
