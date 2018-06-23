package br.unicamp.tretacamp.config;

import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Tipo;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@200491)
 * @since 15/06/18
 */
public class ConfiguracaoCenario {
    private static final String cen1 = "src/resources/cen1.gif";
    private static final String cen2 = "src/resources/cen2.jpg";
    private static final String cen3 = "src/resources/cen3.gif";
    private static final String cen4 = "src/resources/cen4.jpg";

    public static final String[] cenarios = {cen1, cen2, cen3, cen4};
    public static int length() { return cenarios.length; }
}
