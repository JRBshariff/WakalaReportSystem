package com.shariff.wakalav2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ReceiveSMS extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String strbody = sb.toString();

                // apply sms filter
                if (PhoneNumberUtils.compare("+255778016802", sender)) {
                    //mark msg as rea
                    if (strbody.toLowerCase().indexOf("ulichotuma") != -1) {
                        final String refNo,trancType,amount,cName,cNumber,trans_type,total;
                        String total1;
                        trans_type = "Withdraw";
                        refNo = strbody.substring(strbody.indexOf("Na") + 4, strbody.indexOf("Kiasi"));
                        amount = strbody.substring(strbody.indexOf("Tsh") + 3, strbody.indexOf("Mteja"));
                        cName = strbody.substring(strbody.indexOf("Mteja") + 7, strbody.indexOf("namba"));
                        cNumber = strbody.substring(strbody.indexOf("namba") + 6, strbody.indexOf("salio"));
                        total1 = strbody.substring(strbody.indexOf("Ezypesa") + 12);
                        total1 = total1.substring(0, total1.indexOf("kamisheni"));
                        total = total1;
                        try {
                            final Handler ha = new Handler();
                            ha.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    String[] field = new String[6];
                                    field[0] = "refno";
                                    field[1] = "amount";
                                    field[2] = "cNumber";
                                    field[3] = "cName";
                                    field[4] = "trans_type";
                                    field[5] = "total";
                                    //Creating array for data
                                    String[] data = new String[6];
                                    data[0] = refNo;
                                    data[1] = amount;
                                    data[2] = cNumber;
                                    data[3] = cName;
                                    data[4] = trans_type;
                                    data[5] = total;

                                    PutData putData = new PutData("http://192.168.43.59/wkr/insert.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            //End ProgressBar (Set visibility to GONE)
                                            if (result.equals("Success")) {
                                                //Toast.makeText(context, "msg two Success" + refNo, Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(context, "msg two not Succcess:" + result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }, 500);
                        } catch (Exception ex) {
                            Toast.makeText(context, "" + ex, Toast.LENGTH_SHORT).show();
                        }

                    } else if (strbody.toLowerCase().indexOf("ulichopokea") != -1) {
                        final String refNo,trancType,amount,cName,cNumber,trans_type,total;
                        String total1;
                        trans_type = "Deposit";
                        refNo = strbody.substring(strbody.indexOf("Na") + 2, strbody.indexOf("kiasi"));
                        Toast.makeText(context, "msg one ID" + refNo, Toast.LENGTH_SHORT).show();
                        amount = strbody.substring(strbody.indexOf("ulichopokea") + 12, strbody.indexOf("TZS"));
                        cName = strbody.substring(strbody.indexOf("mteja") + 6, strbody.indexOf("Namba"));
                        cNumber = strbody.substring(strbody.indexOf("Namba") + 6, strbody.indexOf("salio"));
                        total1 = strbody.substring(strbody.indexOf("Ezypesa") + 8);
                        total1 = total1.substring(0, total1.indexOf("TZS"));
                        total = total1;
                        try {
                            final Handler ha = new Handler();
                            ha.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    String[] field = new String[6];
                                    field[0] = "refno";
                                    field[1] = "amount";
                                    field[2] = "cNumber";
                                    field[3] = "cName";
                                    field[4] = "trans_type";
                                    field[5] = "total";
                                    //Creating array for data
                                    String[] data = new String[6];
                                    data[0] = refNo;
                                    data[1] = amount;
                                    data[2] = cNumber;
                                    data[3] = cName;
                                    data[4] = trans_type;
                                    data[5] = total;

                                    PutData putData = new PutData("http://192.168.43.59/wkr/insert.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            //End ProgressBar (Set visibility to GONE)
                                            if (result.equals("Success")) {
                                               // Toast.makeText(context," msg one Success" + refNo, Toast.LENGTH_SHORT).show();
                                            } else {
                                               // Toast.makeText(context, "msg one not Succcess:" + result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }, 500);
                        } catch (Exception ex) {
                            Toast.makeText(context, "" + ex, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        }
    }


}
