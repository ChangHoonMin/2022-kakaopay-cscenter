<template>
  <div>
    <h2 v-if="errorCode === 404">페이지를 찾을 수 없어요!</h2>
    <h2 v-else>죄송합니다. 잠시 후에 다시 시도해주세요.</h2>
    <nuxt-link to="/">여기를 누르면 홈으로 갈게요!</nuxt-link>
  </div>
</template>

<script>
  export default {
    name: "DefaultError",
    props: {
      errorCode: Number
    },
  }
</script>

<style scoped>

</style>
