<template>
  <div class="login_form">
    <h2>상담사 로그인</h2>
    <dl>
      <dt>아이디</dt>
      <dd>
        <label>
          <input type="text" @keyup.enter="login" v-model="counselor.username" placeholder="아이디를 입력하세요.">
          <p>{{ fieldErrorMessage.username }}</p>
        </label>
      </dd>
    </dl>
    <dl>
      <dt>비밀번호</dt>
      <dd>
        <label>
          <input type="password" @keyup.enter="login" v-model="counselor.password" placeholder="비밀번호를 입력하세요.">
          <p>{{ fieldErrorMessage.password }}</p>
        </label>
      </dd>
    </dl>
    <dl>
      <dd>
        <button @click="login">로그인</button>
      </dd>
    </dl>
  </div>
</template>

<script>
  export default {
    name: "login",
    data() {
      return {
        counselor: {
          username: null,
          password: null,
        },
        fieldErrorMessage: {
          username: null,
          password: null,
        },
      }
    },
    middleware({ $kakao, redirect }) {
      if ($kakao.cookie.get('access_token')) {
        redirect('/counselor/inquiry/list');
      }
    },
    methods: {
      async login() {
        try {
          const { data } = await this.$kakao.fetch.post('/api/v1/auth/authorize', this.counselor, { isErrorHandler: true });
          this.$kakao.cookie.set('access_token', data.accessToken, data.accessTokenExpiration);
          await this.$router.push('/counselor/inquiry/list');
        } catch (e) {
          if (e.errors) {
            e.errors?.forEach(({ field, message }) => this.fieldErrorMessage[field] = message);
          } else {
            alert(e.message);
          }
        }
      },
      goListPage() {
      }
    },
  }
</script>

<style scoped>
  .login_form {
    text-align: center;
  }
  input {
    width: 200px;
    height: 15px;
  }
  button {
    width: 200px;
  }
  dd label p {
    color: red;
  }
</style>
