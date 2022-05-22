package com.example.demo.Services;

import com.example.demo.Converters.UserConverters;
import com.example.demo.Converters.WalletConvertrs;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Users;
import com.example.demo.Entiti.Wallet;
import com.example.demo.Repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiseImp implements WalletServise{
    WalletConvertrs walletConvertrs = new WalletConvertrs();
    UserConverters userConverters = new UserConverters();
    @Autowired
    public WalletRepository walletRepository;
    @Override
    public void save(WalletDTO walletDTO) {
        Users users = userConverters.FromUserDTOinUsers(walletDTO.getUser());
        Wallet wallet = walletConvertrs.FromWalletDTOInWallet(walletDTO);
        wallet.setUser(users);
        walletRepository.save(wallet);
    }

    @Override
    public void delete(int id) {
        walletRepository.deleteById(id);
    }

    @Override
    public WalletDTO findById(int id) {
        Wallet wallet = walletRepository.findById(id);
        WalletDTO walletDTO = walletConvertrs.FromWalletInWalletDTO(wallet);
        UserDTO userDTO = userConverters.FromUsersInUserDTO(wallet.getUser());
        walletDTO.setUser(userDTO);
        return walletDTO;
    }

    @Override
    public WalletDTO findByAdres(String adres) {
        Wallet wallet = walletRepository.findByAdres(adres);
        WalletDTO walletDTO = walletConvertrs.FromWalletInWalletDTO(wallet);
        UserDTO userDTO = userConverters.FromUsersInUserDTO(wallet.getUser());
        walletDTO.setUser(userDTO);
        return walletDTO;    }

    @Override
    public WalletDTO findByUserDTO(UserDTO userDTO) {
        Wallet wallet = walletRepository.findByUser(userConverters.FromUserDTOinUsers(userDTO));
        WalletDTO walletDTO = walletConvertrs.FromWalletInWalletDTO(wallet);
        UserDTO userDTO1 = userConverters.FromUsersInUserDTO(wallet.getUser());
        walletDTO.setUser(userDTO1);
        return walletDTO;
    }
}
