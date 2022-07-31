package br.com.alura.inventory.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.inventory.R;
import br.com.alura.inventory.model.Product;

public class ListProdutcsAdapter extends
        RecyclerView.Adapter<ListProdutcsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private OnItemClickRemoveContextMenuListener
            onItemClickRemoveContextMenuListener = (position, removedProduct) -> {
    };
    private final Context context;
    private final List<Product> products = new ArrayList<>();

    public ListProdutcsAdapter(Context context,
                               OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    public void setOnItemClickRemoveContextMenuListener(OnItemClickRemoveContextMenuListener onItemClickRemoveContextMenuListener) {
        this.onItemClickRemoveContextMenuListener = onItemClickRemoveContextMenuListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCreated = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(viewCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product produto = products.get(position);
        holder.binds(produto);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void update(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        this.notifyItemRangeInserted(0, this.products.size());
    }

    public void add(Product... products) {
        int currentSize = this.products.size();
        Collections.addAll(this.products, products);
        int newSize = this.products.size();
        notifyItemRangeInserted(currentSize, newSize);
    }

    public void update(int position, Product product) {
        products.set(position, product);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        products.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView fieldId;
        private final TextView fieldName;
        private final TextView fieldPrice;
        private final TextView fieldQuantity;
        private Product product;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldId = itemView.findViewById(R.id.produto_item_id);
            fieldName = itemView.findViewById(R.id.produto_item_nome);
            fieldPrice = itemView.findViewById(R.id.produto_item_preco);
            fieldQuantity = itemView.findViewById(R.id.produto_item_quantidade);
            setClickItem(itemView);
            setMenuContext(itemView);
        }

        private void setMenuContext(@NonNull View itemView) {
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                new MenuInflater(context).inflate(R.menu.list_products_menu, menu);
                menu.findItem(R.id.menu_lista_produtos_remove)
                        .setOnMenuItemClickListener(
                                item -> {
                                    int productPosition = getAdapterPosition();
                                    onItemClickRemoveContextMenuListener
                                            .onItemClick(productPosition, product);
                                    return true;
                                });
            });
        }

        private void setClickItem(@NonNull View itemView) {
            itemView.setOnClickListener(v -> onItemClickListener
                    .onItemClick(getAdapterPosition(), product));
        }

        void binds(Product product) {
            this.product = product;
            fieldId.setText(String.valueOf(product.getId()));
            fieldName.setText(product.getName());
            fieldPrice.setText(formatsPrice(product.getPrice()));
            fieldQuantity.setText(String.valueOf(product.getQuantity()));
        }

        private String formatsPrice(BigDecimal value) {
            NumberFormat formater = NumberFormat.getCurrencyInstance();
            return formater.format(value);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, Product product);
    }

    public interface OnItemClickRemoveContextMenuListener {
        void onItemClick(int position, Product removedProduct);
    }

}
