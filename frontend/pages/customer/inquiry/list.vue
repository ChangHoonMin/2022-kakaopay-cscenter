<template>
  <div>
    <h2>고객 아이디로 문의글 조회</h2>
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
