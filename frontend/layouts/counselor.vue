<template>
  <DefaultLayout />
</template>

<script>
  import DefaultLayout from "~/components/DefaultLayout";

  export default {
    name: "counselor",
    components: {DefaultLayout},
    middleware: ['auth'],
  }
</script>

<style scoped>

</style>
