package com.laioffer.staybooking.service;

import com.laioffer.staybooking.entity.Stay;
import com.laioffer.staybooking.repository.LocationRepository;
import com.laioffer.staybooking.repository.StayAvailabilityRepository;
import com.laioffer.staybooking.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {
    private LocationRepository locationRepository;
    private StayAvailabilityRepository stayAvailabilityRepository;
    private StayRepository stayRepository;

    @Autowired
    public SearchService(LocationRepository locationRepository, StayAvailabilityRepository stayAvailabilityRepository, StayRepository stayRepository) {
        this.locationRepository = locationRepository;
        this.stayAvailabilityRepository = stayAvailabilityRepository;
        this.stayRepository = stayRepository;
    }

    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);

        long duration = Duration.between(checkinDate.atStartOfDay(), checkoutDate.atStartOfDay()).toDays();
        List<Long> filteredStayIds = stayAvailabilityRepository.findByDateBetweenAndStateIsAvailable(stayIds, checkinDate, checkoutDate, duration);
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }
}
