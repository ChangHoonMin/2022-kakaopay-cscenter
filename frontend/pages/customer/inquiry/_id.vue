<template>
  <div>
    <h2>문의글 확인</h2>
    <dl>
      <dt>제목 :</dt>
      <dd v-html="inquiry.title"></dd>
    </dl>
    <dl>
      <dt>내용 :</dt>
      <dd v-html="inquiry.content"></dd>
    </dl>
    <dl v-if="inquiry.reply">
      <dt>답변 내용 :</dt>
      <dd v-html="inquiry.reply.content"></dd>
    </dl>
  </div>
</template>

<script>
  export default {
    name: "id",
    layout: 'customer',
    async asyncData({ $kakao, params }) {
      try {
        const { data } = await $kakao.fetch.get(`/api/v1/inquiries/${params.id}`);
        return {
          inquiry: data
        }
      } catch (e) {}
    }
  }
</script>

<style scoped>
  dl dt {
    font-size: 14px;
  }
  dl dd {
    font-weight: bold;
  }
</style>
