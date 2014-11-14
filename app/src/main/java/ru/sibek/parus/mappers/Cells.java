package ru.sibek.parus.mappers;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.CellsProvider;

public class Cells {

    @Expose
    private List<CellItem> items = new ArrayList<CellItem>();

    /**
     * @return The items
     */
    public List<CellItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<CellItem> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "Cells{" +
                "items=" + items.size() +
                '}';
    }

    public ContentValues[] toContentValues(String rackID) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<CellItem> items = this.getItems();
        for (CellItem item : items) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(CellsProvider.Columns.RACK_ID, rackID);
            contentValues.put(CellsProvider.Columns.SFULLNAME, item.getSfullnumber());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }


    public class CellItem {

        @Expose
        private String sfullnumber;

        /**
         * @return The sfullnumber
         */
        public String getSfullnumber() {
            return sfullnumber;
        }

        /**
         * @param sfullnumber The sfullnumber
         */
        public void setSfullnumber(String sfullnumber) {
            this.sfullnumber = sfullnumber;
        }

    }

}