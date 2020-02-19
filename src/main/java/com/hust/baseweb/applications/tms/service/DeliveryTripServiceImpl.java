package com.hust.baseweb.applications.tms.service;

import com.hust.baseweb.applications.tms.entity.DeliveryTrip;
import com.hust.baseweb.applications.tms.model.createdeliverytrip.CreateDeliveryTripInputModel;
import com.hust.baseweb.applications.tms.repo.DeliveryPlanRepo;
import com.hust.baseweb.applications.tms.repo.DeliveryTripRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryTripServiceImpl implements DeliveryTripService {

    private DeliveryTripRepo deliveryTripRepo;
    private DeliveryPlanRepo deliveryPlanRepo;

    @Override
    @Transactional
    public DeliveryTrip save(CreateDeliveryTripInputModel input) {
        DeliveryTrip deliveryTrip = new DeliveryTrip();
        deliveryTrip.setDeliveryPlan(deliveryPlanRepo.findByDeliveryPlanId(input.getDeliveryPlanId()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date executeDate = null;
        try {
            executeDate = formatter.parse(input.getExecuteDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        deliveryTrip.setExecuteDate(executeDate);

        deliveryTrip = deliveryTripRepo.save(deliveryTrip);
        return deliveryTrip;
    }
}
