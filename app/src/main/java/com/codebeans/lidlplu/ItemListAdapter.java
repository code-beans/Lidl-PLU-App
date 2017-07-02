package com.codebeans.lidlplu;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.R.attr.typeface;

/**
 * Created by Marvin.
 */

public class ItemListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Item> items;
    private ArrayList<Item> filteredItems;
    private Context context;
    private ItemFilter itemFilter;

    public ItemListAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        filteredItems = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Item getItem(int i) {
        return filteredItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {

        final Item item = getItem(pos);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.itemlist_row_layout,parent,false);
        }
        TextView itemPluView = (TextView) view.findViewById(R.id.plu_textview);
        TextView itemNameView = (TextView) view.findViewById(R.id.item_textview);

        itemPluView.setText(Integer.toString(item.plu));
        itemNameView.setText(item.name);

        return view;
    }

    @Override
    public Filter getFilter() {
        if(itemFilter == null)
            itemFilter = new ItemFilter();
        return itemFilter;
    }

    public void clear() {
        items.clear();
        filteredItems.clear();
    }

    public void addAll(ArrayList<Item> items) {
        this.items.addAll(items);
        getFilter().filter(null);
    }

    private class ItemFilter extends Filter {


        @Override
        protected FilterResults performFiltering(final CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //we have something to filter by
            if(charSequence != null && charSequence.length() > 0 ) {
                ArrayList<Item> results = new ArrayList<Item>();
                String s = charSequence.toString().toLowerCase();
                try {
                    //TODO extend to closest match search on plu number
                    int pluNumber = Integer.parseInt(s);
                    for (Item item : items) {
                        if(item.plu != pluNumber)
                            continue;
                        results.add(item);
                    }
                } catch(NumberFormatException e) {
                    for (Item item : items) {
                        if(item.name == null || !item.name.toLowerCase().contains(s))
                            continue;
                        results.add(item);
                    }
                    Collections.sort(results, new ClosestNameMatch(s));

                }
                filterResults.count = results.size();
                filterResults.values = results;
            } else {

                Collections.sort(items,new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        if(o1.name != null && o2.name != null){
                            return o1.name.compareTo(o2.name);
                        }

                        return 0;
                    }
                });

                filterResults.count = items.size();
                filterResults.values = items;
            }
            return filterResults;


        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredItems = (ArrayList<Item>) filterResults.values;
            notifyDataSetChanged();
        }

        private class ClosestNameMatch implements Comparator<Item> {
            private final String keyWord;

            ClosestNameMatch(String keyWord) {
                this.keyWord = keyWord.toLowerCase();
            }

            @Override
            public int compare(Item o1, Item o2) {

                if(o1.name.startsWith(keyWord)) {
                    return o2.name.startsWith(keyWord)? o1.name.compareTo(o2.name.toString()): -1;
                } else {
                    return o2.name.startsWith(keyWord)? 1: o1.name.compareTo(o2.name.toString());
                }
            }
        }


    }


}
