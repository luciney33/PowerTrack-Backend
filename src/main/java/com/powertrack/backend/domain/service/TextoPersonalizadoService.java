package com.powertrack.backend.domain.service;

import org.springframework.stereotype.Service;

@Service
public class TextoPersonalizadoService {

    public String generarDescripcionRutina(int objetivo, int nivel, int dias, int lesion) {
        String textoObjetivo = switch (objetivo) {
            case 0 -> "Tu objetivo es ganar masa muscular y aumentar tu fuerza de forma progresiva.";
            case 1 -> "Tu programa está orientado a la definición muscular y la reducción de grasa corporal.";
            case 2 -> "Tu rutina está diseñada para mantener tu condición física actual y consolidar tus avances.";
            case 3 -> "Tu plan está enfocado en la pérdida de peso de forma progresiva y saludable.";
            default -> "Tu rutina está adaptada a tus objetivos personales.";
        };

        String textoNivel = switch (nivel) {
            case 0 -> "Al estar comenzando, nos enfocaremos en aprender la técnica correcta y construir una base sólida antes de aumentar la intensidad.";
            case 1 -> "Con tu experiencia intermedia, trabajaremos con cargas progresivas, mayor variedad de ejercicios y más intensidad.";
            case 2 -> "Tu nivel avanzado nos permite aplicar técnicas de alta intensidad, mayor volumen y programas periodizados.";
            default -> "La rutina se adaptará a tu nivel actual.";
        };

        String textoDias = dias <= 3
                ? "Entrenarás " + dias + " días a la semana, lo que te permitirá una recuperación completa entre sesiones."
                : dias == 4
                ? "Con 4 sesiones semanales tendrás una frecuencia de entrenamiento óptima para progresar."
                : "Con " + dias + " días de entrenamiento semanal necesitarás prestar especial atención al descanso y la nutrición.";

        String textoLesion = switch (lesion) {
            case 1 -> " Adaptaremos los ejercicios para proteger tu espalda y evitar sobrecargas en esa zona.";
            case 2 -> " Evitaremos ejercicios de alto impacto en rodilla y priorizaremos movimientos seguros.";
            case 3 -> " Los ejercicios de hombro serán modificados para prevenir molestias y permitir tu recuperación.";
            case 4 -> " Adaptaremos la rutina para cuidar tu tobillo y evitar impactos innecesarios.";
            default -> "";
        };

        return textoObjetivo + " " + textoNivel + " " + textoDias + textoLesion;
    }

    public String generarConsejosNutricion(int objetivo, int genero, int edad, int pesoCat) {
        String textoObjetivo = switch (objetivo) {
            case 0 -> "Para ganar músculo necesitas un superávit calórico moderado. Prioriza proteínas de calidad (1.6-2 g por kg de peso corporal) y carbohidratos complejos como arroz, avena y pasta para rendir al máximo en tus entrenamientos.";
            case 1 -> "Para la definición necesitas un déficit calórico moderado sin sacrificar músculo. Mantén una ingesta alta de proteínas (2 g por kg), reduce los carbohidratos simples y come abundante verdura para controlar el hambre.";
            case 2 -> "Para mantener tu composición corporal equilibra tu ingesta calórica con tu gasto diario. Distribuye bien los macronutrientes: proteínas, carbohidratos de calidad y grasas saludables a lo largo del día.";
            case 3 -> "Para perder peso de forma sostenible, apunta a un déficit calórico de 300-500 kcal diarias. Prioriza alimentos saciantes como proteínas magras, fibra y verduras, y evita ultraprocesados y bebidas azucaradas.";
            default -> "Sigue una alimentación variada y equilibrada adaptada a tu nivel de actividad física.";
        };

        String textoEdad = switch (edad) {
            case 0 -> "A tu edad el metabolismo es muy activo, aprovéchalo con una alimentación variada y no te saltes comidas clave como el desayuno post-entreno.";
            case 1 -> "A partir de los 30 el metabolismo comienza a ralentizarse; ajusta las porciones a tu actividad real y evita excesos calóricos en días de descanso.";
            case 2 -> "En esta etapa es especialmente importante el calcio y la vitamina D para mantener la densidad ósea, además de mantener una ingesta proteica elevada.";
            case 3 -> "Presta especial atención a la proteína para prevenir la pérdida muscular propia de la edad, y mantén una hidratación constante a lo largo del día.";
            default -> "Adapta tu alimentación a tu edad y nivel de actividad para optimizar los resultados.";
        };

        String textoPeso = switch (pesoCat) {
            case 0 -> "Con tu peso actual asegúrate de no estar en déficit calórico excesivo, ya que podría comprometer tu energía y rendimiento.";
            case 1 -> "Tu rango de peso es favorable para la mayoría de objetivos fitness; mantén la consistencia en la dieta para ver progresos.";
            case 2 -> "Ajusta tus calorías con precisión según el objetivo: superávit moderado para ganar músculo o déficit controlado para perder grasa.";
            case 3 -> "Prioriza alimentos de alta saciedad y bajo índice glucémico para gestionar mejor el apetito y mantener energía estable.";
            case 4 -> "Una dieta estructurada con alto contenido en proteína y fibra te ayudará a controlar el hambre, preservar el músculo y mejorar tu composición corporal.";
            default -> "Adapta tu ingesta calórica a tu peso y objetivo para maximizar los resultados.";
        };

        return textoObjetivo + " " + textoEdad + " " + textoPeso;
    }
}
