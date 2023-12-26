package com.cumt.atm.controller;

import com.cumt.atm.domain.Account;
import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.domain.TransferMoney;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

@RestController
@CrossOrigin
@RequestMapping("/Individual")
public class IndividualAccountController {
    //    @Autowired
    private IndividualAccountRepository individualAccountRepository;
    private TransferMoneyRepository transferMoneyRepository;
    private AccountRepository accountRepository;
    private ObjectMapper objectMapper;
    private int i = 0; //记录密码输入次数
    private IndividualAccount thisAccount;
    private TransferMoney thisTransferMoney;

    public IndividualAccount getThisAccount() {
        return thisAccount;
    }

    @Autowired
    public IndividualAccountController(IndividualAccountRepository individualAccountRepository, TransferMoneyRepository transferMoneyRepository,
                                       AccountRepository accountRepository, ObjectMapper objectMapper) {
        this.individualAccountRepository = individualAccountRepository;
        this.transferMoneyRepository = transferMoneyRepository;
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
    }


    //    @Autowired
//    public void setIndividualAccountRepository(IndividualAccountRepository individualAccountRepository) {
//        this.individualAccountRepository = individualAccountRepository;
//    }
    // 已实现登录、注册、查询个人账户信息、修改手机号和密码、转账、存款
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody IndividualAccount individualAccount) {

        if(i > 3) return ResponseEntity.badRequest().body("账号已冻结，请联系银行工作人员解决");
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());

        if (foundAccount != null) {
//            if (foundAccount.getIsActive() == 1) return ResponseEntity.ok("账号已处于登录状态");
            // 比较密码等逻辑
            if (foundAccount.getPassword() == individualAccount.getPassword()) {
                // 登录成功状态设置为1
                thisAccount = foundAccount;
                thisAccount.setIsActive(1);
                System.out.println(thisAccount);
//                System.out.println("t");
                individualAccountRepository.save(thisAccount);
                return ResponseEntity.ok("登录成功");
                // 返回 true 或 false，表示登录成功或失败
            } else {
                i++;
                return ResponseEntity.badRequest().body("密码有误");
            }
        }

        // 用户不存在，登录失败
        System.out.println("false");
        return ResponseEntity.badRequest().body("账号信息有误");

    }

    // 模拟退卡
    @PostMapping("/exit")
    public boolean exit(/*@RequestParam("status") int i*/){

//        if(thisAccount == null || thisAccount.getIsActive() == 0){
//            return false;
//        }
        i = 0;
        thisAccount.setIsActive(0);
        individualAccountRepository.save(thisAccount);
        thisAccount = null;
        thisTransferMoney = null;
        return true;
    }

    // 存款取款的情况讨论，还要添加交易记录
//    @PostMapping("/save") // 前端检查存款信息、输入格式等
//    public boolean save(@RequestBody IndividualAccount individualAccount) {
////        // 先判断是否登录
////        if(thisAccount == null || thisAccount.getIsActive() == 0){
////            return false;
////        }
//
//        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
//        if (foundAccount == null) return false;  // 账号信息有误
//        foundAccount.setBalance(foundAccount.getBalance().add(individualAccount.getBalance()));
//        individualAccountRepository.save(foundAccount);
//        System.out.println(foundAccount);
//        TransferMoney transferMoney = new TransferMoney();
//        transferMoney.setFromAccount(individualAccount.getCardNumber());
//        transferMoney.setToAccount(individualAccount.getCardNumber());
//        transferMoney.setDescription("存款");
//        transferMoney.setAmount(individualAccount.getBalance());
//        transferMoney.setTransferDate(new Date());
//        thisTransferMoney = transferMoney;
//        transferMoneyRepository.save(transferMoney);
////        return ResponseEntity.ok("存款操作已完成");
//        return true;
//    }
//
//    @PostMapping("/withdraw")
//    public ResponseEntity<String> withdraw(@RequestBody IndividualAccount individualAccount) {
//
////        if(thisAccount == null || thisAccount.getIsActive() == 0){
////            return ResponseEntity.badRequest().body("账户异常");
////        }
//
//        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
//        if (foundAccount == null) return ResponseEntity.badRequest().body("账号信息有误");
//        if (foundAccount.getBalance().compareTo(individualAccount.getBalance()) < 0) {
//            return ResponseEntity.badRequest().body("余额不足");
//        }
//        foundAccount.setBalance(foundAccount.getBalance().subtract(individualAccount.getBalance()));
//        individualAccountRepository.save(foundAccount);
//        System.out.println(foundAccount);
//        TransferMoney transferMoney = new TransferMoney();
//        transferMoney.setFromAccount(individualAccount.getCardNumber());
//        transferMoney.setToAccount(individualAccount.getCardNumber());
//        transferMoney.setDescription("取款");
//        transferMoney.setAmount(individualAccount.getBalance());
//        transferMoney.setTransferDate(new Date());
//        thisTransferMoney = transferMoney;
//        transferMoneyRepository.save(transferMoney);
//
//        return ResponseEntity.ok("取款操作已完成");
//    }
//
//    @PostMapping("/print")
//    public ResponseEntity<TransferMoney> print(){
//        return ResponseEntity.ok(thisTransferMoney);
//    }

    @PostMapping("/signUp") //一个人在一家银行最多可以办4张卡
    public ResponseEntity<String> signUp(@RequestBody Map<String,Object> data) {
        IndividualAccount individualAccount = objectMapper.convertValue(data.get("individualAccount"),IndividualAccount.class);
        Account account = objectMapper.convertValue(data.get("account"), Account.class);
        boolean tag = true; // 之前是否已办过卡。true是，false否
        System.out.println(individualAccount);
        System.out.println(account);
        Account foundAccount = accountRepository.findByIdCard(account.getIdCard());
        try {
            if (foundAccount.getNumberOfCard() == 4)
                return ResponseEntity.badRequest().body("银行卡数量已达最大限度");
        }catch(Exception e){
            tag = false;
        }
        // 随机数生成器
        Random random = new Random();

        // StringBuilder 用于高效拼接字符串
        StringBuilder stringBuilder = new StringBuilder(18);

        for (int i = 0; i < 18; i++) {
            // 生成随机数字，范围为0-9
            int randomDigit = random.nextInt(10);
            // 将随机数字追加到字符串构建器
            stringBuilder.append(randomDigit);
        }

        String cardNum = stringBuilder.toString();
        individualAccount.setCardNumber(cardNum);
        individualAccount.setOpenDate(new Date());
//        individualAccount.setBalance(new BigDecimal(0));
        System.out.println(individualAccount);
        if (!tag){
            account.setNumberOfCard(1);
            accountRepository.save(account);
        }else {
            foundAccount.setNumberOfCard(foundAccount.getNumberOfCard() + 1);
            accountRepository.save(foundAccount);
        }

        individualAccountRepository.save(individualAccount);


//        // 当注册一个新用户时，在转帐表生成记录，无意义
//        TransferMoney transferMoney = new TransferMoney();
//        transferMoney.setFromAccount(individualAccount.getCardNumber());
//        transferMoneyRepository.save(transferMoney);

        System.out.println("注册完成");
//        System.out.println(individualAccount);
//        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
//        System.out.println(foundAccount);
        return ResponseEntity.ok("注册完成");
    }

    @PostMapping("/queryByCN")
    public IndividualAccount/*ResponseEntity<IndividualAccount>*/ query(@RequestBody IndividualAccount individualAccount) {
//        System.out.println(individualAccount);
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(foundAccount);
        return foundAccount;
    }
    @GetMapping("/queryBalance") // 接收实体和接收参数
    public ResponseEntity<String> query1(@RequestParam String cardNumber/*@RequestBody IndividualAccount individualAccount*/){
        System.out.println(cardNumber);
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(cardNumber);
        System.out.println(foundAccount);
        if (foundAccount == null) return ResponseEntity.badRequest().body("卡号有误");
        return ResponseEntity.ok(foundAccount.getBalance().toString());
    }

    @PostMapping("/modifypsw")
    public ResponseEntity<String> modifypsw(@RequestBody IndividualAccount individualAccount) {
        IndividualAccount found = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(found);
        if(found.getPassword() != individualAccount.getPassword()) return ResponseEntity.badRequest().body("旧密码错误");
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        found.setPassword(individualAccount.getPassword());
        individualAccountRepository.save(found);
        System.out.println(found);
        return ResponseEntity.ok("密码修改完成");
    }

}
