package com.elancier.healthzone.Common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private static SmsListener mListener;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))  {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;

            try {
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    String str = "";
                    String strBody="";
                    // For every SMS message received
                    for (int i=0; i < msgs.length; i++) {
                        // Convert Object array
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        // Sender's phone number
                       // str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                       // str = msgs[i].getMessageBody().toString();
                        str= msgs[i].getOriginatingAddress().toString();
                        strBody = msgs[i].getMessageBody().toString();
                    }
                     Log.e(TAG, "Address Header"+  str);
                     Log.e(TAG, "Address Body"+  strBody);
                    if(str.contains("VRASTN")){
                        Log.i("MsgCode",str);
                        String code=getVerificationCode(strBody);
                        mListener.messageReceived(code);
                    }
                }
            } catch (Exception e) {
                //Timber.d(TAG + " %s", "Exception: " + e.getMessage());
            }
        }
    }

    private String getSplitCode(String body) {
        String[] split = body.split(".");
        Log.e("Msg 0",split[0]);
        String code = split[0];
        return code;
    }

    private String getVerificationCode(String message) {
        Log.e("MSG 1",message.toString());
       // String msg=getSplitCode(message);
        String[] split = message.split("\\s+");
        Log.e("MSG 2",split.toString());
        String code = split[0];
        return code;
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}
