package com.cumt.atm.controller;

import com.cumt.atm.domain.Account;
import com.cumt.atm.domain.IndividualAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @PostMapping("/queryInfo")
    public ResponseEntity<Account> queryInfo(@RequestBody Account account){
        Account found = accountRepository.findByIdCard(account.getIdCard());
        return ResponseEntity.ok(found);
    }

    @PostMapping("/modifypn")
    public boolean modifypn(@RequestBody Account account) {
        Account found = accountRepository.findByIdCard(account.getIdCard());
        System.out.println(found);
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        found.setPhoneNumber(account.getPhoneNumber());
        accountRepository.save(found);
        System.out.println(found);
        return true;
    }
    @PostMapping("/modifyem")
    public boolean modifyem(@RequestBody Account account){
        Account foundAccount = accountRepository.findByIdCard(account.getIdCard());
        foundAccount.setEmail(account.getEmail());
        accountRepository.save(foundAccount);
        return true;
    }
}
