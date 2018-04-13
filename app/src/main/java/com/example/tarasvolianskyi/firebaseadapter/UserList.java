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

public class UserList extends ArrayAdapter<UsersPojo> {

    private Activity context;
    private List<UsersPojo> usersPojoList;

    public UserList(Activity context, List<UsersPojo> usersPojoList) {
        super(context, R.layout.view_of_one_item_user_for_list_view, usersPojoList);
        this.context = context;
        this.usersPojoList = usersPojoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.view_of_one_item_user_for_list_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_name_of_user_view_of_one_item);
        TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.tv_category_of_user_view_of_one_item);

   UsersPojo usersPojo = usersPojoList.get(position);

   textViewName.setText(usersPojo.getUserName());
   textViewCategory.setText(usersPojo.getUserCategory());

        return listViewItem;

    }
}
