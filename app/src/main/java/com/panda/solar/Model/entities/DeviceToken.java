package com.panda.solar.Model.entities;

import com.panda.solar.Model.constants.TokenTypes;
import java.util.Date;

public class DeviceToken {

    private String leasepaymentid;
    private String token;
    private int times;
    private int days;
    private TokenTypes type;
    private Date createdon;
}
