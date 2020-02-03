package com.panda.solar.Model.entities;

public class ApprovalModel {

    private String approverId;
    private String itemId;
    private String description;

    public ApprovalModel(String approverId, String itemId, String description) {
        this.approverId = approverId;
        this.itemId = itemId;
        this.description = description;
    }

    public String getApproverId() {
        return approverId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }
}
