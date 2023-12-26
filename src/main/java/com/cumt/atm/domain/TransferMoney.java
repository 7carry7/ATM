package com.cumt.atm.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
//@Table(name = "transfer_money")
public class TransferMoney {
    private Date appointmentTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private Date transferDate;
    private String description; // 转账 or 存款 or 取款

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransferMoney{" +
//                "appointmentTime=" + appointmentTime +
//                "transferId=" + transferId +  // 数据库主键
                "fromAccount='" + fromAccount + '\'' +   // 转出
                ", toAccount='" + toAccount + '\'' +    // 转入
                ", amount=" + amount +   // 交易的金额
                ", transferDate=" + transferDate +   // 交易时间
                ", description='" + description + '\'' +  // 转账 存款 取款
                '}';
    }
}
