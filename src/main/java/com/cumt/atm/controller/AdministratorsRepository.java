package com.cumt.atm.controller;

import com.cumt.atm.domain.Account;
import com.cumt.atm.domain.Administrators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends JpaRepository<Administrators, String> {
    public Administrators findAdministratorsByUserId(String userId);
}
