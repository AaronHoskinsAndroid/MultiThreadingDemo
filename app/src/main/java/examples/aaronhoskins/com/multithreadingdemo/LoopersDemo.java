package examples.aaronhoskins.com.multithreadingdemo;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LoopersDemo extends Thread {

    public Handler mainHandler;
    public Handler myHandler;

    public LoopersDemo(Handler mainHandler) {
        this.mainHandler = mainHandler;
        myHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // When child thread handler get message from child thread message queue.
                Log.i("CHILD_THREAD", "Receive message from main thread.");
                //Log.e -> Exception Handling(RED)   --> Log.e(<TAG> , <MESSAGE>, <EXCEPTION>);
                //Log.w -> Exception Handling(BLACK) --> Log.w(<TAG> , <MESSAGE>, <EXCEPTION>);
                Message message = new Message();
                message.what = msg.what;
                //put data in the message
                Bundle bundle = new Bundle();
                bundle.putString("key", "From Child Handler");

                message.setData(bundle);
                // Send the message back to main thread message queue use main thread message Handler.
                //myHandler.sendMessage(message);
            }
        };
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        Looper.loop();
    }
}
