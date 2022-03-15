<template>
  <div>
    <h2>답변 등록</h2>
    <div>
      <p>제목 :</p>
      <span v-html="inquiry.title"></span>
    </div>
    <div>
      <p>내용 :</p>
      <span v-html="inquiry.content"></span>
    </div>
    <div>
      <p>답변 내용 :</p>
      <label v-if="!isReply">
        <textarea v-model="replyForm.content"></textarea>
      </label>
      <span v-else v-html="reply.content"></span>
      <span>{{ fieldErrorMessage.content }}</span>
    </div>
    <button v-if="!isReply" @click="save">답변 등록</button>
  </div>
</template>

<script>
  export default {
    name: "reply",
    layout: 'counselor',
    async asyncData({ $kakao, query }) {
      try {
        const { data } = await $kakao.fetch.get(`/api/v1/inquiries/${query.inquiryId}`);
        return {
          inquiry: data,
        }
      } catch (e) {}
    },
    data() {
      return {
        replyForm: {
          content: null,
        },
        fieldErrorMessage: {
          content: null,
        }
      }
    },
    computed: {
      isReply() {
        return !!this.reply;
      },
      reply() {
        return this.inquiry.reply;
      }
    },
    methods: {
      async save() {
        try {
          await this.$kakao.fetch.post(`/api/v1/inquiries/replies`, {
            ...this.replyForm,
            inquiryId: this.inquiry.id
          }, { isErrorHandler: true });
          alert('정상적으로 처리되었습니다.');
          await this.$router.push('/counselor/inquiry/list');
        } catch (e) {
          if (e.errors) {
            e.errors?.forEach(({ field, message }) => this.fieldErrorMessage[field] = message);
          } else {
            alert(e.message);
          }
        }
      }
    }
  }
</script>

<style scoped>
  textarea {
    width: 500px;
    height: 500px;
  }
  button {
    width: 100px;
  }
  p {
    font-size: 14px;
  }
</style>
