package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "edu_trip_destination")
public class EducationalTripDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private Integer destinationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private EducationalTripRequest tripRequest;

    @Column(name = "destination_name", length = 150, nullable = false)
    private String destinationName;

    @Column(name = "location_type", nullable = false)
    private String locationType;

    @Column(name = "sequence_no", nullable = false)
    private Integer sequenceNo;

    public EducationalTripDestination(EducationalTripRequest tripRequest,
                                      String destinationName,
                                      String locationType,
                                      Integer sequenceNo) {
        this.tripRequest = tripRequest;
        this.destinationName = destinationName;
        this.locationType = locationType;
        this.sequenceNo = sequenceNo;
    }
}
