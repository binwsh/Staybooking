package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.entity.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 通过距离查询LocationIds = stayIds
@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, Long>, CustomLocationRepository  {
}
