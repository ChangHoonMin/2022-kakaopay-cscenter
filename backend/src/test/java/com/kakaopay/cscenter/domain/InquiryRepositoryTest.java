package com.kakaopay.cscenter.domain;

import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.domain.counselor.CounselorRepository;
import com.kakaopay.cscenter.domain.inquiry.Inquiry;
import com.kakaopay.cscenter.domain.inquiry.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
public class InquiryRepositoryTest {

    @Autowired
    InquiryRepository inquiryRepository;

    @Test
    @DisplayName("Inquiry 엔티티를 저장한다.")
    void save_test() {
        // given
        final Inquiry inquiry = Inquiry.builder()
                .customerId("test1")
                .title("타이틀")
                .content("내용")
                .build();

        // when
        final Inquiry savedInquiry = inquiryRepository.save(inquiry);

        // then
        assertNotNull(savedInquiry.getId());
        assertEquals(savedInquiry.getCustomerId(), inquiry.getCustomerId());
    }

    @Test
    @DisplayName("Inquiry 엔티티를 가져온다.")
    void find_test() {
        // given
        final Long id = 1L;
        inquiryRepository.save(Inquiry.builder()
                .customerId("test1")
                .title("타이틀")
                .content("내용")
                .build());

        // when
        final Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        // then
        assertEquals(inquiry.getId(), id);
    }

    @Test
    @DisplayName("Inquiry 엔티티 전체를 가져온다.")
    void find_All_test() {
        // given
        inquiryRepository.save(Inquiry.builder()
                .customerId("test1")
                .title("타이틀")
                .content("내용")
                .build());
        inquiryRepository.save(Inquiry.builder()
                .customerId("test2")
                .title("타이틀2")
                .content("내용2")
                .build());

        // when
        final List<Inquiry> inquiryList = inquiryRepository.findAll();

        // then
        assertThat(inquiryList.size(), is(equalTo(2)));
    }
}
