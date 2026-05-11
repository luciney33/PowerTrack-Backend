-- Reinsertar todas las asociaciones plan-comidas por si faltaron en V1
INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Principiante'
  AND c.nombre IN ('Avena con leche', 'Batido de proteínas', 'Arroz con pollo', 'Fruta de temporada', 'Salmón con verduras', 'Barrita de proteínas')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Intermedio'
  AND c.nombre IN ('Tortilla francesa', 'Batido de proteínas', 'Pasta con atún', 'Frutos secos', 'Salmón con verduras', 'Barrita de proteínas')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Superávit Avanzado'
  AND c.nombre IN ('Batido de proteínas', 'Arroz con pollo', 'Pasta con atún', 'Pechuga a la plancha con patata', 'Barrita de proteínas', 'Salmón con verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Déficit Principiante'
  AND c.nombre IN ('Yogur griego con fruta', 'Ensalada de quinoa', 'Crema de verduras', 'Fruta de temporada', 'Tortilla de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Déficit Intermedio'
  AND c.nombre IN ('Yogur griego con fruta', 'Lentejas con verduras', 'Merluza al vapor', 'Fruta de temporada', 'Tortilla de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Mantenimiento'
  AND c.nombre IN ('Avena con leche', 'Arroz con pollo', 'Salmón con verduras', 'Fruta de temporada', 'Ensalada de pollo', 'Pechuga a la plancha con patata', 'Yogur griego con fruta')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Pérdida de Peso'
  AND c.nombre IN ('Yogur griego con fruta', 'Crema de verduras', 'Merluza al vapor', 'Fruta de temporada', 'Ensalada de pollo')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);

INSERT INTO plan_comidas (plan_id, comida_id)
SELECT p.id, c.id FROM planes_nutricionales p, comidas c
WHERE p.nombre = 'Rehabilitación Nutricional'
  AND c.nombre IN ('Avena con leche', 'Ensalada de quinoa', 'Tortilla de verduras', 'Fruta de temporada', 'Crema de verduras')
  AND NOT EXISTS (SELECT 1 FROM plan_comidas WHERE plan_id = p.id AND comida_id = c.id);
