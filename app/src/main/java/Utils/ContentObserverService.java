package Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

public class ContentObserverService  extends Service{

    private Looper serviceLooper;
    private Handler serviceHandler;

    final class ServieceHandler extends Handler{
        public ServieceHandler(Looper looper){
            super(looper);
        }
    }






    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
