package com.sywwifi.syw.entity;

import java.io.Serializable;

/**
 * Created by shaowen on 2017/6/28.
 */

public class MobileTraffic implements Serializable {
    private long mobileTraffic;
    public MobileTraffic() {
    }
    public MobileTraffic(long mobileTraffic) {
        this.mobileTraffic = mobileTraffic;
    }

    public void setMobileTraffic(long mobileTraffic) {
        this.mobileTraffic = mobileTraffic;
    }

    public long getMobileTraffic() {
        return mobileTraffic;
    }
}
