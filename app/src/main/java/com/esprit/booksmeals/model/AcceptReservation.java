package com.esprit.booksmeals.model;

import com.squareup.moshi.Json;

public class AcceptReservation {
    @Json(name = "reservation_id")
    int reservation_id;

    @Json(name = "approved")
    int approved;

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "AcceptReservation{" +
                "reservation_id=" + reservation_id +
                ", approved=" + approved +
                '}';
    }
}
