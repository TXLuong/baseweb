package com.hust.baseweb.applications.accounting.service;

import com.hust.baseweb.applications.accounting.document.Payment;
import com.hust.baseweb.applications.accounting.document.PaymentApplication;
import com.hust.baseweb.applications.accounting.entity.PaymentSequenceId;
import com.hust.baseweb.applications.accounting.repo.PaymentApplicationRepo;
import com.hust.baseweb.applications.accounting.repo.PaymentRepo;
import com.hust.baseweb.applications.accounting.repo.sequenceid.PaymentSequenceIdRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Hien Hoang (hienhoang2702@gmail.com)
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@org.springframework.transaction.annotation.Transactional
@javax.transaction.Transactional
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo paymentRepo;
    private PaymentSequenceIdRepo paymentSequenceIdRepo;
    private PaymentApplicationRepo paymentApplicationRepo;

    @Override
    public Payment.Model createPayment(Payment.CreateModel paymentCreateModel) {
        Payment payment = new Payment();
        payment.setFromCustomerId(UUID.fromString(paymentCreateModel.getPartyId()));
        payment.setAmount(paymentCreateModel.getAmount());
        return save(payment).toModel();
    }

    @Override
    public Payment.Model getPayment(String paymentId) {
        return paymentRepo.findById(paymentId).orElseThrow(NoSuchFieldError::new).toModel();
    }

    @Override
    public List<Payment.Model> getAllPayment() {
        return paymentRepo.findAll().stream().map(Payment::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Payment.Model> getAllByInvoiceId(String invoiceId) {
        List<PaymentApplication> paymentApplications = paymentApplicationRepo.findAllByInvoiceId(invoiceId);
        List<String> paymentIds = paymentApplications.stream()
                .map(PaymentApplication::getPaymentId)
                .distinct()
                .collect(Collectors.toList());
        List<Payment> payments = paymentRepo.findAllByPaymentIdIn(paymentIds);
        return payments.stream().map(Payment::toModel).collect(Collectors.toList());
    }

    @Override
    public Payment save(Payment payment) {
        if (payment.getPaymentId() == null) {
            PaymentSequenceId id = paymentSequenceIdRepo.save(new PaymentSequenceId());
            payment.setPaymentId(Payment.convertSequenceIdToPaymentId(id.getId()));
        }
        return paymentRepo.save(payment);
    }

    @Override
    public List<Payment> saveAll(List<Payment> payments) {
        List<Payment> newPayments = payments.stream()
                .filter(payment -> payment.getPaymentId() == null).collect(Collectors.toList());
        if (!newPayments.isEmpty()) {
            List<PaymentSequenceId> ids = paymentSequenceIdRepo.saveAll(newPayments.stream()
                    .map(payment -> new PaymentSequenceId())
                    .collect(Collectors.toList()));
            for (int i = 0; i < newPayments.size(); i++) {
                payments.get(i).setPaymentId(Payment.convertSequenceIdToPaymentId(ids.get(i).getId()));
            }
        }
        return paymentRepo.saveAll(payments);
    }
}
