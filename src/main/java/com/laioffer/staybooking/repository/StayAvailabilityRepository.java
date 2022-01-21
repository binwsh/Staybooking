package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.entity.StayAvailability;
import com.laioffer.staybooking.entity.StayAvailabilityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

// 查询Stay是否可住
public interface StayAvailabilityRepository extends JpaRepository<StayAvailability, StayAvailabilityKey> {
    @Query(value =
            "SELECT sa.id.stay_id " +         // sa.id saKey 组合键
            "FROM StayAvailability sa " +
            "WHERE sa.id.stay_id IN ?1 " +    // ?1 -> Ids,
            "AND sa.state = 0 " +
            "AND sa.id.date BETWEEN ?2 AND ?3 " +    // ?2 -> startDate
            "GROUP BY sa.id.stay_id " +
            "HAVING COUNT(sa.id.date) = ?4")
    List<Long> findByDateBetweenAndStateIsAvailable(List<Long> stayIds, LocalDate startDate, LocalDate endDate, long duration);

    @Query(value = "SELECT sa.id.date " +
            "FROM StayAvailability sa " +
            "WHERE sa.id.stay_id = ?1 " +
            "AND sa.state = 0 " +
            "AND sa.id.date BETWEEN ?2 AND ?3")
    List<LocalDate> countByDateBetweenAndId(Long stayId, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query(value = "UPDATE StayAvailability sa" +
            " SET sa.state = 1" +
            " WHERE sa.id.stay_id = ?1" +
            " AND sa.id.date BETWEEN ?2 AND ?3")
    void reserveByDateBetweenAndId(Long stayId, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query(value = "UPDATE StayAvailability sa " +
            "SET sa.state = 0 " +
            "WHERE sa.id.stay_id = ?1 " +
            "AND sa.id.date BETWEEN ?2 AND ?3")
    void cancelByDateBetweenAndId(Long stayId, LocalDate startDate, LocalDate endDate);

}

// select distinct stay_id
// from StayAvailability
// where stay_id in Ids
// and state = 0
// and date between startDate and endDate
// group by stay_id
// having count = duration

// [1,1,2,2,2,2,3,3,3] map
// 1 -> 2
// 2 -> 4
// 3 -> 3