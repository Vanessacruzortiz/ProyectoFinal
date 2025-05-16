<script setup>
import {ref, onMounted } from "vue";
import axios from "axios";

const alumnos = ref([]);
const cargarAlumnos = async () => {
  const response = await axios.get("http://localhost:8080/alumnos");
  alumnos.value = response.data;
  console.console.log(alumnos.value);
}

onMounted(cargarAlumnos);
</script>

<template>
  <div class="container">
    <button class="btn btn-primary">Botón</button>
  </div>
</template>

<style scoped></style>
