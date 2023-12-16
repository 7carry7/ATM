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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

@RestController
@RequestMapping("/Individual")
public class IndividualAccountController {
//    @Autowired
    private IndividualAccountRepository individualAccountRepository;
    private TransferMoneyRepository transferMoneyRepository;
    @Autowired
    public IndividualAccountController(IndividualAccountRepository individualAccountRepository, TransferMoneyRepository transferMoneyRepository) {
        this.individualAccountRepository = individualAccountRepository;
        this.transferMoneyRepository = transferMoneyRepository;
    }



    //    @Autowired
//    public void setIndividualAccountRepository(IndividualAccountRepository individualAccountRepository) {
//        this.individualAccountRepository = individualAccountRepository;
//    }
    // 已实现登录、注册、查询个人账户信息、修改手机号和密码、转账、存款
    @PostMapping("/login")
    public boolean logIn(@RequestBody IndividualAccount individualAccount) {
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        if (foundAccount != null) {
            // 比较密码等逻辑
            if (foundAccount.getPassword() == individualAccount.getPassword()) {
                System.out.println("t");
                return true;
                // 返回 true 或 false，表示登录成功或失败
            }
        }

        // 用户不存在，登录失败
        System.out.println("f");
        return false;

    }
    // 存款取款的情况讨论，还要添加交易记录
    @PostMapping("/save")
    public boolean save(@RequestBody IndividualAccount individualAccount){
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        foundAccount.setBalance(foundAccount.getBalance().add(individualAccount.getBalance()));
        individualAccountRepository.save(foundAccount);
        System.out.println(foundAccount);
        TransferMoney transferMoney = new TransferMoney();
        transferMoney.setFromAccount(individualAccount.getCardNumber());
        transferMoney.setToAccount(individualAccount.getCardNumber());
        transferMoney.setDescription("存款");
        transferMoney.setAmount(individualAccount.getBalance());
        transferMoney.setTransferDate(new Date());
        transferMoneyRepository.save(transferMoney);
        return true;
    }
    @PostMapping("/withdraw")
    public boolean withdraw(@RequestBody IndividualAccount individualAccount){
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        foundAccount.setBalance(foundAccount.getBalance().subtract(individualAccount.getBalance()));
        individualAccountRepository.save(foundAccount);
        System.out.println(foundAccount);
        TransferMoney transferMoney = new TransferMoney();
        transferMoney.setFromAccount(individualAccount.getCardNumber());
        transferMoney.setToAccount(individualAccount.getCardNumber());
        transferMoney.setDescription("取款");
        transferMoney.setAmount(individualAccount.getBalance());
        transferMoney.setTransferDate(new Date());
        transferMoneyRepository.save(transferMoney);

        return true;
    }
    @PostMapping("/signUp")
    public boolean signUp(@RequestBody IndividualAccount individualAccount){
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
        System.out.println(individualAccount);

        individualAccountRepository.save(individualAccount);

//        // 当注册一个新用户时，在转帐表生成记录，无意义
//        TransferMoney transferMoney = new TransferMoney();
//        transferMoney.setFromAccount(individualAccount.getCardNumber());
//        transferMoneyRepository.save(transferMoney);

        System.out.println("注册完成");
//        System.out.println(individualAccount);
//        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
//        System.out.println(foundAccount);
        return true;
    }

    @PostMapping("/query")
    public IndividualAccount/*ResponseEntity<IndividualAccount>*/ query(@RequestBody IndividualAccount individualAccount){
//        System.out.println(individualAccount);
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(foundAccount);
        return foundAccount;
    }

    @PostMapping("/modifypsw")
    public boolean modifypsw(@RequestBody IndividualAccount individualAccount){
        IndividualAccount found = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(found);
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        found.setPassword(individualAccount.getPassword());
        individualAccountRepository.save(found);
        System.out.println(found);
        return true;
    }
    @PostMapping("/modifypn")
    public boolean modifypn(@RequestBody IndividualAccount individualAccount){
        IndividualAccount found = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(found);
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        found.setPhoneNumber(individualAccount.getPhoneNumber());
        individualAccountRepository.save(found);
        System.out.println(found);
        return true;
    }
}
