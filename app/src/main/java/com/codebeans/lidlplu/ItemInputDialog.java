package com.codebeans.lidlplu;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;

import static com.codebeans.lidlplu.ItemReaderContract.*;

/**
 * Created by Marvin.
 */

public class ItemInputDialog extends DialogFragment {

    private String name;
    private int pluNumber;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ItemDbHelper helper = new ItemDbHelper(getActivity());

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(ItemEntry.COLUMN_NAME_PLU, pluNumber);
                values.put(ItemEntry.COLUMN_NAME_NAME, name);

                System.out.println(pluNumber + "," + name);

                String selection = ItemEntry.COLUMN_NAME_PLU + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { Integer.toString(pluNumber) };
                // Issue SQL statement.
                db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                db.insert(ItemEntry.TABLE_NAME, null, values);

                ((MainActivity) getActivity()).UpdateList();
            }
        })
                .setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog, null);
        builder.setView(view);


        TextView pluView = (TextView) view.findViewById(R.id.dialog_plu);
        TextView nameView = (TextView) view.findViewById(R.id.dialog_name);



        if (getArguments() != null) {
            pluNumber = getArguments().getInt("plu");
            pluView.setText(Integer.toString(pluNumber));
            name = getArguments().getString("name");
            nameView.setText(name);
        }

        pluView.addTextChangedListener( new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    pluNumber = Integer.parseInt(charSequence.toString());
                } catch (NumberFormatException e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        nameView.addTextChangedListener( new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
            }
        });


        final Dialog dialog = builder.create();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.del_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = ItemEntry.COLUMN_NAME_PLU + " LIKE ?";
                String[] selectionArgs = { Integer.toString(pluNumber) };
                ItemDbHelper helper = new ItemDbHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);

                dialog.dismiss();
                ((MainActivity)getActivity()).UpdateList();
            }
        });

        return dialog;
    }



}
