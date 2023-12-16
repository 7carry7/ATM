package com.cumt.atm;

import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.mapper.IndividualAccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class IndividualAccountTest {
    @Autowired
    private IndividualAccountMapper individualAccountMapper;
    @Test
    void testFindall(){
        List<IndividualAccount> all = individualAccountMapper.findAll();
        System.out.println(all);
        System.out.println(new Date());
    }

}
