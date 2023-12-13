package com.cumt.atm.controller;

//import com.cumt.atm.domain.IndividualAccount;
//import org.springframework.stereotype.Repository;
//
//public interface individualAccountRepository {
//}
import com.cumt.atm.domain.IndividualAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualAccountRepository extends JpaRepository<IndividualAccount, String> {

    // 通过 cardNumber 查询用户
    IndividualAccount findByCardNumber(String cardNumber);

    // 其他自定义查询方法...

}
