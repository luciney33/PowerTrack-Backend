-- Reasignar registros_detalle que apuntan a ejercicios duplicados (id > 24)
-- al ejercicio canónico (id más bajo con el mismo nombre)
UPDATE registros_detalle rd
    JOIN ejercicios e_dup ON rd.ejercicio_id = e_dup.id
    JOIN (SELECT MIN(id) AS canonical_id, nombre FROM ejercicios GROUP BY nombre) e_can
         ON e_dup.nombre = e_can.nombre
SET rd.ejercicio_id = e_can.canonical_id
WHERE rd.ejercicio_id > 24;

-- para eliminar filas dependientes con referencias a duplicados
DELETE FROM plan_comidas WHERE comida_id > 19 OR plan_id > 8;
DELETE FROM rutina_ejercicios WHERE rutina_id > 8 OR ejercicio_id > 24;

-- para eliminar registros duplicados (mantener solo el primero de cada entidad)
DELETE FROM ejercicios WHERE id > 24;
DELETE FROM planes_nutricionales WHERE id > 8;
DELETE FROM comidas WHERE id > 19;
DELETE FROM rutinas WHERE id > 8;

-- para eliminar la tabla fantasma creada por hibernate cuando ddl-auto=update estaba activo
DROP TABLE IF EXISTS `static/comidas`;
