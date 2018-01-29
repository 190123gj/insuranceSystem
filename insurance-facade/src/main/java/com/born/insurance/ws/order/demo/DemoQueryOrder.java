package com.born.insurance.ws.order.demo;

import com.born.insurance.ws.base.QueryPermissionPageBase;

/**
 * Created by Administrator on 2016-11-18.
 */
public class DemoQueryOrder  extends QueryPermissionPageBase {
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
