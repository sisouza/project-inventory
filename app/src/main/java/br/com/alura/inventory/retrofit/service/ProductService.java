package br.com.alura.inventory.retrofit.service;

import java.util.List;

import br.com.alura.inventory.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("product")
    Call<List<Product>> getAll();
}
