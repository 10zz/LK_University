package com.courseproject.mlkuniversity.main_ui_fragments.finance_fragment;

public class FinanceListItem
{
    private String operation, operationType, paymentDate;
    private int sum;
    // TODO: переменная, хранящая изображение для иконки.


    public FinanceListItem(String operation, String operationType, int sum, String paymentDate)
    {
        this.operation = operation;
        this.operationType = operationType;
        this.sum = sum;
        this.paymentDate = paymentDate;
        // TODO: переменная, хранящая изображение для иконки.
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public String getOperationType()
    {
        return operationType;
    }

    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
    }

    public int getSum()
    {
        return sum;
    }

    public void setSum(int sum)
    {
        this.sum = sum;
    }

    public String getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    // TODO: Геттер и сеттер для переменной, хранящей иконку.
}