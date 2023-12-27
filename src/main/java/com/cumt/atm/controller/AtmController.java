package com.cumt.atm.controller;

import com.cumt.atm.domain.Atm;
import com.cumt.atm.domain.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/atm")
public class AtmController {
    private AtmRepository atmRepository;
    private TransferMoneyRepository transferMoneyRepository;
    private static BigDecimal balance = new BigDecimal(10000);
    @Autowired
    public AtmController(AtmRepository atmRepository, TransferMoneyRepository transferMoneyController) {
        this.atmRepository = atmRepository;
        this.transferMoneyRepository = transferMoneyController;
    }

    public static BigDecimal getBalance() {
        return balance;
    }

    public static void setBalance(BigDecimal balance) {
        AtmController.balance = balance;
    }

    @PostMapping("/queryinfo")
    public ResponseEntity<Atm> queryInfo(@RequestBody Atm atm){
        Atm found = atmRepository.findAtmById(atm.getId());

        return ResponseEntity.ok(found);
    }
    @GetMapping("/queryall")
    public ResponseEntity<List<TransferMoney>> queryAll(){
        List<TransferMoney> list = transferMoneyRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/querybalance")
    public ResponseEntity<String> queryBalance(){
        return ResponseEntity.ok(AtmController.getBalance().toString());
    }
}
