package com.laioffer.staybooking.service;

import com.laioffer.staybooking.entity.*;
import com.laioffer.staybooking.exception.StayDeleteException;
import com.laioffer.staybooking.repository.LocationRepository;
import com.laioffer.staybooking.repository.ReservationRepository;
import com.laioffer.staybooking.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class StayService {
    private StayRepository stayRepository;
    private ImageStorageService imageStorageService;
    private LocationRepository locationRepository;
    private GeoEncodingService geoEncodingService;
    private ReservationRepository reservationRepository;

    @Autowired
    public StayService(StayRepository stayRepository, ImageStorageService imageStorageService, LocationRepository locationRepository, GeoEncodingService geoEncodingService, ReservationRepository reservationRepository) {
        this.stayRepository = stayRepository;
        this.imageStorageService = imageStorageService;
        this.locationRepository = locationRepository;
        this.geoEncodingService = geoEncodingService;
        this.reservationRepository = reservationRepository;
    }

    public Stay findByID(Long stayId) {
        return stayRepository.findById(stayId).orElse(null);
    }

    // select * from stay where user_id = username
    public List<Stay> findByHost(String username) {
        User.Builder builder = new User.Builder();
        builder.setUsername(username);
        User user = builder.build();

        return stayRepository.findByHost(user);
    }

    // 更新就一次
    public void add(Stay stay, MultipartFile[] images) {
        LocalDate date = LocalDate.now().plusDays(1);
        List<StayAvailability> availabilities = new ArrayList<>();
        for (int i = 0; i < 30; ++i) {
            availabilities.add(
                    new StayAvailability.Builder()
                            .setId(new StayAvailabilityKey(stay.getId(), date))
                            .setStay(stay)
                            .setState(StayAvailabilityState.AVAILABLE)
                            .build());
            date = date.plusDays(1);
        }
        
        stay.setAvailabilities(availabilities);


//        List<StayImage> stayImages = new ArrayList<>();
//        for (MultipartFile image: images) {
              // 串行上传
//            String url = imageStorageService.save(image); // 耗费时间
//            StayImage stayImage = new StayImage(url, stay);
//            stayImages.add(stayImage);
//        }
//        stay.setImages(stayImages);

        // 并行上传
        List<String> urls = Arrays.stream(images).parallel().map(image -> imageStorageService.save(image)).collect(Collectors.toList());

        List<StayImage> stayImages = new ArrayList<>();
        for (String url: urls) {
            stayImages.add(new StayImage(url, stay));
        }
        stay.setImages(stayImages);
        stayRepository.save(stay);

        // save location to elasticsearch
        Location location = geoEncodingService.getLatLng(stay.getId(), stay.getAddress());
        locationRepository.save(location);
    }

    public void deleteById(Long stayId) throws StayDeleteException {
        List<Reservation> reservations = reservationRepository.findByStayAndCheckoutDateAfter(new Stay.Builder().setId(stayId).build(), LocalDate.now());
        if (reservations != null && reservations.size() > 0) {
            throw new StayDeleteException("Cannot delete stay with active reservation");
        }
        stayRepository.deleteById(stayId);
    }
}
