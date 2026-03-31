package com.powertrack.backend;

import com.powertrack.backend.service.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RecomendacionServiceTest.class,
        UsuarioServiceTest.class,
        EjercicioServiceTest.class,
        ComidaServiceTest.class,
        RutinaServiceTest.class,
        PlanNutricionalServiceTest.class,
        RegistroEntrenamientoServiceTest.class
})
class PowerTrackBackendApplicationTests {
}
