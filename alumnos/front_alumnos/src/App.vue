<script setup>
import { ref, reactive } from "vue";

const alumnos = ref([
  { id: 1, nombre: "Evelyn", apellido: "Santiago Victoria", carrera: "sistemas", telefono: "Sin teléfono", matricula: "22620261", imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" },
  { id: 2, nombre: "Nancy", apellido: "Olivera Santiago", carrera: "sistemas", telefono: "Sin teléfono", matricula: "22620222", imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" },
  { id: 3, nombre: "Vanessa", apellido: "Cruz Ortiz", carrera: "sistemas", telefono: "Sin teléfono", matricula: "22620060", imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" },
  { id: 4, nombre: "Edgar Axel", apellido: "Sandoval Hernández", carrera: "sistemas", telefono: "Sin teléfono", matricula: "22620093", imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" }
]);

const nuevoAlumno = reactive({
  id: null,
  nombre: "",
  apellido: "",
  carrera: "",
  telefono: "",
  matricula: "",
  imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" // URL por defecto
});

const modoEdicion = ref(false);
const mensajeExito = ref("");
const mensajeError = ref("");

const mostrarMensaje = (texto) => {
  mensajeExito.value = texto;
  mensajeError.value = "";
  setTimeout(() => {
    mensajeExito.value = "";
  }, 2000);
};

const mostrarError = (texto) => {
  mensajeError.value = texto;
  mensajeExito.value = "";
  setTimeout(() => {
    mensajeError.value = "";
  }, 3000);
};

const hayDuplicado = () => {
  return alumnos.value.some(a => 
    a.nombre.toLowerCase() === nuevoAlumno.nombre.trim().toLowerCase() &&
    a.apellido.toLowerCase() === nuevoAlumno.apellido.trim().toLowerCase() &&
    a.matricula === nuevoAlumno.matricula &&
    a.id !== nuevoAlumno.id // Para que no detecte a sí mismo cuando edita
  );
};

const agregarAlumno = () => {
  if (!nuevoAlumno.nombre || !nuevoAlumno.apellido || !nuevoAlumno.matricula) {
    mostrarError("Por favor, completa nombre, apellido y matrícula.");
    return;
  }
  if (hayDuplicado()) {
    mostrarError("Ya existe un alumno con esos datos.");
    return;
  }
  if (modoEdicion.value) {
    // Editar alumno existente
    const index = alumnos.value.findIndex(a => a.id === nuevoAlumno.id);
    if (index !== -1) {
      alumnos.value[index] = { ...nuevoAlumno };
      mostrarMensaje("Alumno editado ✓");
    }
  } else {
    // Agregar nuevo alumno
    const nuevoId = alumnos.value.length ? alumnos.value[alumnos.value.length - 1].id + 1 : 1;
    alumnos.value.push({ id: nuevoId, ...nuevoAlumno });
    mostrarMensaje("Alumno agregado ✓");
  }
  // Limpiar formulario y reset URL imagen por defecto
  Object.assign(nuevoAlumno, { id: null, nombre: "", apellido: "", carrera: "", telefono: "", matricula: "", imagen: "https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg" });
  modoEdicion.value = false;
};

const editarAlumno = (alumno) => {
  Object.assign(nuevoAlumno, alumno);
  modoEdicion.value = true;
};

const eliminarAlumno = (id) => {
  alumnos.value = alumnos.value.filter(a => a.id !== id);
  mostrarMensaje("Alumno eliminado ✓");
};
</script>

<template>
  <div class="container">
    <h2>Formulario de Alumnos</h2>

    <div class="form-box">
      <form @submit.prevent="agregarAlumno">
        <div class="row">
          <div class="col">
            <label>Nombre</label>
            <input v-model="nuevoAlumno.nombre" type="text" class="form-control" />
          </div>
          <div class="col">
            <label>Apellidos</label>
            <input v-model="nuevoAlumno.apellido" type="text" class="form-control" />
          </div>
        </div>
        <div class="row">
          <div class="col">
            <label>Carrera</label>
            <input v-model="nuevoAlumno.carrera" type="text" class="form-control" />
          </div>
          <div class="col">
            <label>Teléfono</label>
            <input v-model="nuevoAlumno.telefono" type="text" class="form-control" />
          </div>
        </div>
        <div class="row">
          <div class="col">
            <label>Matrícula</label>
            <input v-model="nuevoAlumno.matricula" type="text" class="form-control" />
          </div>
          <div class="col">
            <label>Imagen URL</label>
            <input v-model="nuevoAlumno.imagen" type="text" class="form-control" />
          </div>
        </div>
        <button type="submit" class="btn btn-primary mt-3">
          {{ modoEdicion ? "Guardar Cambios" : "Agregar Alumno" }}
        </button>
      </form>
    </div>

    <!-- Mensajes -->
    <div v-if="mensajeExito" class="mensaje-exito">
      {{ mensajeExito }}
    </div>
    <div v-if="mensajeError" class="mensaje-error">
      {{ mensajeError }}
    </div>

    <h4 style="text-align: left;">Lista de Alumnos</h4>

    <div class="list-box">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Carrera</th>
            <th>Teléfono</th>
            <th>Matrícula</th>
            <th>Imagen</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="alumno in alumnos" :key="alumno.id">
            <td>{{ alumno.id }}</td>
            <td>{{ alumno.nombre }}</td>
            <td>{{ alumno.apellido }}</td>
            <td>{{ alumno.carrera }}</td>
            <td>{{ alumno.telefono }}</td>
            <td>{{ alumno.matricula }}</td>
            <td>
              <img
                :src="alumno.imagen || 'https://i.pinimg.com/736x/8b/7e/54/8b7e54cac0aae7cdaca36f237be8ffbf.jpg'"
                alt="imagen de Alumno"
                width="50"
              />
            </td>
            <td>
              <button class="btn btn-danger btn-sm" @click="eliminarAlumno(alumno.id)">Eliminar</button>
              <button class="btn btn-warning btn-sm" @click="editarAlumno(alumno)">Editar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.form-box {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
}

.list-box {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
}

.row {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
}

.col {
  flex: 1;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  font-weight: 600;
}

.mensaje-exito {
  background-color: #d4edda;
  color: #155724;
  padding: 12px;
  border-radius: 6px;
  font-weight: 600;
  margin-bottom: 15px;
  border: 1px solid #c3e6cb;
  width: fit-content;
  user-select: none;
}

.mensaje-error {
  background-color: #f8d7da;
  color: #721c24;
  padding: 12px;
  border-radius: 6px;
  font-weight: 600;
  margin-bottom: 15px;
  border: 1px solid #f5c6cb;
  width: fit-content;
  user-select: none;
}
</style>
