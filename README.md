# TaskBoard Backend (Ktor)

Backend simple para la app TaskBoard. Sigue el flujo:

```
Ruta (HTTP) -> Service -> Repository -> Tabla (Neon/PostgreSQL)
```

## 1. Crear la base de datos en Neon

1. Entra a https://neon.tech y crea una cuenta / proyecto.
2. Crea una base de datos (por ejemplo `taskboard`).
3. En el dashboard del proyecto, ve a **Connection Details** y copia:
   - Host
   - Nombre de la base de datos
   - Usuario
   - Contraseña

Neon te da una cadena de conexión tipo:
```
postgresql://usuario:password@ep-xxxx.us-east-2.aws.neon.tech/taskboard?sslmode=require
```

## 2. Configuración de la base de datos

Para simplificar, la conexión a Neon ya está puesta por defecto en `plugins/Databases.kt` y el JWT secret en `service/JwtService.kt`. No necesitas configurar nada más: solo dale Run.

Si en algún momento cambias de base de datos o quieres usar otras credenciales sin tocar el código, el proyecto también acepta las variables de entorno `DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD` y `JWT_SECRET` (tienen prioridad sobre los valores por defecto).

⚠️ Nota: como las credenciales de Neon quedan escritas en el código, si tu repositorio de GitHub es público, cualquiera con el link podría ver y usar tu base de datos. Si es un repo privado o solo lo revisa el profesor, no hay problema.

## 3. Correr el proyecto

Abre la carpeta en IntelliJ IDEA (con el plugin de Kotlin) y ejecuta `Application.kt`, o desde la terminal:

```bash
./gradlew run
```

El servidor queda escuchando en `http://localhost:8080`.

## 4. Endpoints disponibles

### Registro
```
POST /auth/register
{
  "name": "Juan",
  "email": "juan@correo.com",
  "password": "123456"
}
```

### Login (devuelve un JWT)
```
POST /auth/login
{
  "email": "juan@correo.com",
  "password": "123456"
}
```
Respuesta:
```
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

### Tareas (requieren el token del login)
Header obligatorio: `Authorization: Bearer <token>`

```
GET    /tasks              -> lista tus tareas
POST   /tasks               -> { "title": "Estudiar", "status": "TODO" }
PUT    /tasks/{id}          -> { "title": "Estudiar", "status": "IN_PROGRESS" }
DELETE /tasks/{id}
```

## 5. Estructura del proyecto

```
models/         -> Tablas (Exposed) y DTOs
repository/     -> Consultas SQL (habla con la base de datos)
service/        -> Reglas de negocio, hashing de contraseñas, JWT
routes/         -> Endpoints HTTP (GET, POST, PUT, DELETE)
plugins/        -> Configuración: base de datos, seguridad, serialización, routing
```

## 6. Video para el profesor

Para mostrar las tablas en Neon: entra al dashboard de tu proyecto en Neon -> pestaña **Tables** (o usa el **SQL Editor** con `SELECT * FROM users;` y `SELECT * FROM tasks;`) después de correr el backend al menos una vez (las tablas se crean automáticamente al iniciar).
