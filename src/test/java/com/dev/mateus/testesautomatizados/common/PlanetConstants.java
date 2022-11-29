package com.dev.mateus.testesautomatizados.common;

import com.dev.mateus.testesautomatizados.domain.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {

    public static final Planet PLANET =
            new Planet(null, "NAME", "CLIMATE", "TERRAIN");

    public static final Planet EMPTY_PLANET =
            new Planet(null, "", "", "");

    public static final Planet NULL_PLANET =
            new Planet(null, null, null, null);

    public static final Long EXISTING_ID = 1L;

    public static final Long UNEXISTING_ID = 1L;

    public static final String EXISTING_NAME = "existing";

    public static final String UNEXISTING_NAME = "nonexisting";

    public static final Planet TATOOINE = new Planet(1L, "Tatooine", "arid", "desert");
    public static final Planet ALDERAAN = new Planet(2L, "Alderaan", "temperate", "grasslands, mountains");
    public static final Planet YAVINIV = new Planet(3L, "Yavin IV", "temperate, tropical", "jungle, rainforests");
    public static final List<Planet> PLANETS = new ArrayList<>() {
        {
            add(TATOOINE);
            add(ALDERAAN);
            add(YAVINIV);
        }
    };
}
