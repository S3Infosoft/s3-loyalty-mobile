package com.s3infosoft.loyaltyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.s3infosoft.loyaltyapp.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "loyapp";
    private static final String TABLE_NAME = "cart";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_NAME + "( item_name TEXT, item_id TEXT PRIMARY KEY, item_desc TEXT, item_logo_url TEXT, quantity INTEGER, amount INTEGER)";
        sqLiteDatabase.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addItem(CartItem item)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_name", item.getItem_name());
        values.put("item_id", item.getItem_id());
        values.put("item_desc", item.getItem_desc());
        values.put("item_logo_url", item.getItem_logo_url());
        values.put("quantity", item.getQuantity());
        values.put("amount", item.getAmount());

        long i = db.insert(TABLE_NAME, null, values);
        db.close();

        if (i>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<CartItem> getAllCartItem()
    {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                CartItem cartItem = new CartItem(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }

        db.close();
        return cartItems;
    }

    public boolean removeItem(String id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "item_id" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return true;
    }
}
