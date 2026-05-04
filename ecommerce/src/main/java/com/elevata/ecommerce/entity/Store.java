package com.elevata.ecommerce.entity;

import com.elevata.ecommerce.enums.Country;
import com.elevata.ecommerce.enums.State;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private int storeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @Column(name = "name")
    private String name;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Column(name = "postal_code")
    private int postalCode;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "is_active")
    private boolean isActive;
}
