<template>
  <DefaultError
    :errorCode="Number($route.query.code)"
  />
</template>

<script>
  import DefaultError from "~/components/DefaultError";
  export default {
    name: "errorPage",
    components: {DefaultError}
  }
</script>

<style scoped>

</style>
