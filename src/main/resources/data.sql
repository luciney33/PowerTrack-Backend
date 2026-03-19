-- ROLES / USUARIO ADMIN
INSERT IGNORE INTO usuarios (username, password, email, nombre, rol, activo, formulario_completado)
  VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHHi', 'admin@powertrack.com', 'Administrador', 'ADMIN', true, false);

  -- EJERCICIOS
  INSERT IGNORE INTO ejercicios (nombre, tipo_entrenamiento, imagen_url, descripcion) VALUES
  ('Press de banca', 'PECHO', '', 'Ejercicio básico de empuje para pecho'),
  ('Fondos en paralelas', 'PECHO', '', 'Ejercicio de peso corporal para pecho y tríceps'),
  ('Aperturas con mancuernas', 'PECHO', '', 'Ejercicio de aislamiento para pecho'),
  ('Press inclinado', 'PECHO', '', 'Press de banca con banco inclinado'),
  ('Dominadas', 'ESPALDA', '', 'Ejercicio de peso corporal para espalda'),
  ('Remo con barra', 'ESPALDA', '', 'Ejercicio de tirón para espalda media'),
  ('Jalón al pecho', 'ESPALDA', '', 'Ejercicio de tirón en polea alta'),
  ('Remo en polea baja', 'ESPALDA', '', 'Ejercicio de tirón para espalda baja'),
  ('Sentadilla', 'PIERNAS', '', 'Ejercicio compuesto fundamental para piernas'),
  ('Prensa de piernas', 'PIERNAS', '', 'Ejercicio de empuje para cuádriceps'),
  ('Zancadas', 'PIERNAS', '', 'Ejercicio unilateral para piernas'),
  ('Curl de isquiotibiales', 'PIERNAS', '', 'Aislamiento de isquiotibiales'),
  ('Press militar', 'HOMBROS', '', 'Ejercicio básico de empuje para hombros'),
  ('Elevaciones laterales', 'HOMBROS', '', 'Aislamiento de deltoides lateral'),
  ('Pájaros', 'HOMBROS', '', 'Aislamiento de deltoides posterior'),
  ('Curl de bíceps', 'BICEPS', '', 'Ejercicio básico de aislamiento de bíceps'),
  ('Curl martillo', 'BICEPS', '', 'Curl neutro para bíceps y braquial'),
  ('Extensión de tríceps', 'TRICEPS', '', 'Aislamiento de tríceps en polea'),
  ('Press francés', 'TRICEPS', '', 'Ejercicio de aislamiento para tríceps'),
  ('Plancha', 'CORE', '', 'Ejercicio isométrico para core'),
  ('Crunch abdominal', 'CORE', '', 'Ejercicio básico de abdominales'),
  ('Cardio moderado', 'CARDIO', '', 'Sesión de cardio a intensidad moderada'),
  ('HIIT', 'CARDIO', '', 'Entrenamiento de intervalos de alta intensidad'),
  ('Movilidad articular', 'MOVILIDAD', '', 'Ejercicios de movilidad y flexibilidad');

  -- RUTINAS
  INSERT IGNORE INTO rutinas (nombre, descripcion, tipo) VALUES
  ('Volumen - Principiante', 'Rutina de volumen para principiantes con ejercicios básicos', 0),
  ('Volumen - Intermedio', 'Rutina de volumen para nivel intermedio', 1),
  ('Volumen - Avanzado', 'Rutina de volumen para nivel avanzado', 2),
  ('Definición - Principiante', 'Rutina de definición para principiantes', 3),
  ('Definición - Intermedio', 'Rutina de definición para nivel intermedio', 4),
  ('Mantenimiento', 'Rutina equilibrada de mantenimiento', 5),
  ('Pérdida de peso', 'Rutina enfocada en quema de calorías', 6),
  ('Rehabilitación', 'Rutina suave para personas con lesiones', 7);

  -- PLANES NUTRICIONALES
  INSERT IGNORE INTO planes_nutricionales (nombre, descripcion, tipo, calorias_objetivo, proteinas_objetivo, carbohidratos_objetivo, grasas_objetivo) VALUES
  ('Superávit Principiante', 'Plan hipercalórico para ganar masa muscular', 0, 3000, 180, 350, 90),
  ('Superávit Intermedio', 'Plan hipercalórico moderado', 1, 3200, 200, 370, 95),
  ('Superávit Avanzado', 'Plan hipercalórico para volumen avanzado', 2, 3500, 220, 400, 100),
  ('Déficit Principiante', 'Plan hipocalórico suave para definición', 3, 2000, 160, 200, 60),
  ('Déficit Intermedio', 'Plan hipocalórico moderado para definición', 4, 1800, 170, 180, 55),
  ('Mantenimiento', 'Plan equilibrado de mantenimiento calórico', 5, 2400, 150, 280, 75),
  ('Pérdida de Peso', 'Plan con déficit calórico para perder peso', 6, 1600, 140, 160, 50),
  ('Rehabilitación Nutricional', 'Plan suave y equilibrado para lesionados', 7, 2200, 130, 260, 70);

  -- COMIDAS
  INSERT IGNORE INTO comidas (nombre, calorias, proteinas, carbohidratos, grasas, categoria) VALUES
  ('Avena con leche', 350, 15, 55, 8, 'DESAYUNO'),
  ('Tortilla francesa', 200, 14, 2, 14, 'DESAYUNO'),
  ('Yogur griego con fruta', 250, 18, 30, 5, 'DESAYUNO'),
  ('Tostadas con aguacate', 300, 8, 35, 14, 'DESAYUNO'),
  ('Batido de proteínas', 200, 25, 15, 3, 'DESAYUNO'),
  ('Arroz con pollo', 500, 40, 55, 8, 'ALMUERZO'),
  ('Pasta con atún', 480, 35, 60, 10, 'ALMUERZO'),
  ('Ensalada de quinoa', 380, 20, 45, 12, 'ALMUERZO'),
  ('Lentejas con verduras', 420, 25, 50, 8, 'ALMUERZO'),
  ('Pechuga a la plancha con patata', 450, 45, 40, 7, 'ALMUERZO'),
  ('Salmón con verduras', 420, 38, 20, 18, 'CENA'),
  ('Merluza al vapor', 280, 35, 5, 8, 'CENA'),
  ('Tortilla de verduras', 300, 18, 15, 16, 'CENA'),
  ('Ensalada de pollo', 350, 35, 15, 14, 'CENA'),
  ('Crema de verduras', 200, 8, 25, 6, 'CENA'),
  ('Fruta de temporada', 150, 2, 35, 1, 'MERIENDA'),
  ('Frutos secos', 200, 6, 8, 16, 'MERIENDA'),
  ('Barrita de proteínas', 220, 20, 22, 7, 'MERIENDA'),
  ('Queso fresco con miel', 180, 12, 18, 5, 'MERIENDA');

-- Rutina 0: Volumen Principiante → ejercicios básicos
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(1, 1), -- Press de banca
(1, 9), -- Sentadilla
(1, 16), -- Curl de bíceps
(1, 18), -- Extensión de tríceps
(1, 13), -- Press militar
(1, 20); -- Plancha

-- Rutina 1: Volumen Intermedio
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(2, 1), (2, 2), (2, 5), (2, 6), (2, 9), (2, 10), (2, 13), (2, 16);

-- Rutina 2: Volumen Avanzado
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(3, 1), (3, 2), (3, 3), (3, 5), (3, 6), (3, 7), (3, 9), (3, 10), (3, 11), (3, 13), (3, 14);

-- Rutina 3: Definición Principiante
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(4, 22), (4, 9), (4, 20), (4, 21), (4, 16);

-- Rutina 4: Definición Intermedio (HIIT)
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(5, 23), (5, 9), (5, 11), (5, 20), (5, 21), (5, 22);

-- Rutina 5: Mantenimiento
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(6, 1), (6, 9), (6, 13), (6, 22), (6, 20), (6, 24);

-- Rutina 6: Pérdida de peso
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(7, 22), (7, 23), (7, 9), (7, 11), (7, 20), (7, 21);

-- Rutina 7: Rehabilitación
INSERT IGNORE INTO rutina_ejercicios (rutina_id, ejercicio_id) VALUES
(8, 24), (8, 20), (8, 11), (8, 21);

-- Plan 0: Superávit Principiante
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(1, 1), (1, 5), (1, 6), (1, 16), (1, 11), (1, 18);

-- Plan 1: Superávit Intermedio
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(2, 2), (2, 5), (2, 7), (2, 17), (2, 11), (2, 18);

-- Plan 2: Superávit Avanzado
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(3, 5), (3, 6), (3, 7), (3, 10), (3, 18), (3, 11);

-- Plan 3: Déficit Principiante
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(4, 3), (4, 8), (4, 15), (4, 16), (4, 13);

-- Plan 4: Déficit Intermedio
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(5, 3), (5, 9), (5, 12), (5, 16), (5, 13);

-- Plan 5: Mantenimiento
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(6, 1), (6, 6), (6, 11), (6, 16), (6, 14);

-- Plan 6: Pérdida de peso
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(7, 3), (7, 15), (7, 12), (7, 16), (7, 14);

-- Plan 7: Rehabilitación
INSERT IGNORE INTO plan_comidas (plan_id, comida_id) VALUES
(8, 1), (8, 8), (8, 13), (8, 16), (8, 15);