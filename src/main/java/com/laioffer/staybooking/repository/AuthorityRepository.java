package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <Table_name, search by Type>
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}