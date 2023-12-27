package com.cumt.atm.controller;

import com.cumt.atm.domain.Account;
import com.cumt.atm.domain.Administrators;
import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.domain.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/adc")
public class AdministratorsController {
    private AdministratorsRepository administratorsRepository;
    private TransferMoneyRepository transferMoneyRepository;
    private Administrators thisAccount;

    @Autowired
    public AdministratorsController(AdministratorsRepository administratorsRepository, TransferMoneyRepository transferMoneyRepository) {
        this.administratorsRepository = administratorsRepository;
        this.transferMoneyRepository = transferMoneyRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Administrators administrators){
        Administrators found = administratorsRepository.findAdministratorsByUserId(administrators.getUserId());

        if (found != null) {
//            if (foundAccount.getIsActive() == 1) return ResponseEntity.ok("账号已处于登录状态");
            // 比较密码等逻辑
            if (found.getPassword() == administrators.getPassword()) {
                // 登录成功状态设置为1
                thisAccount = found;
                thisAccount.setActive(1);
                System.out.println(thisAccount);
//                System.out.println("t");
                administratorsRepository.save(thisAccount);
                return ResponseEntity.ok("登录成功");
                // 返回 true 或 false，表示登录成功或失败
            } else {
//                return ResponseEntity.badRequest().body("密码有误");
                return ResponseEntity.ok("密码有误");
            }
        }

        // 用户不存在，登录失败
        System.out.println("false");
        return ResponseEntity.ok("账号信息有误");
    }

    @PostMapping("/queryinfo")
    public ResponseEntity<Administrators> queryInfo(@RequestBody Administrators administrators){
        Administrators found = administratorsRepository.findAdministratorsByUserId(administrators.getUserId());

        return ResponseEntity.ok(found);
    }
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
    @PostMapping("/modifypsw")
    public ResponseEntity<String> modifypsw(@RequestBody Administrators administrators) {
        Administrators found = administratorsRepository.findAdministratorsByUserId(administrators.getUserId());
        System.out.println(found);
//        if(found.getPassword() != individualAccount.getPassword()) return ResponseEntity.badRequest().body("旧密码错误");
//        individualAccountRepository.updatePasswordByCardNumber(individualAccount.getCardNumber()
//                ,individualAccount.getPassword());
        found.setPassword(administrators.getPassword());
        administratorsRepository.save(found);
        System.out.println(found);
        return ResponseEntity.ok("密码修改完成");
    }
    @PostMapping("/modifypn")
    public ResponseEntity<String> modifypn(@RequestBody Administrators administrators) {
        Administrators found = administratorsRepository.findAdministratorsByUserId(administrators.getUserId());

        found.setPhoneNumber(administrators.getPhoneNumber());
        administratorsRepository.save(found);
        System.out.println(found);
        return ResponseEntity.ok("手机号修改完成");
    }
}
