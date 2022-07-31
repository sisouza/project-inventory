package br.com.alura.inventory.asynctask;

import android.os.AsyncTask;

public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private final ExecuteListener<T> executeListener;
    private final FinishListener<T> finishListener;

    public BaseAsyncTask(ExecuteListener<T> executeListener,
                         FinishListener<T> finishListener) {
        this.executeListener = executeListener;
        this.finishListener = finishListener;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return executeListener.executes();
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        finishListener.finishes(result);
    }

    public interface ExecuteListener<T> {
        T executes();
    }

    public interface FinishListener<T> {
        void finishes(T result);
    }

}
