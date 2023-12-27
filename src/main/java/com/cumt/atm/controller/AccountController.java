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
    private IndividualAccountRepository individualAccountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, IndividualAccountRepository individualAccountRepository) {
        this.accountRepository = accountRepository;
        this.individualAccountRepository = individualAccountRepository;
    }



    @PostMapping("/queryInfo")
    public ResponseEntity<Account> queryInfo(@RequestBody Account account){
        Account found = accountRepository.findByIdCard(account.getIdCard());
        return ResponseEntity.ok(found);
    }

    @PostMapping("/modifypn")
    public boolean modifypn(@RequestBody IndividualAccount individualAccount) {
        IndividualAccount found = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        Account account = accountRepository.findByIdCard(individualAccount.getIdCard());
        System.out.println(account);
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        account.setPhoneNumber(individualAccount.getHolderName());
        accountRepository.save(account);
        System.out.println(account);
        return true;
    }
    @PostMapping("/querypn")
    public boolean querypn(@RequestBody IndividualAccount individualAccount){
        IndividualAccount found = individualAccountRepository.findByCardNumber(individualAccount.getCardNumber());
        Account account = accountRepository.findByIdCard(found.getIdCard());
        if (account.getPhoneNumber().equals(individualAccount.getHolderName())){
            return true;
        }
        return false;
    }
    @PostMapping("/modifyem")
    public boolean modifyem(@RequestBody Account account){
        Account foundAccount = accountRepository.findByIdCard(account.getIdCard());
        foundAccount.setEmail(account.getEmail());
        accountRepository.save(foundAccount);
        return true;
    }
}
