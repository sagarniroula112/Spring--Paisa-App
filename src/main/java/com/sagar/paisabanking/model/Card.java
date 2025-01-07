package com.sagar.paisabanking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="card_table")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "card_no_generator")
    @TableGenerator(
            name = "card_no_generator",
            table = "no_generator",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "card_no",
            initialValue = 12340000,
            allocationSize = 1
    )
    private long cardNo;
    private int cvv;
    private String type;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    public long getCardNo() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
