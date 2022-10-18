<script setup>

const signOut = () => {
    localStorage.clear()
}

</script>
 
<template>
<button @click="signOut()">Sign Out</button>
</template>
 
<style>

</style>