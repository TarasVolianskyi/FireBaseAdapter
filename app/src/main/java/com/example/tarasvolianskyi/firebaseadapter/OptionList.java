package com.example.tarasvolianskyi.firebaseadapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OptionList extends ArrayAdapter<OptionPojo> {
    private Activity context;
    private List<OptionPojo> optionPojoList;

    public OptionList(Activity context, List<OptionPojo> optionPojoList) {
        super(context, R.layout.view_of_one_item_option_for_list_view, optionPojoList);
        this.context = context;
        this.optionPojoList = optionPojoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.view_of_one_item_option_for_list_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_name_of_option_view_of_one_item);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.tv_rating_of_option_view_of_one_item);

        OptionPojo optionPojo = optionPojoList.get(position);

        textViewName.setText(optionPojo.getOptionName());
        textViewRating.setText(String.valueOf(optionPojo.getOptionRating()));

        return listViewItem;

    }
}
