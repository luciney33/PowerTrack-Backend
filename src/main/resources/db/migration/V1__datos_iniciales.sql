INSERT INTO usuarios (username, password, email, nombre, rol, activo, formulario_completado)
SELECT 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHHi',
       'admin@powertrack.com', 'Administrador', 'ADMIN', true, false
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'admin');

INSERT INTO ejercicios (nombre, tipo_entrenamiento, imagen_url, descripcion)
SELECT nombre, tipo_entrenamiento, imagen_url, descripcion FROM (
 SELECT 'Press de banca' nombre, 'PECHO' tipo_entrenamiento, '' imagen_url, 'Ejercicio básico de empuje para pecho' descripcion UNION ALL
 SELECT 'Fondos en paralelas', 'PECHO', '', 'Ejercicio de peso corporal para pecho y tríceps' UNION ALL
 SELECT 'Aperturas con mancuernas', 'PECHO', '', 'Ejercicio de aislamiento para pecho' UNION ALL
 SELECT 'Press inclinado', 'PECHO', '', 'Press de banca con banco inclinado' UNION ALL
 SELECT 'Dominadas', 'ESPALDA', '', 'Ejercicio de peso corporal para espalda' UNION ALL
 SELECT 'Remo con barra', 'ESPALDA', '', 'Ejercicio de tirón para espalda media' UNION ALL
 SELECT 'Jalón al pecho', 'ESPALDA', '', 'Ejercicio de tirón en polea alta' UNION ALL
 SELECT 'Remo en polea baja', 'ESPALDA', '', 'Ejercicio de tirón para espalda baja' UNION ALL
 SELECT 'Sentadilla', 'PIERNAS', '', 'Ejercicio compuesto fundamental para piernas' UNION ALL
 SELECT 'Prensa de piernas', 'PIERNAS', '', 'Ejercicio de empuje para cuádriceps' UNION ALL
 SELECT 'Zancadas', 'PIERNAS', '', 'Ejercicio unilateral para piernas' UNION ALL
 SELECT 'Curl de isquiotibiales', 'PIERNAS', '', 'Aislamiento de isquiotibiales' UNION ALL
 SELECT 'Press militar', 'HOMBROS', '', 'Ejercicio básico de empuje para hombros' UNION ALL
 SELECT 'Elevaciones laterales', 'HOMBROS', '', 'Aislamiento de deltoides lateral' UNION ALL
 SELECT 'Pájaros', 'HOMBROS', '', 'Aislamiento de deltoides posterior' UNION ALL
 SELECT 'Curl de bíceps', 'BICEPS', '', 'Ejercicio básico de aislamiento de bíceps' UNION ALL
 SELECT 'Curl martillo', 'BICEPS', '', 'Curl neutro para bíceps y braquial' UNION ALL
 SELECT 'Extensión de tríceps', 'TRICEPS', '', 'Aislamiento de tríceps en polea' UNION ALL
 SELECT 'Press francés', 'TRICEPS', '', 'Ejercicio de aislamiento para tríceps' UNION ALL
 SELECT 'Plancha', 'CORE', '', 'Ejercicio isométrico para core' UNION ALL
 SELECT 'Crunch abdominal', 'CORE', '', 'Ejercicio básico de abdominales' UNION ALL
 SELECT 'Cardio moderado', 'CARDIO', '', 'Sesión de cardio a intensidad moderada' UNION ALL
 SELECT 'HIIT', 'CARDIO', '', 'Entrenamiento de intervalos de alta intensidad' UNION ALL
 SELECT 'Movilidad articular', 'MOVILIDAD', '', 'Ejercicios de movilidad y flexibilidad'
         ) tmp WHERE NOT EXISTS (SELECT 1 FROM ejercicios WHERE nombre = tmp.nombre);

INSERT INTO rutinas (nombre, descripcion, tipo)
SELECT nombre, descripcion, tipo FROM (
                                          SELECT 'Volumen - Principiante' nombre, 'Rutina de volumen para principiantes con ejercicios básicos' descripcion, 0 tipo UNION ALL
                                          SELECT 'Volumen - Intermedio', 'Rutina de volumen para nivel intermedio', 1 UNION ALL
                                          SELECT 'Volumen - Avanzado', 'Rutina de volumen para nivel avanzado', 2 UNION ALL
                                          SELECT 'Definición - Principiante', 'Rutina de definición para principiantes', 3 UNION ALL
                                          SELECT 'Definición - Intermedio', 'Rutina de definición para nivel intermedio', 4 UNION ALL
                                          SELECT 'Mantenimiento', 'Rutina equilibrada de mantenimiento', 5 UNION ALL
                                          SELECT 'Pérdida de peso', 'Rutina enfocada en quema de calorías', 6 UNION ALL
                                          SELECT 'Rehabilitación', 'Rutina suave para personas con lesiones', 7
                                      ) tmp WHERE NOT EXISTS (SELECT 1 FROM rutinas WHERE nombre = tmp.nombre);

INSERT INTO planes_nutricionales (nombre, descripcion, tipo, calorias_objetivo, proteinas_objetivo, carbohidratos_objetivo, grasas_objetivo)
SELECT nombre, descripcion, tipo, calorias_objetivo, proteinas_objetivo, carbohidratos_objetivo, grasas_objetivo FROM (

SELECT 'Superávit Principiante' nombre, 'Plan hipercalórico para ganar masa muscular' descripcion, 0 tipo, 3000 calorias_objetivo, 180 proteinas_objetivo, 350 carbohidratos_objetivo, 90 grasas_objetivo UNION ALL
SELECT 'Superávit Intermedio', 'Plan hipercalórico moderado', 1, 3200, 200, 370, 95 UNION ALL
SELECT 'Superávit Avanzado', 'Plan hipercalórico para volumen avanzado', 2, 3500, 220, 400, 100 UNION ALL
SELECT 'Déficit Principiante', 'Plan hipocalórico suave para definición', 3, 2000, 160, 200, 60 UNION ALL
SELECT 'Déficit Intermedio', 'Plan hipocalórico moderado para definición', 4, 1800, 170, 180, 55 UNION ALL
SELECT 'Mantenimiento', 'Plan equilibrado de mantenimiento calórico', 5, 2400, 150, 280, 75 UNION ALL
SELECT 'Pérdida de Peso', 'Plan con déficit calórico para perder peso', 6, 1600, 140, 160, 50 UNION ALL
SELECT 'Rehabilitación Nutricional', 'Plan suave y equilibrado para lesionados', 7, 2200, 130, 260, 70
                                                   ) tmp WHERE NOT EXISTS (SELECT 1 FROM planes_nutricionales WHERE nombre = tmp.nombre);

INSERT INTO comidas (nombre, calorias, proteinas, carbohidratos, grasas, categoria)
SELECT nombre, calorias, proteinas, carbohidratos, grasas, categoria FROM (

           SELECT 'Avena con leche' nombre, 350 calorias, 15 proteinas, 55 carbohidratos, 8 grasas, 'DESAYUNO' categoria UNION ALL
           SELECT 'Tortilla francesa', 200, 14, 2, 14, 'DESAYUNO' UNION ALL
           SELECT 'Yogur griego con fruta', 250, 18, 30, 5, 'DESAYUNO' UNION ALL
           SELECT 'Tostadas con aguacate', 300, 8, 35, 14, 'DESAYUNO' UNION ALL
           SELECT 'Batido de proteínas', 200, 25, 15, 3, 'DESAYUNO' UNION ALL
           SELECT 'Arroz con pollo', 500, 40, 55, 8, 'ALMUERZO' UNION ALL
           SELECT 'Pasta con atún', 480, 35, 60, 10, 'ALMUERZO' UNION ALL
           SELECT 'Ensalada de quinoa', 380, 20, 45, 12, 'ALMUERZO' UNION ALL
           SELECT 'Lentejas con verduras', 420, 25, 50, 8, 'ALMUERZO' UNION ALL
           SELECT 'Pechuga a la plancha con patata', 450, 45, 40, 7, 'ALMUERZO' UNION ALL
           SELECT 'Salmón con verduras', 420, 38, 20, 18, 'CENA' UNION ALL
           SELECT 'Merluza al vapor', 280, 35, 5, 8, 'CENA' UNION ALL
           SELECT 'Tortilla de verduras', 300, 18, 15, 16, 'CENA' UNION ALL
           SELECT 'Ensalada de pollo', 350, 35, 15, 14, 'CENA' UNION ALL
           SELECT 'Crema de verduras', 200, 8, 25, 6, 'CENA' UNION ALL
           SELECT 'Fruta de temporada', 150, 2, 35, 1, 'MERIENDA' UNION ALL
           SELECT 'Frutos secos', 200, 6, 8, 16, 'MERIENDA' UNION ALL
           SELECT 'Barrita de proteínas', 220, 20, 22, 7, 'MERIENDA' UNION ALL
           SELECT 'Queso fresco con miel', 180, 12, 18, 5, 'MERIENDA'
       ) tmp WHERE NOT EXISTS (SELECT 1 FROM comidas WHERE nombre = tmp.nombre);

-- RELACIONES RUTINA-EJERCICIOS
INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Volumen - Principiante' AND e.nombre IN ('Press de banca', 'Sentadilla', 'Curl de bíceps', 'Extensión de tríceps', 'Press militar', 'Plancha')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Volumen - Intermedio' AND e.nombre IN ('Press de banca', 'Fondos en paralelas', 'Dominadas', 'Remo con barra', 'Sentadilla', 'Prensa de piernas', 'Press militar', 'Curl de bíceps')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Volumen - Avanzado' AND e.nombre IN ('Press de banca', 'Fondos en paralelas', 'Aperturas con mancuernas', 'Dominadas', 'Remo con barra', 'Jalón al pecho', 'Sentadilla', 'Prensa de piernas', 'Zancadas', 'Press militar', 'Elevaciones laterales')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Definición - Principiante' AND e.nombre IN ('Cardio moderado', 'Sentadilla', 'Plancha', 'Crunch abdominal', 'Curl de bíceps')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Definición - Intermedio' AND e.nombre IN ('HIIT', 'Sentadilla', 'Zancadas', 'Plancha', 'Crunch abdominal', 'Cardio moderado')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Mantenimiento' AND e.nombre IN ('Press de banca', 'Sentadilla', 'Press militar', 'Cardio moderado', 'Plancha', 'Movilidad articular')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Pérdida de peso' AND e.nombre IN ('Cardio moderado', 'HIIT', 'Sentadilla', 'Zancadas', 'Plancha', 'Crunch abdominal')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

INSERT INTO rutina_ejercicios (rutina_id, ejercicio_id)
SELECT r.id, e.id FROM rutinas r, ejercicios e
WHERE r.nombre = 'Rehabilitación' AND e.nombre IN ('Movilidad articular', 'Plancha', 'Zancadas', 'Crunch abdominal')
  AND NOT EXISTS (SELECT 1 FROM rutina_ejercicios WHERE rutina_id = r.id AND ejercicio_id = e.id);

-- RELACIONES PLAN-COMIDAS
INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Principiante' AND c.nombre IN ('Avena con leche', 'Batido de proteínas', 'Arroz con pollo', 'Fruta de temporada', 'Salmón con verduras', 'Barrita de proteínas')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Intermedio' AND c.nombre IN ('Tortilla francesa', 'Batido de proteínas', 'Pasta con atún', 'Frutos secos', 'Salmón con verduras', 'Barrita de proteínas')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Avanzado' AND c.nombre IN ('Batido de proteínas', 'Arroz con pollo', 'Pasta con atún', 'Pechuga a la plancha con patata', 'Barrita de proteínas', 'Salmón con verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Déficit Principiante' AND c.nombre IN ('Yogur griego con fruta', 'Ensalada de quinoa', 'Crema de verduras', 'Fruta de temporada', 'Tortilla de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Déficit Intermedio' AND c.nombre IN ('Yogur griego con fruta', 'Lentejas con verduras', 'Merluza al vapor', 'Fruta de temporada', 'Tortilla de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Mantenimiento' AND c.nombre IN ('Avena con leche', 'Arroz con pollo', 'Salmón con verduras', 'Fruta de temporada', 'Ensalada de pollo')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Pérdida de Peso' AND c.nombre IN ('Yogur griego con fruta', 'Crema de verduras', 'Merluza al vapor', 'Fruta de temporada', 'Ensalada de pollo')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Rehabilitación Nutricional' AND c.nombre IN ('Avena con leche', 'Ensalada de quinoa', 'Tortilla de verduras', 'Fruta de temporada', 'Crema de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);