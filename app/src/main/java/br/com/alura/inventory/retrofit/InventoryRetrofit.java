package br.com.alura.inventory.retrofit;

import br.com.alura.inventory.retrofit.service.ProductService;
import retrofit2.Retrofit;

public class InventoryRetrofit {

    private final ProductService productService;

    public InventoryRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.20.249:8080/")
                .build();
         productService = retrofit.create(ProductService.class);
    }

    public ProductService getProductService() {
        return productService;
    }

}
