<template>
  <div>
    <h2>문의글 리스트 (전체 확인 테스트용)</h2>
    <InquiryListTable
      :inquiryList="inquiryList"
      :readOnly="true"
    />
  </div>
</template>

<script>
  import InquiryListTable from "~/components/InquiryListTable";

  export default {
    name: "list",
    components: {InquiryListTable},
    async asyncData({ $kakao }) {
      const { data } = await $kakao.fetch.get('/api/v1/inquiries');
      return {
        inquiryList: data
      }
    },
  }
</script>

<style scoped>

</style>
