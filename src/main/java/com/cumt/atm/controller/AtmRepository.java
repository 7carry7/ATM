package com.cumt.atm.controller;

import com.cumt.atm.domain.Account;
import com.cumt.atm.domain.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, String> {
    public Atm findAtmById(String id);
}
