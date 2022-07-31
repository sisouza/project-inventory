package br.com.alura.inventory.ui.dialog;

import android.content.Context;

import br.com.alura.inventory.model.Product;

public class UpdateProductDialog extends ProductFormDialog {

    private static final String TITLE = "Updating product";
    private static final String TITLE_POSITIVE_BUTTON = "Edit";

    public UpdateProductDialog(Context context,
                               Product product,
                               listenerConfirmation listener) {
        super(context, TITLE, TITLE_POSITIVE_BUTTON, listener, product);
    }
}
