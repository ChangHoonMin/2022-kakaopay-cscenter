package com.kakaopay.cscenter.domain.inquiry;

import com.kakaopay.cscenter.domain.BaseEntity;
import com.kakaopay.cscenter.domain.counselor.Counselor;
import com.kakaopay.cscenter.dto.inquiry.InquiryMapStruct;
import com.kakaopay.cscenter.dto.inquiry.InquiryResponseDto;
import com.kakaopay.cscenter.error.enums.InquiryErrorEnum;
import com.kakaopay.cscenter.error.enums.InquiryReplyErrorEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private Counselor counselor;

    @OneToOne
    @JoinColumn
    private InquiryReply inquiryReply;

    @Column(nullable = false, length = 20)
    private String customerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Builder
    private Inquiry(Long id, Counselor counselor, InquiryReply inquiryReply, String customerId, String title, String content) {
        this.id = id;
        this.counselor = counselor;
        this.inquiryReply = inquiryReply;
        this.customerId = customerId;
        this.title = title;
        this.content = content;
    }

    public Inquiry counselorIdValidation(Long counselorId) {
        if (this.counselor == null || !this.counselor.getId().equals(counselorId)) {
            throw InquiryReplyErrorEnum.FORBIDDEN.getException();
        }
        return this;
    }

    public Inquiry updateReply(InquiryReply inquiryReply) {
        if (this.inquiryReply != null) {
            throw InquiryReplyErrorEnum.CONFLICT.getException();
        }
        this.inquiryReply = inquiryReply;
        return this;
    }


    public Inquiry updateCounselor(Counselor counselor) {
        if (this.counselor != null) {
            throw InquiryErrorEnum.CONFLICT.getException();
        }
        this.counselor = counselor;
        return this;
    }

    @Override
    public InquiryResponseDto toDto() {
        return InquiryMapStruct.INSTANCE.toDto(this);
    }
}
