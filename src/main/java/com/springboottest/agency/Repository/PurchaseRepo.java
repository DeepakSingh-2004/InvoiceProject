package com.springboottest.agency.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboottest.agency.Entity.PurchaseUser;

@Repository
public interface PurchaseRepo extends JpaRepository<PurchaseUser, Long > {

}
