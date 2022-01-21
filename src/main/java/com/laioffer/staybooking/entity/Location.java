package com.laioffer.staybooking.entity;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;

// 加名字
@Document(indexName = "loc")
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(type = FieldType.Long)
    private long id;

    @GeoPointField
    private GeoPoint geoPoint;

    public Location(long id, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
    }

    public long getId() {
        return id;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
