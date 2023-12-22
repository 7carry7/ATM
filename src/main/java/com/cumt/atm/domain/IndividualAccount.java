package com.cumt.atm.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class IndividualAccount {
    @Id
    private String cardNumber;
    private int password;
    private String holderName;
    private int accountType;
    private BigDecimal balance;
    private Date openDate;
    private int isActive;
    private String idCard;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        this.openDate = formatter.format(this.openDate);

    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "IndividualAccount{" +
                "cardNumber='" + cardNumber + '\'' +
                ", password=" + password +
                ", holderName='" + holderName + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", openDate=" + openDate +
                ", isActive=" + isActive +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
