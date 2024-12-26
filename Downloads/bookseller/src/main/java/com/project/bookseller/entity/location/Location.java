package com.project.bookseller.entity.location;

import com.project.bookseller.entity.address.City;
import com.project.bookseller.entity.address.Coordinates;
import com.project.bookseller.entity.enums.LocationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long location_id;
    private String locationName;
    private String detailedAddress;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City city;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('STORE','WAREHOUSE','DISTRIBUTION_CENTER', 'ONLINE_STORE')")
    private LocationType locationType;
    @Embedded
    private Coordinates coordinates;
}
