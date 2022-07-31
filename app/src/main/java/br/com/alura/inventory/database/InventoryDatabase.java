package br.com.alura.inventory.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.alura.inventory.database.converter.BigDecimalConverter;
import br.com.alura.inventory.database.dao.ProductsDAO;
import br.com.alura.inventory.model.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
@TypeConverters(value = {BigDecimalConverter.class})
public abstract class InventoryDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "inventory.db";

    public abstract ProductsDAO getProductDAO();

    public static InventoryDatabase getInstance(Context context) {
        return Room.databaseBuilder(
                context,
                InventoryDatabase.class,
                        DATABASE_NAME)
                .build();
    }
}
