UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 3, re.repeticiones = 12, re.descanso_seg = 60 WHERE r.tipo = 0;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 4, re.repeticiones = 10, re.descanso_seg = 90 WHERE r.tipo = 1;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 5, re.repeticiones = 8, re.descanso_seg = 120 WHERE r.tipo = 2;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 3, re.repeticiones = 15, re.descanso_seg = 60 WHERE r.tipo = 3;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 4, re.repeticiones = 12, re.descanso_seg = 45 WHERE r.tipo = 4;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 3, re.repeticiones = 12, re.descanso_seg = 75 WHERE r.tipo = 5;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 3, re.repeticiones = 15, re.descanso_seg = 45 WHERE r.tipo = 6;

UPDATE rutina_ejercicios re JOIN rutinas r ON re.rutina_id = r.id
SET re.series = 2, re.repeticiones = 10, re.descanso_seg = 90 WHERE r.tipo = 7;