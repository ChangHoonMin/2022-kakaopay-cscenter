<template>
  <table>
    <thead>
      <tr>
        <th>제목</th>
        <th>등록일</th>
        <th v-if="readOnly">답변 완료 여부</th>
        <th v-else>담당자</th>
        <th>상세 페이지</th>
      </tr>
    </thead>
    <tbody>
      <tr v-if="inquiryList?.length" v-for="inquiry of inquiryList">
        <td>{{ inquiry.title }}</td>
        <td>{{ inquiry.createdDate }} </td>
        <td v-if="readOnly">
          {{ inquiry.reply ? 'O' : 'X' }}
        </td>
        <td v-else>
          <button v-if="inquiry.counselorId" disabled>담당자 지정 완료</button>
          <button v-else @click="updateCounselor(inquiry.id)">담당자로 지정</button>
        </td>
        <td>
          <nuxt-link tag="button" :to="createDetailPageUrl(inquiry.id)">Go!</nuxt-link>
        </td>
      </tr>
      <tr v-if="!inquiryList?.length">
        <td colspan="4">문의글이 없습니다.</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
  export default {
    name: "InquiryListTable",
    props: {
      inquiryList: Array,
      readOnly: {
        type: Boolean,
        default: false
      },
    },
    methods: {
      async updateCounselor(id) {
        const isConfirm = confirm('이 문의글의 담당자가 되시겠습니까?');
        if (isConfirm) {
          try {
            await this.$kakao.fetch.patch(`/api/v1/inquiries/${id}/counselors`, null, { isErrorHandler: true });
            alert('정상적으로 처리되었습니다.');
            this.$nuxt.refresh();
          } catch (e) {
            if (e?.message) {
              alert(e.message);
            }
          }
        }
      },
      createDetailPageUrl(id) {
        return this.readOnly ? `/customer/inquiry/${id}` : `/counselor/inquiry/reply?inquiryId=${id}`;
      }
    }
  }
</script>

<style scoped>
  table {
    border-collapse: separate;
    border-spacing: 1px;
    text-align: center;
    line-height: 1.5;
    margin: 20px 10px;
  }
  table th {
    width: 155px;
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    color: #ffffff;
    background: #61aeff;
  }
  table td {
    width: 155px;
    padding: 10px;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    background: #eee;
  }
</style>
