-- Los FK constraints requieren que rutina_id y ejercicio_id estén indexados.
-- La PK compuesta (rutina_id, ejercicio_id) es el único índice actual.
-- Creamos índices independientes primero para poder eliminar la PK sin romper los FKs.
ALTER TABLE rutina_ejercicios ADD INDEX idx_re_rutina (rutina_id);
ALTER TABLE rutina_ejercicios ADD INDEX idx_re_ejercicio (ejercicio_id);

ALTER TABLE rutina_ejercicios DROP PRIMARY KEY;
ALTER TABLE rutina_ejercicios
  ADD COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  ADD PRIMARY KEY (id);

ALTER TABLE rutina_ejercicios
  ADD COLUMN series INT NULL,
  ADD COLUMN repeticiones INT NULL,
  ADD COLUMN descanso_seg INT NULL;