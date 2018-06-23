package br.unicamp.tretacamp.config;

import java.net.MalformedURLException;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 15/06/18
 */
public class ConfiguracaoEstilo {
    public final String PIXEL_LOVE;
    public final String PIXEL_LOVE_SMALL;
    public final String PIXEL_LOVE_BIG;
    public final String CONSOLAS_BOLD;
    public final String HOVER_TEXT;
    public final String HOVER_BUTTON;
    public final String IMG;
    public final String BRANCO;
    public final String SOMBRA;


    private static ConfiguracaoEstilo instancia = null;
    public static ConfiguracaoEstilo getInstance() throws MalformedURLException {
        if (instancia == null) {
            instancia = new ConfiguracaoEstilo();
        }

        return instancia;
    }

    private ConfiguracaoEstilo() throws MalformedURLException {
        PIXEL_LOVE = "pixel-love";
        PIXEL_LOVE_SMALL = "pixel-love-sm";
        PIXEL_LOVE_BIG = "pixel-love-big";
        HOVER_TEXT = "hover-text";
        HOVER_BUTTON = "hover-button";
        IMG = "img";
        BRANCO = "branco";
        SOMBRA = "sombra";
        CONSOLAS_BOLD = "consolas-bold";
    }

}
