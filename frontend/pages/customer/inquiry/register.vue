<template>
  <div>
    <h2>문의글 등록</h2>
    <div>
      <p>아이디</p>
      <label>
        <input type="text" v-model="inquiryForm.customerId">
      </label>
      <span>{{ fieldErrorMessage.customerId }}</span>
    </div>
    <div>
      <p>제목</p>
      <label>
        <input style="width: 250px;" type="text" v-model="inquiryForm.title">
      </label>
      <span>{{ fieldErrorMessage.title }}</span>
    </div>
    <div>
      <p>내용 ({{ ~~(inquiryForm.content?.length) }}/2000)</p>
      <label>
        <textarea v-model="inquiryForm.content"></textarea>
      </label>
      <span>{{ fieldErrorMessage.content }}</span>
    </div>
    <button @click="save">문의 등록</button>
  </div>
</template>

<script>
  export default {
    name: "register",
    layout: 'customer',
    data() {
      return {
        inquiryForm: {
          customerId: null,
          title: null,
          content: null
        },
        fieldErrorMessage: {
          customerId: null,
          title: null,
          content: null
        },
      }
    },
    methods: {
      async save() {
        try {
          const { data } = await this.$kakao.fetch.post('/api/v1/inquiries', this.inquiryForm, { isErrorHandler: true });
          alert('정상적으로 처리 되었습니다.');
          await this.$router.push(`/customer/inquiry/${data.id}`);
        } catch (e) {
          if (e.errors) {
            e.errors?.forEach(({ field, message }) => this.fieldErrorMessage[field] = message);
          } else {
            alert(e.message);
          }
        }
      },
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
  div span {
    color: red;
  }
</style>
