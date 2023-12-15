package com.cumt.atm.controller;

//import com.cumt.atm.domain.IndividualAccount;
//import org.springframework.stereotype.Repository;
//
//public interface individualAccountRepository {
//}
import com.cumt.atm.domain.IndividualAccount;
//import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualAccountRepository extends JpaRepository<IndividualAccount, String> {

    // 通过 cardNumber 查询用户
    IndividualAccount findByCardNumber(String cardNumber);
//    IndividualAccount findIndividualAccountByCardNumberAndPassword();

    // 其他自定义查询方法...
    // 通过 cardNumber 更新密码
//    @Modifying
//    @Query("update IndividualAccount u set u.password = :password where u.cardNumber = :cardNumber")
//    void updatePasswordByCardNumber(@Param("cardNumber") String cardNumber, @Param("password") int password);
}
