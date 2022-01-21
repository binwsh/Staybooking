package com.laioffer.staybooking.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class StayAvailabilityKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long stay_id;
    private LocalDate date;

    public StayAvailabilityKey() {}

    public StayAvailabilityKey(Long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

    public StayAvailabilityKey setStay_id(Long stay_id) {
        this.stay_id = stay_id;
        return this;
    }

    public StayAvailabilityKey setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Long getStay_id() {
        return stay_id;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayAvailabilityKey that = (StayAvailabilityKey) o;
        return stay_id.equals(that.stay_id) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }
}
