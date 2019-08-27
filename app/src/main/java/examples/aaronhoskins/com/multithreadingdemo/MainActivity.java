package examples.aaronhoskins.com.multithreadingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements ExampleRunnable.Callback{
    TextView tvRunnableDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRunnableDisplay = findViewById(R.id.tvRunnableDisplay);


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ExampleRunnable exampleRunnable = new ExampleRunnable("Hello", this);
//        Thread thread = new Thread(exampleRunnable);
//        thread.start();
//        ExampleAsyncTask exampleAsyncTask = new ExampleAsyncTask();
//        exampleAsyncTask.execute();
        looperExecution();

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onThreadResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvRunnableDisplay.setText(result);
            }
        });

    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final MessageEvent event) {
        Log.d("TAG_EVENT", event.getMessage());
        tvRunnableDisplay.setText(event.getMessage());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, event.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, 5000);
    }

    public void looperExecution() {
        LoopersDemo loopersDemo = new LoopersDemo(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                tvRunnableDisplay.setText(bundle.getString("key", "NO VALUE PASSED"));
            }
        });

        loopersDemo.start();
        loopersDemo.myHandler.sendMessage(new Message());
    }
}
