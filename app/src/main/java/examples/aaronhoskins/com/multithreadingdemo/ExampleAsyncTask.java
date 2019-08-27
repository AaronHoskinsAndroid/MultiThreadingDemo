package examples.aaronhoskins.com.multithreadingdemo;

import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class ExampleAsyncTask extends AsyncTask<Void, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("TAG", "onPreExecute: ");
    }

    @Override
    protected String doInBackground(Void... voids) {
        String returnString = "";
        for(int i = 0 ; i < 10 ; i++) {
            returnString = returnString + i;
            publishProgress(returnString);
            try{
                Thread.sleep(1000);
            } catch (Exception e) {
                //Do Something to handle if needed
            }
        }
        return returnString;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("TAG", values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new MessageEvent(s));
    }
}
