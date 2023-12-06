package com.constellation.paymentservice.repository;

import com.constellation.paymentservice.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
}
