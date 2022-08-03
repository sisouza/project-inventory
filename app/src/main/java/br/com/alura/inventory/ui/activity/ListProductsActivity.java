package br.com.alura.inventory.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import br.com.alura.inventory.R;
import br.com.alura.inventory.asynctask.BaseAsyncTask;
import br.com.alura.inventory.database.InventoryDatabase;
import br.com.alura.inventory.database.dao.ProductsDAO;
import br.com.alura.inventory.model.Product;
import br.com.alura.inventory.retrofit.InventoryRetrofit;
import br.com.alura.inventory.retrofit.service.ProductService;
import br.com.alura.inventory.ui.dialog.UpdateProductDialog;
import br.com.alura.inventory.ui.dialog.SaveProductDialog;
import br.com.alura.inventory.ui.recyclerview.adapter.ListProdutcsAdapter;
import retrofit2.Call;
import retrofit2.Response;

public class ListProductsActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR = "Products List";
    private ListProdutcsAdapter adapter;
    private ProductsDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        setTitle(TITLE_APPBAR);

        setProdutcsList();
        setFabAddProduct();

        InventoryDatabase db = InventoryDatabase.getInstance(this);
        dao = db.getProductDAO();

        searchProducts();
    }

    private void searchProducts() {
        ProductService service = new InventoryRetrofit().getProductService();
        Call<List<Product>> call = service.getAll();

        new BaseAsyncTask<>(() ->{
            try {
                Response<List<Product>> response = call.execute();
                List<Product>  newProducts = response.body();
                //save products internaly when there is no network to external connection
                dao.saveInternaly(newProducts);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dao.searchAll();
        }, newProducts -> {
            if (newProducts != null){
                adapter.update(newProducts);
            }else {
                Toast.makeText(this, "It was not possible to return products",
                        Toast.LENGTH_SHORT).show();
            }

        });
//        new BaseAsyncTask<>(dao::searchAll,
//                result -> adapter.update(result))
//                .execute();

    }

    private void setProdutcsList() {
        RecyclerView listProdutcs = findViewById(R.id.activity_list_products);
        adapter = new ListProdutcsAdapter(this, this::openUpdateProductForm);
        listProdutcs.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener(this::remove);
    }

    private void remove(int position,
                        Product deletedProduct) {
        new BaseAsyncTask<>(() -> {
            dao.remove(deletedProduct);
            return null;
        }, result -> adapter.remove(position))
                .execute();
    }

    private void setFabAddProduct() {
        FloatingActionButton fabAddProduct = findViewById(R.id.activity_list_products_fab_add_product);
        fabAddProduct.setOnClickListener(v -> openSaveProductForm());
    }

    private void openSaveProductForm() {
        new SaveProductDialog(this, this::save).show();
    }

    private void save(Product product) {
        new BaseAsyncTask<>(() -> {
            long id = dao.save(product);
            return dao.searchProduct(id);
        }, savedProduct ->
                adapter.add(savedProduct))
                .execute();
    }

    private void openUpdateProductForm(int posicao, Product product) {
        new UpdateProductDialog(this, product,
                updatedProduct -> update(posicao, updatedProduct))
                .show();
    }

    private void update(int position, Product product) {
        new BaseAsyncTask<>(() -> {
            dao.update(product);
            return product;
        }, updatedProduct ->
                adapter.update(position, updatedProduct))
                .execute();
    }


}
