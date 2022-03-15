package com.kakaopay.cscenter.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByCustomerId(String customerId);
    List<Inquiry> findByCounselorIsNull();
}