package br.com.alura.inventory.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.com.alura.inventory.model.Product;

@Dao
public interface ProductsDAO {

    @Insert
    long save(Product product);

    @Update
    void update(Product product);

    @Query("SELECT * FROM Product")
    List<Product> searchAll();

    @Query("SELECT * FROM Product WHERE id = :id")
    Product searchProduct(long id);

    @Delete
    void remove(Product product);
}
