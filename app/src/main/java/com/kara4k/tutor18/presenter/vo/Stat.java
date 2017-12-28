package com.kara4k.tutor18.presenter.vo;


import com.kara4k.tutor18.other.FormatUtils;

import java.math.BigDecimal;

public class Stat {

    public static final int TYPE_DAY = 1;
    public static final int TYPE_WEEK = 2;
    public static final int TYPE_MONTH = 3;

    private int type;
    private int completed;
    private int canceled;
    private double owned;
    private double paid;

    public void addCompleted() {
        completed++;
    }

    public void addCanceled() {
        canceled++;
    }

    public void addOwned(double price) {
        owned = calcTotal(owned, price);
    }

    public void addPayment(double price) {
        paid = calcTotal(paid, price);
    }

    private double calcTotal(double current, double price) {
        BigDecimal total = new BigDecimal(current);
        BigDecimal payment = new BigDecimal(price);
        total = total.add(payment);
        return total.doubleValue();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCompleted(){
        return String.valueOf(completed);
    }

    public String getCanceled(){
        return String.valueOf(canceled);
    }

    public String getOwned() {
        String value = FormatUtils.formatPrice(owned);
        return "$ " + value;
    }

    public String getPaid() {
        String value = FormatUtils.formatPrice(paid);
        return "$ " + value;
    }
}
