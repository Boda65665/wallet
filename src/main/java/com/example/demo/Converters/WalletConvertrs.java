package com.example.demo.Converters;

import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Wallet;

public class WalletConvertrs {
    public WalletDTO FromWalletInWalletDTO(Wallet walet){
        WalletDTO walet_convert = new WalletDTO();
        walet_convert.setBalance(walet.getBalance());
        walet_convert.setAdres(walet.getAdres());
        walet_convert.setId(walet.getId());
        return walet_convert;

    }
    public Wallet FromWalletDTOInWallet(WalletDTO walet){
        Wallet walet_convert = new Wallet();
        walet_convert.setBalance(walet.getBalance());
        walet_convert.setAdres(walet.getAdres());
        walet_convert.setId(walet.getId());
        return walet_convert;
    }
}
