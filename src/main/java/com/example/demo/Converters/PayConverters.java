package com.example.demo.Converters;

import com.example.demo.DTO.PayDTO;
import com.example.demo.Entiti.Pay;

public class PayConverters
{
    public PayDTO FromPayInPayDTO(Pay pay){
        PayDTO pay_convert = new PayDTO();
        pay_convert.setSumma(pay.getSumma());
        pay_convert.setComment(pay.getComment());
        pay_convert.setId(pay.getId());
        return pay_convert;
    }
    public Pay FromPayDTOInPay(PayDTO pay){
        Pay pay_convert = new Pay();

        pay_convert.setSumma(pay.getSumma());
        pay_convert.setComment(pay.getComment());
        pay_convert.setId(pay.getId());
        return pay_convert;
    }
}
