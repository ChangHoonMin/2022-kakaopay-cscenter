<template>
  <div>
    <h2>안녕하세요! 카카오페이 사전 과제 입니다.</h2>
    <nuxt-link to="/list">
      <p>전체 문의글 가져오기(테스트 확인용)</p>
    </nuxt-link>
    <nuxt-link to="/login">
      <p>상담사 - 로그인 페이지로 이동</p>
    </nuxt-link>
    <nuxt-link to="/counselor/inquiry/list">
      <p>상담사 - 미답변 문의글 리스트 페이지로 이동</p>
    </nuxt-link>
    <nuxt-link to="/customer/inquiry/register">
      <p>고객 문의 - 등록 페이지로 이동</p>
    </nuxt-link>
    <div>
      <label>
        고객 아이디 입력 : <input type="text" v-model="customerId" @keyup.enter="goCustomerInquiryListPage">
      </label>
      <button @click="goCustomerInquiryListPage">고객 아이디로 문의글 조회</button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        customerId: null
      }
    },
    methods: {
      goCustomerInquiryListPage() {
        this.customerId && this.$router.push(`/customer/inquiry/list?id=${this.customerId}`);
      }
    }
  }
</script>

<style scoped>

</style>
