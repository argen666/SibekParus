package ru.sibek.parus;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.sibek.parus.Invoices.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 06.10.2014.
 */

public class ItemListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Item> listItems = new ArrayList<Item>();

    public ItemListAdapter(Context context) {
        super();

        mContext = context;
    }

    public void setItems(List<Item> items) {
        listItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Item getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            listItem = inflater.inflate(R.layout.list_item, null);

            ItemHolder holder = new ItemHolder();
            holder.tvName = (TextView) listItem.findViewById(R.id.tvName);
            //holder.language = (TextView) row.findViewById(R.id.repo_language);
            listItem.setTag(holder);
        }

        ItemHolder holder = (ItemHolder) listItem.getTag();
        Item item = listItems.get(position);
        holder.tvName.setText(item.getSdoctype() + ", " + item.getSpref().trim() + "-" + item.getSnumb().trim() + ", " +
                item.getDdocDate() +
                "\nПоставщик: " + item.getSagent());
        //holder.language.setText(repo.language);

        return listItem;
    }

    static class ItemHolder {
        TextView tvName;
        //TextView language;
    }
}