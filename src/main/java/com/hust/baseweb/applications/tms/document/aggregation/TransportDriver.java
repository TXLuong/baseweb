package com.hust.baseweb.applications.tms.document.aggregation;

import com.hust.baseweb.applications.tms.model.TransportReportModel;
import com.hust.baseweb.utils.Constant;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Hien Hoang (hienhoang2702@gmail.com)
 */
@Getter
@Setter
@Document
public class TransportDriver extends TransportReport<TransportDriver.Id> {

    @org.springframework.data.annotation.Id
    private Id id;

    public TransportDriver(Id id,
                           Double cost,
                           Double totalDistance,
                           Double numberTrips,
                           Double totalWeight) {
        super(cost, totalDistance, numberTrips, totalWeight);
        this.id = id;
    }

    @Override
    public TransportReportModel.DateReport toDateReport() {
        TransportReportModel.DateReport dateReport = super.toDateReport();
        dateReport.setDate(id.getDate().format(Constant.LOCAL_DATE_FORMAT));
        return dateReport;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Id {
        private UUID driverId;
        private LocalDate date;
    }
}
