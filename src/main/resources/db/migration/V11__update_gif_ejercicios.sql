-- Añadir ejercicio Búlgaras
INSERT INTO ejercicios (nombre, tipo_entrenamiento, imagen_url, descripcion)
SELECT 'Búlgaras', 'PIERNAS', 'https://static.exercisedb.dev/media/6YUfHPL.gif', 'Sentadilla búlgara unilateral para piernas y glúteos'
WHERE NOT EXISTS (SELECT 1 FROM ejercicios WHERE nombre = 'Búlgaras');

-- Actualizar GIFs de todos los ejercicios
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/5v7KYld.gif' WHERE nombre = 'Press de banca';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/K1vlode.gif' WHERE nombre = 'Fondos en paralelas';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/7gdLIXa.gif' WHERE nombre = 'Aperturas con mancuernas';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/3TZduzM.gif' WHERE nombre = 'Press inclinado';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/0V2YQjW.gif' WHERE nombre = 'Dominadas';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/aaxA3cm.gif' WHERE nombre = 'Remo con barra';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/7F1DVzn.gif' WHERE nombre = 'Jalón al pecho';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/4LoWllp.gif' WHERE nombre = 'Remo en polea baja';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/Gu2rNJd.gif' WHERE nombre = 'Sentadilla';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/V07qpXy.gif' WHERE nombre = 'Prensa de piernas';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/9E25EOx.gif' WHERE nombre = 'Zancadas';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/17lJ1kr.gif' WHERE nombre = 'Curl de isquiotibiales';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/CggQhII.gif' WHERE nombre = 'Press militar';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/goJ6ezq.gif' WHERE nombre = 'Elevaciones laterales';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/aqvSOQE.gif' WHERE nombre = 'Pájaros';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/25GPyDY.gif' WHERE nombre = 'Curl de bíceps';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/2NpxjC1.gif' WHERE nombre = 'Curl martillo';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/1xHyxys.gif' WHERE nombre = 'Extensión de tríceps';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/1cTf2Ux.gif' WHERE nombre = 'Press francés';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/CosupLu.gif' WHERE nombre = 'Plancha';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/225x2Vd.gif' WHERE nombre = 'Crunch abdominal';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/rjiM4L3.gif' WHERE nombre = 'Cardio moderado';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/XSCHmiI.gif' WHERE nombre = 'HIIT';
UPDATE ejercicios SET imagen_url = 'https://static.exercisedb.dev/media/0mB6wHO.gif' WHERE nombre = 'Movilidad articular';