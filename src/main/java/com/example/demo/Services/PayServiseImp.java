package com.example.demo.Services;

import com.example.demo.Converters.PayConverters;
import com.example.demo.Converters.WalletConvertrs;
import com.example.demo.DTO.PayDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Pay;
import com.example.demo.Repositories.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayServiseImp implements  PayServise{
    PayConverters payConverters = new PayConverters();
    @Autowired
    PayRepository payRepository;
    WalletConvertrs walletConvertrs = new WalletConvertrs();

    @Override
    public PayDTO findById(int id) {
        Pay pay = payRepository.findById(id);
        PayDTO payDTO = payConverters.FromPayInPayDTO(pay);
        payDTO.setTo_walletDTO(walletConvertrs.FromWalletInWalletDTO(pay.getTo()));
        payDTO.setFrom_walletDTO(walletConvertrs.FromWalletInWalletDTO(pay.getFrom()));
        return payDTO;
    }




    @Override
    public void save(PayDTO payDTO) {
        Pay pay = payConverters.FromPayDTOInPay(payDTO);
        pay.setFrom(walletConvertrs.FromWalletDTOInWallet(payDTO.getFrom_walletDTO()));
        pay.setTo(walletConvertrs.FromWalletDTOInWallet(payDTO.getTo_walletDTO()));
        System.out.println(pay);
        payRepository.save(pay);


    }

    @Override
    public List<PayDTO> findAllByFrom_wallet(WalletDTO walletDTO) {
        List<Pay> payList = payRepository.findAllByFrom(walletConvertrs.FromWalletDTOInWallet(walletDTO));
        List<PayDTO> payDTOList = new ArrayList<>();
        for (int i = 0;i<payList.size();i++){
            PayDTO payDTO = payConverters.FromPayInPayDTO(payList.get(i));
            payDTO.setFrom_walletDTO(walletDTO);
            payDTO.setTo_walletDTO(walletConvertrs.FromWalletInWalletDTO(payList.get(i).getTo()));
            payDTOList.add(payDTO);

        }
        return payDTOList;
    }

    @Override
    public List<PayDTO> findAllByTo_wallet(WalletDTO walletDTO) {
        List<Pay> payList = payRepository.findAllByTo(walletConvertrs.FromWalletDTOInWallet(walletDTO));
        List<PayDTO> payDTOList = new ArrayList<>();
        for (int i = 0;i<payList.size();i++){
            PayDTO payDTO = payConverters.FromPayInPayDTO(payList.get(i));
            payDTO.setTo_walletDTO(walletDTO);
            payDTO.setFrom_walletDTO(walletConvertrs.FromWalletInWalletDTO(payList.get(i).getFrom()));
            payDTOList.add(payDTO);

        }
        return payDTOList;
    }
}

