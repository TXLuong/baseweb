package com.hust.baseweb.applications.tms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Shipment {
    @Id
    @Column(name = "shipment_id")
    private UUID shipmentId;

    @Column(name = "shipment_type_id")
    private String shipmentTypeId;

    @JoinColumn(name = "shipment_id", referencedColumnName = "shipment_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<ShipmentItem> shipmentItems;


}
