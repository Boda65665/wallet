package com.example.demo.Services;

import com.example.demo.DTO.PayDTO;
import com.example.demo.DTO.WalletDTO;

import java.util.List;

public interface PayServise {
    PayDTO findById(int id);

    void save(PayDTO payDTO);

    List<PayDTO> findAllByFrom_wallet(WalletDTO walletDTO);
    List<PayDTO> findAllByTo_wallet(WalletDTO walletDTO);
}
