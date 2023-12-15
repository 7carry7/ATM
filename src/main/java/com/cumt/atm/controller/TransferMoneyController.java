package com.cumt.atm.controller;

import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.domain.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/transfer")
public class TransferMoneyController {
    private TransferMoneyRepository transferMoneyRepository;
    private IndividualAccountRepository individualAccountRepository;
    @Autowired
    public TransferMoneyController(TransferMoneyRepository transferMoneyRepository,
                                   IndividualAccountRepository individualAccountRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
        this.individualAccountRepository = individualAccountRepository;
    }



    // 转账、查询交易记录（toAccount、fromAccount、transferDate）

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferMoney transferMoney){
        IndividualAccount foundFrom = individualAccountRepository.findByCardNumber(
                                    transferMoney.getFromAccount());
        IndividualAccount foundTo = individualAccountRepository.findByCardNumber(
                transferMoney.getToAccount()
        );
        if (foundFrom == null){
//            return ResponseEntity.ok("sdfa");
            return ResponseEntity.badRequest().body("转账账号有误");
        } else if (foundTo == null) {
            return ResponseEntity.badRequest().body("转入账号有误");
        } else if (foundFrom.getBalance().compareTo(transferMoney.getAmount())<0) {
            return ResponseEntity.badRequest().body("转账账户余额不足");
        }
        foundFrom.setBalance(foundFrom.getBalance().subtract(transferMoney.getAmount()));
        foundTo.setBalance(foundTo.getBalance().add(transferMoney.getAmount()));
        return ResponseEntity.ok("转账成功");
    }
}
//