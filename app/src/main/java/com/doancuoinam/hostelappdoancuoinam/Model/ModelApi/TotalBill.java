package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

public class TotalBill {
    private Long id;
    private int electricBill;
    private String month;
    private int sum;
    private int waterBill;
    private int CostsIncurred;
    private Rent rent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(int electricBill) {
        this.electricBill = electricBill;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(int waterBill) {
        this.waterBill = waterBill;
    }

    public int getCostsIncurred() {
        return CostsIncurred;
    }

    public void setCostsIncurred(int costsIncurred) {
        CostsIncurred = costsIncurred;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
