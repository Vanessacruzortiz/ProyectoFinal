import express from "express";
import cors from "cors";

const app = express();
app.use(cors());
app.use(express.json());

// Base de datos simulada en memoria
let alumnos = [
  { nombre: "Evelyn", apellido: "Santiago Victoria", matricula: "22620261", carrera: "Ing. Sistemas", telefono: "1234567890", imagen: "url_imagen1" },
  { nombre: "Nancy", apellido: "Olivera Santiago", matricula: "22620222", carrera: "Ing. Mecatrónica", telefono: "0987654321", imagen: "url_imagen2" },
  { nombre: "Vanessa", apellido: "Cruz Ortiz", matricula: "22620060", carrera: "Lic. Arquitectura", telefono: "1122334455", imagen: "url_imagen3" },
  { nombre: "Edgar Axel", apellido: "Sandoval Hernández", matricula: "22620093", carrera: "Ing. Civil", telefono: "6677889900", imagen: "url_imagen4" },
];

// GET: obtener todos los alumnos
app.get("/alumnos", (req, res) => {
  res.json(alumnos);
});

// POST: agregar un alumno nuevo
app.post("/alumnos", (req, res) => {
  const nuevoAlumno = req.body;

  // Verificar que la matrícula no exista ya
  const existe = alumnos.some(a => a.matricula === nuevoAlumno.matricula);
  if (existe) {
    return res.status(400).json({ message: "La matrícula ya existe" });
  }

  alumnos.push(nuevoAlumno);
  res.status(201).json(nuevoAlumno);
});

// PUT: editar un alumno por matrícula
app.put("/alumnos/:matricula", (req, res) => {
  const { matricula } = req.params;
  const datosActualizados = req.body;

  const index = alumnos.findIndex(a => a.matricula === matricula);
  if (index === -1) {
    return res.status(404).json({ message: "Alumno no encontrado" });
  }

  // Actualizar solo los campos que envíes
  alumnos[index] = { ...alumnos[index], ...datosActualizados };
  res.json(alumnos[index]);
});

// DELETE: eliminar un alumno por matrícula
app.delete("/alumnos/:matricula", (req, res) => {
  const { matricula } = req.params;
  const index = alumnos.findIndex(a => a.matricula === matricula);
  if (index === -1) {
    return res.status(404).json({ message: "Alumno no encontrado" });
  }

  alumnos.splice(index, 1);
  res.json({ message: "Alumno eliminado correctamente" });
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor escuchando en http://localhost:${PORT}`);
});
