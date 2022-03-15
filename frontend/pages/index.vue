<template>
  <div>
    <h2>안녕하세요! 카카오페이 사전 과제 입니다.</h2>
    <nuxt-link to="/login">
      <p>상담사 로그인 페이지로 이동</p>
    </nuxt-link>
    <nuxt-link to="/customer/inquiry/register">
      <p>고객 문의 등록페이지로 이동</p>
    </nuxt-link>
    <nuxt-link to="/customer/inquiry/list">
      <p>전체 문의글 가져오기(확인용)</p>
    </nuxt-link>
  </div>
</template>

<script>
  export default {
    name: "index"
  }
</script>

<style scoped>

</style>
