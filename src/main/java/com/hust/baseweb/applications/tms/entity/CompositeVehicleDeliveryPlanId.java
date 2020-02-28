package com.hust.baseweb.applications.tms.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CompositeVehicleDeliveryPlanId implements Serializable {
    private UUID vehicleId;
    private UUID deliveryPlanId;
}
