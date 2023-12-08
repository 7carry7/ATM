package com.cumt.atm.domain;

import java.math.BigDecimal;
import java.util.Date;

public class IndividualAccount {
    private String cardId;
    private int password;
    private String holderName;
    private int accountType;
    private BigDecimal balance;
    private Date openDate;
    private String address;

    private String phoneNumber;
    private int isActive;
    private String email;

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardId() {
        return cardId;
    }

    public int getPassword() {
        return password;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getIsActive() {
        return isActive;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "IndividualAccount{" +
                "cardId='" + cardId + '\'' +
                ", password=" + password +
                ", holderName='" + holderName + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", openDate=" + openDate +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isActive=" + isActive +
                ", email='" + email + '\'' +
                '}';
    }
}
