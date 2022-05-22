package com.example.demo.Services;

import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Wallet;

public interface WalletServise {
    void save(WalletDTO walletDTO);
    void delete(int id);
    WalletDTO findById(int id);
    WalletDTO findByAdres(String adres);
    WalletDTO findByUserDTO(UserDTO userDTO);

}
