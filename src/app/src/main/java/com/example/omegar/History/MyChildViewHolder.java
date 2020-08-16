package com.example.omegar.History;

import android.view.View;
import android.widget.TextView;
import com.example.omegar.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class MyChildViewHolder extends ChildViewHolder {

    public TextView listChild;

    public MyChildViewHolder(View itemView) {
        super(itemView);
        listChild = (TextView) itemView.findViewById(R.id.listChild);

    }

    public void onBind(String Sousdoc) {
        listChild.setText(Sousdoc);

    }


}