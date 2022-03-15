<template>
  <DefaultError
    :errorCode="error.statusCode"
  />
</template>

<script>
  import DefaultError from "~/components/DefaultError";
  export default {
    name: "error",
    props: ['error'],
    components: {DefaultError}
  }
</script>

<style scoped>

</style>
