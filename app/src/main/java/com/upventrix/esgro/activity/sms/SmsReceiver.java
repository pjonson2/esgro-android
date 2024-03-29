package com.upventrix.esgro.activity.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();

            if (sender.equals("Anywheresms"))
            {
                System.out.println("Some SMS Received");
                String messageBody = smsMessage.getMessageBody();

                //Pass the message text to interface
                mListener.messageReceived(messageBody);

            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}