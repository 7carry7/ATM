package com.cumt.atm.mapper;

import com.cumt.atm.domain.IndividualAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndividualAccountMapper {
    @Select("select * from individual_account")
//    @Results({
//            @Result(property = "cardId", column = "card_id"),
//            @Result(property = "phoneNumber", column = "phone_number")
//            // 其他映射关系
//    })

    public List<IndividualAccount> findAll();

}
