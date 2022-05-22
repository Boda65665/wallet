package com.example.demo.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayDTO {

    int summa;
    String comment;
    int id;
    WalletDTO from_walletDTO;
    WalletDTO to_walletDTO;
}
