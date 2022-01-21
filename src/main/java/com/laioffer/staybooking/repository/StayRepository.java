package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.entity.Stay;
import com.laioffer.staybooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 查询Stay
@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    List<Stay> findByHost(User user);

    List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> ids, int guestNumber);

}

