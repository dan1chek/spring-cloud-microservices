package com.javastart.bill.service;

import com.javastart.bill.entity.Bill;
import com.javastart.bill.exeption.BillNotFoundException;
import com.javastart.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillById(Long billId){
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Unable to find bill with id: " + billId));
    }

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled){
        Bill bill = new Bill(accountId, amount, isDefault, overdraftEnabled);
        return  billRepository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId,Long accountId, BigDecimal amount, Boolean isDefault,
                           Boolean overdraftEnabled){
        Bill bill = new Bill(accountId, amount, isDefault, overdraftEnabled);
        bill.setBillId(billId);
        return billRepository.save(bill);
    }

    public Bill deleteBill(Long billId){
        Bill deletedBill = getBillById(billId);
        billRepository.deleteById(billId);
        return deletedBill;
    }
}


