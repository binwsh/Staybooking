package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 查询User
@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    List<User> findAllByPassword(String password);  // 用反射机制 找到password关键字
}

