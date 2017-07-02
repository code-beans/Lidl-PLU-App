package com.codebeans.lidlplu;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import static com.codebeans.lidlplu.ItemReaderContract.*;


/**
 * @author Marvin (marvin.neurath@code-beans.com)
 */
public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    private ItemListAdapter itemListAdapter;
    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Init();

        final EditText search = (EditText) findViewById(R.id.editText);

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemListAdapter.getFilter().filter(s);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemInputDialog inputDialog = new ItemInputDialog();
                inputDialog.show(getFragmentManager(),"Neuer Eintrag");
            }
        });
    }

    private void Init() {
        itemListView = (ListView) findViewById(R.id.itemlistview);
        itemListAdapter = new ItemListAdapter(this.getApplicationContext(),ReadAllItemsFromDB());
        itemListView.setAdapter(itemListAdapter);
        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position >= 0 && position <= itemListAdapter.getCount()) {
                    handleItemListLongClick(itemListAdapter.getItem(position));
                    return true;
                }
                return false;
            }
        });

    }

    private ArrayList<Item> ReadAllItemsFromDB() {
        ItemDbHelper helper = new ItemDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {ItemEntry.COLUMN_NAME_PLU, ItemEntry.COLUMN_NAME_NAME };
        Cursor cursor = db.query(ItemEntry.TABLE_NAME,projection,null,null,null,null,null);
        ArrayList<Item> results = new ArrayList<Item>();
        while(cursor.moveToNext()) {
            int plu = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_NAME_PLU));
            String name = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME_NAME));
            results.add(new Item(name,plu));
        }
        return results;
    }

    private void handleItemListLongClick(Item item) {
        ItemInputDialog inputDialog = new ItemInputDialog();
        Bundle args = new Bundle();
        args.putInt("plu",item.plu);
        args.putString("name",item.name);
        inputDialog.setArguments(args);
        inputDialog.show(getFragmentManager(),"Eintrag Editieren");
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        itemListAdapter.getFilter().filter(s);
        return true;
    }

    public void UpdateList() {
        itemListAdapter.clear();
        itemListAdapter.addAll(ReadAllItemsFromDB());
        itemListAdapter.notifyDataSetChanged();
        itemListView.invalidate();
    }
}
