package br.com.alura.inventory.ui.dialog;

import android.content.Context;

public class SaveProductDialog extends ProductFormDialog {

    private static final String TITLE = "Saving product";
    private static final String TITLE_POSITIVE_BUTTON = "Save";

    public SaveProductDialog(Context context,
                             listenerConfirmation listener) {
        super(context, TITLE, TITLE_POSITIVE_BUTTON, listener);
    }

}
