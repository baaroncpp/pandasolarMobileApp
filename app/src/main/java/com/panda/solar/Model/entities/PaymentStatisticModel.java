package com.panda.solar.Model.entities;

import java.util.List;

public class PaymentStatisticModel {

    private Long dailyPayments;
    private List<KeyValueModel> weeklyPayments;
    private List<KeyValueModel> monthlyPayments;

    public Long getDailyPayments() {
        return dailyPayments;
    }

    public List<KeyValueModel> getWeeklyPayments() {
        return weeklyPayments;
    }

    public List<KeyValueModel> getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setDailyPayments(Long dailyPayments) {
        this.dailyPayments = dailyPayments;
    }

    public void setWeeklyPayments(List<KeyValueModel> weeklyPayments) {
        this.weeklyPayments = weeklyPayments;
    }

    public void setMonthlyPayments(List<KeyValueModel> monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }
}
