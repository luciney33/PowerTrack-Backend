package com.powertrack.backend.common;

public class Constantes {

    private Constantes() {}

    // JWT
    public static final String ROL = "rol";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = 7;
    public static final String BEARER_TYPE = "Bearer";
    public static final String SHA_512 = "SHA-512";
    public static final String AES = "AES";

    // Roles
    public static final String ROLE = "ROLE_";

    // URL Paths - Security
    public static final String API = "/api/**";
    public static final String AUTH_PUBLIC_LOGIN = "/api/auth/login";
    public static final String AUTH_PUBLIC_REGISTER = "/api/auth/register";
    public static final String AUTH_PUBLIC_ACTIVAR = "/api/auth/activar";
    public static final String AUTH_PUBLIC_REFRESH = "/api/auth/refresh";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String SWAGGER_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String WEBJARS = "/webjars/**";
    public static final String PATTERN = "/**";
    public static final String STRING = "*";

    // URL Paths - Controllers
    public static final String API_AUTH = "/api/auth";
    public static final String API_ACTIVAR = "/api/auth/activar";
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";
    public static final String AUTH_LOGOUT = "/logout";
    public static final String REFRESH_ENDPOINT = "/refresh";
    public static final String API_PERFIL = "/api/perfil";
    public static final String COMPLETAR = "/completar";
    public static final String API_EJERCICIOS = "/api/ejercicios";
    public static final String API_RUTINAS = "/api/rutinas";
    public static final String RECOMENDADA = "/recomendada";
    public static final String API_REGISTROS = "/api/registros";
    public static final String API_PLANES = "/api/planes";
    public static final String RECOMENDADO = "/recomendado";
    public static final String API_COMIDAS = "/api/comidas";
    public static final String CATEGORIA_PATH = "/categoria/{categoria}";

    // HTTP Methods (CORS)
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String OPTIONS = "OPTIONS";

    // Table names
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_EJERCICIOS = "ejercicios";
    public static final String TABLE_RUTINAS = "rutinas";
    public static final String TABLE_RUTINA_EJERCICIOS = "rutina_ejercicios";
    public static final String TABLE_REGISTROS = "registros_entrenamiento";
    public static final String TABLE_REGISTROS_DETALLE = "registros_detalle";
    public static final String TABLE_PLANES = "planes_nutricionales";
    public static final String TABLE_COMIDAS = "comidas";
    public static final String TABLE_PLAN_COMIDAS = "plan_comidas";

    // Column names
    public static final String TIPO_ENTRENAMIENTO = "tipo_entrenamiento";
    public static final String IMAGEN_URL = "imagen_url";
    public static final String RUTINA_ID = "rutina_id";
    public static final String EJERCICIO_ID = "ejercicio_id";
    public static final String PLAN_ID = "plan_id";
    public static final String COMIDA_ID = "comida_id";
    public static final String REGISTRO_ID = "registro_id";
    public static final String USUARIO_ID = "usuario_id";
    public static final String EJERCICIOS = "ejercicios";
    public static final String COMIDAS = "comidas";

    // Thymeleaf templates
    public static final String TEMPLATE = "usuario-activo";
    public static final String TEMPLATE_ERROR = "error-activacion";
    public static final String EMAIL_ACTIVACION = "email-activacion";
    public static final String NOMBRE_USUARIO = "nombreUsuario";
    public static final String CODIGO_ACTIVACION = "codigoActivacion";
    public static final String MENSAJE_ERROR = "mensajeError";
    public static final String ACTIVAR = "activar";
    public static final String CODIGO = "codigo";
    public static final String UTF_8 = "UTF-8";
    public static final String ACTIVACION_DE_CUENTA = "Activación de Cuenta - PowerTrack";

    // Mensajes de error
    public static final String MSG_USERNAME_YA_EN_USO = "El nombre de usuario ya está en uso";
    public static final String MSG_EMAIL_YA_EN_USO = "El email ya está en uso";
    public static final String MSG_LOGIN_INVALID = "Credenciales inválidas";
    public static final String MSG_LOGIN_SUCCESS = "Login exitoso";
    public static final String MSG_LOGOUT_SUCCESS = "Sesión cerrada correctamente";
    public static final String MSG_TOKEN_REFRESCADO = "Token refrescado correctamente";
    public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado: ";
    public static final String CODIGO_INVALIDO = "Código de activación inválido";
    public static final String CODIGO_EXPIRADO = "El código de activación ha expirado";
    public static final String REFRESH_TOKEN_INVALIDO_O_EXPIRADO = "Refresh token inválido o expirado";
    public static final String REFRESH_TOKEN_INVALIDO = "Refresh token inválido: ";
    public static final String RUTINA_NO_ENCONTRADA = "Rutina no encontrada";
    public static final String EJERCICIO_NO_ENCONTRADO = "Ejercicio no encontrado";
    public static final String REGISTRO_NO_ENCONTRADO = "Registro no encontrado";
    public static final String REGISTRO_NO_PERTENECE = "Este registro no pertenece al usuario";
    public static final String PLAN_NO_ENCONTRADO = "Plan nutricional no encontrado";
    public static final String COMIDA_NO_ENCONTRADA = "Comida no encontrada";

    // Mensajes de log
    public static final String FALLO_AUTENTICACION = "Fallo de autenticación para usuario: {}";
    public static final String ERROR_REFRESCAR_TOKENS = "Error al refrescar tokens: {}";
    public static final String ERROR_ENVIAR_CORREO = "Error al enviar correo a: {}";
    public static final String ERROR_TOKEN_JWT = "Error al procesar el token JWT: ";

    // Swagger tags
    public static final String TAG_AUTH = "Autenticación";
    public static final String TAG_AUTH_DESC = "Registro, login y gestión de tokens JWT";
    public static final String TAG_ACTIVACION = "Activación de Cuenta";
    public static final String TAG_ACTIVACION_DESC = "Activación de cuenta de usuario por email";
    public static final String TAG_PERFIL = "Perfil";
    public static final String TAG_PERFIL_DESC = "Gestión del perfil y formulario fitness del usuario";
    public static final String TAG_EJERCICIOS = "Ejercicios";
    public static final String TAG_EJERCICIOS_DESC = "Catálogo de ejercicios disponibles";
    public static final String TAG_RUTINAS = "Rutinas";
    public static final String TAG_RUTINAS_DESC = "Rutinas de entrenamiento personalizadas";
    public static final String TAG_REGISTROS = "Registros de Entrenamiento";
    public static final String TAG_REGISTROS_DESC = "Historial de sesiones de entrenamiento";
    public static final String TAG_PLANES = "Planes Nutricionales";
    public static final String TAG_PLANES_DESC = "Planes de nutrición adaptados al objetivo";
    public static final String TAG_COMIDAS = "Comidas";
    public static final String TAG_COMIDAS_DESC = "Catálogo de alimentos y recetas";

    // HTTP status (Swagger)
    public static final String HTTP_200 = "200";
    public static final String HTTP_201 = "201";
    public static final String HTTP_400 = "400";
    public static final String HTTP_401 = "401";
    public static final String HTTP_403 = "403";
    public static final String HTTP_404 = "404";
}
