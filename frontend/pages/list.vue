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
      try {
        const { data } = await $kakao.fetch.get('/api/v1/inquiries');
        return {
          inquiryList: data
        }
      } catch (e) {}
    },
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
