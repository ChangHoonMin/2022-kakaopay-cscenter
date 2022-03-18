package com.kakaopay.cscenter.domain.inquiry;

import com.kakaopay.cscenter.domain.BaseEntity;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyMapStruct;
import com.kakaopay.cscenter.dto.inquiry.InquiryReplyResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "inquiryReply")
    private Inquiry inquiry;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    private InquiryReply(Long id, Inquiry inquiry, String content) {
        this.id = id;
        this.inquiry = inquiry;
        this.content = content;
    }

    public InquiryReply inquiry(Inquiry inquiry) {
        this.inquiry = inquiry.updateReply(this);
        return this;
    }

    public InquiryReplyResponseDto toDto() {
        return InquiryReplyMapStruct.INSTANCE.toDto(this);
    }
}
