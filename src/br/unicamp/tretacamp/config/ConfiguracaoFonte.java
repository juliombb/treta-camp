package br.unicamp.tretacamp.config;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 15/06/18
 */
public class ConfiguracaoFonte {
    public final String PIXEL_LOVE;

    private static ConfiguracaoFonte instancia = null;
    public static ConfiguracaoFonte getInstance() throws MalformedURLException {
        if (instancia == null) {
            instancia = new ConfiguracaoFonte();
        }

        return instancia;
    }

    private ConfiguracaoFonte() throws MalformedURLException {
        PIXEL_LOVE = "pixel-love";
    }

}
