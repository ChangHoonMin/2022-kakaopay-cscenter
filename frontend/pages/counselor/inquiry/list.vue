<template>
  <div>
    <h2>문의글 리스트</h2>
    <InquiryListTable
      :inquiryList="inquiryList"
    />
  </div>
</template>

<script>
  import InquiryListTable from "~/components/InquiryListTable";

  export default {
    name: "list",
    components: {InquiryListTable},
    layout: 'counselor',
    async asyncData({ $kakao }) {
      try {
        const { data } = await $kakao.fetch.get('/api/v1/inquiries/no-counselors');
        return {
          inquiryList: data
        }
      } catch (e) {}
    },
    data() {
      return {
        pollingTimeId: null,
      }
    },
    methods: {
      async updateCounselor(id) {
        const isConfirm = confirm('이 문의글의 담당자가 되시겠습니까?');
        if (isConfirm) {
          try {
            await this.$kakao.fetch.patch(`/api/v1/inquiries/${id}/counselors`, null, { isErrorHandler: true });
            await this.$router.push(`/counselor/inquiry/reply?inquiryId=${id}`);
          } catch (e) {
            if (e?.message) {
              alert(e.message);
            }
          }
        }
      },
      pageReload() {
        console.log('page reload');
        this.$nuxt.refresh();
      }
    },
    mounted() {
      this.pollingTimeId = setInterval(this.pageReload, 1000 * 10);
    },
    beforeDestroy() {
      clearInterval(this.pollingTimeId);
    }
  }
</script>

<style scoped>

</style>
