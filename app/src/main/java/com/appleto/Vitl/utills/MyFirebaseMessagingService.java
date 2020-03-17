package com.appleto.Vitl.utills;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appleto.Vitl.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static final String PUSH_NOTIFICATION = "pushNotification";
    private NotificationHelper notificationHelper;
    private String imageUrl = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            fcmHandleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            handleDataMessage(remoteMessage.getData());
        }
    }

    @Override
    public void onNewToken(String token) {
        System.out.println("TOKEN::"+token);
       // new PreferenceApp(getApplicationContext()).setValue(Constants.fcm_token, token);
        super.onNewToken(token);
    }

    private void handleDataMessage(Map<String, String> customData) {
        String badge   = customData.get("badge");
        String image = customData.get("image");
        String sound = customData.get("sound");
        String title = customData.get("title");
        String message = customData.get("message");

        if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);
        }else{
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("badge", badge);
            resultIntent.putExtra("image", image);
            resultIntent.putExtra("sound", sound);
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("message", message);
            boolean isImageAttached;
            if(image.equals("")){
                isImageAttached = false;
            } else {
                isImageAttached = true;
            }
            showNotificationMessage(getApplicationContext(), title, message, isImageAttached, image, resultIntent);
        }
    }

    private void fcmHandleNotification(String message) {
        if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

            NotificationHelper NotificationHelper = new NotificationHelper(getApplicationContext());
            NotificationHelper.playNotificationSound();
            //showNotificationMessage(getApplicationContext(), "", message, false, "", pushNotification);
        } /*else {
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("message", message);
            startActivity(resultIntent);
        }*/
      /*  Intent pushNotification = new Intent(PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        NotificationHelper NotificationHelper = new NotificationHelper(getApplicationContext());
        NotificationHelper.playNotificationSound();*/

       // EventBus.getDefault().post(new MessageEvent(message));
    }

    private void showNotificationMessage(Context context, String title, String message, boolean isBigNotif, String imageUrl, Intent intent) {
        notificationHelper=new NotificationHelper(context);
        notificationHelper.createNotification(title,message,isBigNotif,imageUrl,intent);

    }
}