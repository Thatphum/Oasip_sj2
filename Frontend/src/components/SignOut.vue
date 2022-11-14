<script>
export default {
  name: 'SignOut',
  methods: {
    signOut() {
      localStorage.removeItem('my_tkn')
      this.$router.push('/')
    },
  }
}

</script>

<template>
  <button @click="signOut()">Sign Out</button>
</template>

<style></style>
