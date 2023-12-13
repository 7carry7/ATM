package com.cumt.atm.controller;

import com.cumt.atm.domain.IndividualAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/Individual")
public class IndividualAccountController {
//    @Autowired
    private IndividualAccountRepository individualAccountRepository;
    @Autowired
    public IndividualAccountController(IndividualAccountRepository individualAccountRepository) {
        this.individualAccountRepository = individualAccountRepository;
    }
    //    @Autowired
//    public void setIndividualAccountRepository(IndividualAccountRepository individualAccountRepository) {
//        this.individualAccountRepository = individualAccountRepository;
//    }

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

        System.out.println("注册完成");
//        System.out.println(individualAccount);
//        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
//        System.out.println(foundAccount);
        return true;
    }

    @PostMapping("/query")
    public IndividualAccount query(@RequestBody IndividualAccount individualAccount){
//        System.out.println(individualAccount);
        IndividualAccount foundAccount = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        System.out.println(foundAccount);
        return foundAccount;
    }
}
