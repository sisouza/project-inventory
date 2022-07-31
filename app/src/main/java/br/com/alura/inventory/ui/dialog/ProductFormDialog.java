package br.com.alura.inventory.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;

import androidx.appcompat.app.AlertDialog;
import br.com.alura.inventory.R;
import br.com.alura.inventory.model.Product;

abstract public class ProductFormDialog {

    private final String title;
    private final String positiveButtonTitle;
    private final listenerConfirmation listener;
    private final Context context;
    private static final String NEGATIVE_BUTTON_TITLE = "Cancel";
    private Product product;

    ProductFormDialog(Context context,
                      String title,
                      String positiveButtonTitle,
                      listenerConfirmation listener) {
        this.title = title;
        this.positiveButtonTitle = positiveButtonTitle;
        this.listener = listener;
        this.context = context;
    }

    ProductFormDialog(Context context,
                      String title,
                      String positiveButtonTitle,
                      listenerConfirmation listener,
                      Product product) {
        this(context, title, positiveButtonTitle, listener);
        this.product = product;
    }

    public void show() {
        @SuppressLint("InflateParams") View viewCreated = LayoutInflater.from(context)
                .inflate(R.layout.form_product, null);
        tryFillForm(viewCreated);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(viewCreated)
                .setPositiveButton(positiveButtonTitle, (dialog, which) -> {
                    EditText fieldName = getEditText(viewCreated, R.id.formulario_produto_nome);
                    EditText fieldPrice = getEditText(viewCreated, R.id.formulario_produto_preco);
                    EditText fieldQuantity = getEditText(viewCreated, R.id.formulario_produto_quantidade);
                    createProduct(fieldName, fieldPrice, fieldQuantity);
                })
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void tryFillForm(View viewCriada) {
        if (product != null) {
            TextView fieldID = viewCriada.findViewById(R.id.formulario_produto_id);
            fieldID.setText(String.valueOf(product.getId()));
            fieldID.setVisibility(View.VISIBLE);
            EditText fieldName = getEditText(viewCriada, R.id.formulario_produto_nome);
            fieldName.setText(product.getName());
            EditText fieldPrice = getEditText(viewCriada, R.id.formulario_produto_preco);
            fieldPrice.setText(product.getPrice().toString());
            EditText fieldQuantity = getEditText(viewCriada, R.id.formulario_produto_quantidade);
            fieldQuantity.setText(String.valueOf(product.getQuantity()));
        }
    }

    private void createProduct(EditText fieldName, EditText fieldPrice, EditText fieldQuantity) {
        String name = fieldName.getText().toString();
        BigDecimal price = convertPrice(fieldPrice);
        int quantity = convertQuantity(fieldQuantity);
        long id = fillID();
        Product product = new Product(id, name, price, quantity);
        listener.confirmed(product);
    }

    private long fillID() {
        if (product != null) {
            return product.getId();
        }
        return 0;
    }

    private BigDecimal convertPrice(EditText fieldPrice) {
        try {
            return new BigDecimal(fieldPrice.getText().toString());
        } catch (NumberFormatException ignored) {
            return BigDecimal.ZERO;
        }
    }

    private int convertQuantity(EditText fieldQuantity) {
        try {
            return Integer.valueOf(
                    fieldQuantity.getText().toString());
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    private EditText getEditText(View viewCreated, int idTextInputLayout) {
        TextInputLayout textInputLayout = viewCreated.findViewById(idTextInputLayout);
        return textInputLayout.getEditText();
    }

    public interface listenerConfirmation {
        void confirmed(Product product);
    }


}
