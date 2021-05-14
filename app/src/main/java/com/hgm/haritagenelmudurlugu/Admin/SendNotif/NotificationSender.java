package com.hgm.haritagenelmudurlugu.Admin.SendNotif;

import android.provider.ContactsContract;

public class NotificationSender {
    public Data data;
    public String to;

    public NotificationSender(){

    }

    public NotificationSender(Data data, String to) {
        this.data = data;
        this.to = to;
    }
}
