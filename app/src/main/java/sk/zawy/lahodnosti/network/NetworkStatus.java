package sk.zawy.lahodnosti.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
    private Context context;
    private boolean connected = false;

    public NetworkStatus(Context context) {
        this.context=context;
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        setConnected( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());

        return connected;
    }

    private void setConnected(boolean connected) {
        this.connected = connected;
    }
}


