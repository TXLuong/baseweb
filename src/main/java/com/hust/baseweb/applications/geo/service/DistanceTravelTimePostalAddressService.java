package com.hust.baseweb.applications.geo.service;

import org.springframework.stereotype.Service;

@Service
public interface DistanceTravelTimePostalAddressService {

    int computeMissingDistance(String distanceSource, int speedTruck, int speedMotorbike, int maxElements);
}
