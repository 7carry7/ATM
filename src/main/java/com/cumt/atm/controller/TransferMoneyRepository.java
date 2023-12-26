package com.cumt.atm.controller;

import com.cumt.atm.domain.IndividualAccount;
import com.cumt.atm.domain.TransferMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface TransferMoneyRepository extends JpaRepository<TransferMoney, String> {
//    TransferMoney findTransferMoneyByFromAccount(String fromAccount);

    TransferMoney findTransferMoneyByToAccount(String toAccount);

    List<TransferMoney> findTransferMoneyByFromAccountOrToAccount(String fromAccount, String toAccount);
    List<TransferMoney> findTransferMoneyByFromAccount(String fromAccount);

    //    TransferMoney findByTransferDate(Date date);
    @Query("SELECT t FROM TransferMoney t WHERE t.transferDate BETWEEN :startDate AND :endDate AND t.fromAccount = :fromAccount")
    List<TransferMoney> findByTransferDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("fromAccount") String fromAccount);

}
//    TransferMoneyRepository transferMoneyRepository = new TransferMoneyRepository() {
//        @Override
//        public TransferMoney findTransferMoneyByFromAccount(String fromAccount) {
//            return null;
//        }
//
//        @Override
//        public TransferMoney findTransferMoneyByToAccount(String toAccount) {
//            return null;
//        }
//
//        @Override
//        public TransferMoney findByTransferDate(Date date) {
//            return null;
//        }
//
//        @Override
//        public List<TransferMoney> findAll() {
//            return null;
//        }
//
//        @Override
//        public List<TransferMoney> findAll(Sort sort) {
//            return null;
//        }
//
//        @Override
//        public List<TransferMoney> findAllById(Iterable<String> strings) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> List<S> saveAll(Iterable<S> entities) {
//            return null;
//        }
//
//        @Override
//        public void flush() {
//
//        }
//
//        @Override
//        public <S extends TransferMoney> S saveAndFlush(S entity) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> List<S> saveAllAndFlush(Iterable<S> entities) {
//            return null;
//        }
//
//        @Override
//        public void deleteAllInBatch(Iterable<TransferMoney> entities) {
//
//        }
//
//        @Override
//        public void deleteAllByIdInBatch(Iterable<String> strings) {
//
//        }
//
//        @Override
//        public void deleteAllInBatch() {
//
//        }
//
//        @Override
//        public TransferMoney getOne(String s) {
//            return null;
//        }
//
//        @Override
//        public TransferMoney getById(String s) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> List<S> findAll(Example<S> example) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> List<S> findAll(Example<S> example, Sort sort) {
//            return null;
//        }
//
//        @Override
//        public Page<TransferMoney> findAll(Pageable pageable) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> S save(S entity) {
//            return null;
//        }
//
//        @Override
//        public Optional<TransferMoney> findById(String s) {
//            return Optional.empty();
//        }
//
//        @Override
//        public boolean existsById(String s) {
//            return false;
//        }
//
//        @Override
//        public long count() {
//            return 0;
//        }
//
//        @Override
//        public void deleteById(String s) {
//
//        }
//
//        @Override
//        public void delete(TransferMoney entity) {
//
//        }
//
//        @Override
//        public void deleteAllById(Iterable<? extends String> strings) {
//
//        }
//
//        @Override
//        public void deleteAll(Iterable<? extends TransferMoney> entities) {
//
//        }
//
//        @Override
//        public void deleteAll() {
//
//        }
//
//        @Override
//        public <S extends TransferMoney> Optional<S> findOne(Example<S> example) {
//            return Optional.empty();
//        }
//
//        @Override
//        public <S extends TransferMoney> Page<S> findAll(Example<S> example, Pageable pageable) {
//            return null;
//        }
//
//        @Override
//        public <S extends TransferMoney> long count(Example<S> example) {
//            return 0;
//        }
//
//        @Override
//        public <S extends TransferMoney> boolean exists(Example<S> example) {
//            return false;
//        }
//
//        @Override
//        public <S extends TransferMoney, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//            return null;
//        }
//    }