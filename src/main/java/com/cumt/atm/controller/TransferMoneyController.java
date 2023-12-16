package com.cumt.atm.controller;

import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.domain.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
            return ResponseEntity.badRequest().body("转出账号有误");
        } else if (foundTo == null) {
            return ResponseEntity.badRequest().body("转入账号有误");
        } else if (foundFrom.getBalance().compareTo(transferMoney.getAmount())<0) {
            return ResponseEntity.badRequest().body("转账账户余额不足");
        }
        // 待完善：预约时间的引入
        foundFrom.setBalance(foundFrom.getBalance().subtract(transferMoney.getAmount()));
        foundTo.setBalance(foundTo.getBalance().add(transferMoney.getAmount()));
        individualAccountRepository.save(foundFrom);
        individualAccountRepository.save(foundTo);
        transferMoney.setTransferDate(new Date());
        transferMoney.setDescription("转账");
        transferMoneyRepository.save(transferMoney);
        System.out.println(transferMoney);
        return ResponseEntity.ok("转账成功");
    }

    @PostMapping("queryByAccount")
    public ResponseEntity<List<TransferMoney>> queryByAccount(@RequestBody TransferMoney transferMoney){
        List<TransferMoney> list = new ArrayList<>();
        list = transferMoneyRepository.findTransferMoneyByFromAccountOrToAccount(transferMoney.getFromAccount(),transferMoney.getFromAccount());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("queryByDate")
    public ResponseEntity<List<TransferMoney>> queryByDate(@RequestParam("startDate")
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startDate,
                                                           @RequestParam("endDate")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endDate,
                                                           @RequestParam("fromAccount") String fromAccount){

        List<TransferMoney> list = new ArrayList<>();
        // list为按时间查找的记录
        list = transferMoneyRepository.findByTransferDate(startDate, endDate, fromAccount);

        return ResponseEntity.ok().body(list);
    }
}
//