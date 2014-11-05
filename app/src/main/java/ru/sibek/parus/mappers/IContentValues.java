package ru.sibek.parus.mappers;

/**
 * Created by Developer on 04.11.2014.
 */
public interface IContentValues {
    public android.content.ContentValues[] toContentValues();

    public android.content.ContentValues[] toContentValues(String invoicsID);
}
