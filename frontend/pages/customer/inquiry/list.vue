<template>
  <div>
    <h2>고객 아이디로 문의글 조회</h2>
    <InquiryListTable
      :inquiryList="inquiryList"
      :readOnly="true"
    />
    <nuxt-link :to="`/customer/inquiry/register?customerId=${$route.query.id || ''}`">문의글 작성하기</nuxt-link>
  </div>
</template>

<script>
  import InquiryListTable from "~/components/InquiryListTable";
  export default {
    name: "list",
    layout: 'customer',
    components: {InquiryListTable},
    async asyncData({ $kakao, query }) {
      try {
        const { data } = await $kakao.fetch.get(`/api/v1/inquiries/customers/${query.id}`);
        return {
          inquiryList: data
        }
      } catch (e) {}
    },
  }
</script>

<style scoped>

</style>
