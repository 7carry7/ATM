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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.*;
import java.util.function.Function;

@RestController
@CrossOrigin
@RequestMapping("/transfer")
public class TransferMoneyController {
    private TransferMoneyRepository transferMoneyRepository;
    private IndividualAccountRepository individualAccountRepository;
    private TransferMoney thisTransferMoney;

    @Autowired
    public TransferMoneyController(TransferMoneyRepository transferMoneyRepository,
                                   IndividualAccountRepository individualAccountRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
        this.individualAccountRepository = individualAccountRepository;
    }


    // 转账、查询交易记录（toAccount、fromAccount、transferDate）

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferMoney transferMoney) {
        System.out.println(transferMoney);
        IndividualAccount foundFrom = individualAccountRepository.findByCardNumber(
                transferMoney.getFromAccount());
        IndividualAccount foundTo = individualAccountRepository.findByCardNumber(
                transferMoney.getToAccount()
        );
        if (foundFrom == null) {
//            return ResponseEntity.ok("sdfa");
            return ResponseEntity.badRequest().body("转出账号有误");
        } else if (foundTo == null) {
            return ResponseEntity.badRequest().body("转入账号有误");
        } else if (foundFrom.getBalance().compareTo(transferMoney.getAmount()) < 0) {
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
        thisTransferMoney = transferMoney;
        return ResponseEntity.ok("转账成功");
    }

    @PostMapping("/save") // 前端检查存款信息、输入格式等
    public ResponseEntity<String> save(@RequestBody IndividualAccount individualAccount) {
//        // 先判断是否登录
//        if(thisAccount == null || thisAccount.getIsActive() == 0){
//            return false;
//        }

        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(
                individualAccount.getCardNumber());
        if (foundAccount == null) return ResponseEntity.badRequest().body("账号有误");  // 账号信息有误
        foundAccount.setBalance(foundAccount.getBalance().add(individualAccount.getBalance()));
        individualAccountRepository.save(foundAccount);
        System.out.println(foundAccount);
        TransferMoney transferMoney = new TransferMoney();
        transferMoney.setFromAccount(individualAccount.getCardNumber());
        transferMoney.setToAccount(individualAccount.getCardNumber());
        transferMoney.setDescription("存款");
        transferMoney.setAmount(individualAccount.getBalance());
        transferMoney.setTransferDate(new Date());
        thisTransferMoney = transferMoney;
        transferMoneyRepository.save(transferMoney);
        System.out.println("yes");
//        return ResponseEntity.ok("存款操作已完成");
        return ResponseEntity.ok("存款完成");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody IndividualAccount individualAccount/*,RedirectAttributes redirectAttributes*/) {

//        if(thisAccount == null || thisAccount.getIsActive() == 0){
//            return ResponseEntity.badRequest().body("账户异常");
//        }

        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        if (foundAccount == null) return ResponseEntity.badRequest().body("账号信息有误");
        if (foundAccount.getBalance().compareTo(individualAccount.getBalance()) < 0) {
            return ResponseEntity.badRequest().body("余额不足");
        }
        foundAccount.setBalance(foundAccount.getBalance().subtract(individualAccount.getBalance()));
        individualAccountRepository.save(foundAccount);
        System.out.println(foundAccount);
        TransferMoney transferMoney = new TransferMoney();
        transferMoney.setFromAccount(individualAccount.getCardNumber());
        transferMoney.setToAccount(individualAccount.getCardNumber());
        transferMoney.setDescription("取款");
        transferMoney.setAmount(individualAccount.getBalance());
        transferMoney.setTransferDate(new Date());
        thisTransferMoney = transferMoney;
        transferMoneyRepository.save(transferMoney);
//        // 将消息或其他数据添加到重定向属性中
//        redirectAttributes.addFlashAttribute("message", foundAccount.getBalance().toString());
//
//        // 重定向到其他 URL
//        return "redirect:http://localhost:8080/#/succwithdrawal";
        return ResponseEntity.ok("取款操作已完成");
    }

    @PostMapping("/print")
    public ResponseEntity<TransferMoney> print(){
        return ResponseEntity.ok(thisTransferMoney);
    }

//    @PostMapping("queryByAccount")
//    public ResponseEntity<List<TransferMoney>> queryByAccount(@RequestBody TransferMoney transferMoney) {
//        List<TransferMoney> list = new ArrayList<>();
//        list = transferMoneyRepository.findTransferMoneyByFromAccountOrToAccount(transferMoney.getFromAccount(), transferMoney.getFromAccount());
//        // 前端判断如果返回的是空list，则说明账号有误
//        return ResponseEntity.ok().body(list);
//    }

    @GetMapping("queryByDate")
    public ResponseEntity<List<TransferMoney>> queryByDate(@RequestParam("startDate")
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
                                                           @RequestParam("endDate")
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
                                                           @RequestParam("fromAccount") String fromAccount) {

        List<TransferMoney> list = new ArrayList<>();
        // list为按时间查找的记录
        list = transferMoneyRepository.findByTransferDate(startDate, endDate, fromAccount);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/queryByCN")
    public ResponseEntity<List<TransferMoney>> queryByDate(@RequestParam String cardNumber) {

        List<TransferMoney> list = new ArrayList<>();
        // list为按时间查找的记录
        list = transferMoneyRepository.findTransferMoneyByFromAccount(cardNumber);

        return ResponseEntity.ok().body(list);
    }
}
//