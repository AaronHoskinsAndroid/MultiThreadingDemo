package examples.aaronhoskins.com.multithreadingdemo;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

public class ExampleRunnable implements Runnable {
    private String stringToReverse;
    Callback callback;

    public ExampleRunnable(String stringToReverse, Callback callback) {
        this.stringToReverse = stringToReverse;
        this.callback = callback;
    }

    @Override
    public void run() {
        char[] stringToReverseArray = stringToReverse.toCharArray();
        int headPointer = 0;
        int tailPointer = stringToReverseArray.length-1;

        while(headPointer <= tailPointer) {
            char temp = stringToReverseArray[headPointer];
            stringToReverseArray[headPointer] = stringToReverseArray[tailPointer];
            stringToReverseArray[tailPointer] = temp;
            headPointer++;
            tailPointer--;
        }
        String returnString = Arrays.toString(stringToReverseArray)
                .replace("[","")
                .replace(",", "")
                .replace("]", "")
                .replace(" ", "");
        EventBus.getDefault().post(new MessageEvent(returnString));
        callback.onThreadResult(returnString);

    }

    interface Callback{
        void onThreadResult(String result);
    }
}
