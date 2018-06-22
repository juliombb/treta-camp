package br.unicamp.tretacamp.config;

import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Tipo;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@200491)
 * @since 15/06/18
 */
public class ConfiguracaoDregos {
    private final Drego mago = new Drego(
        "mago", "mago.png", 100.0, 100.0, Tipo.AGUA, null);
    private final Drego espotenique = new Drego(
        "espotenique", "espotenique.png", 100.0, 100.0, Tipo.AGUA, null);
    private final Drego monho = new Drego(
        "monho", "monho.gif", 100.0, 100.0, Tipo.AGUA, null);
    private final Drego cobroso = new Drego(
        "cobroso", "cobroso.png", 100.0, 100.0, Tipo.AGUA, null);

    public final Drego[] dregos = {mago, espotenique, monho, cobroso};

    private static ConfiguracaoDregos instancia = null;
    public static ConfiguracaoDregos getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoDregos();
        }

        return instancia;
    }

    private ConfiguracaoDregos() {
        mago.adicionarPoder(ConfiguracaoPoder.PEPITA_MAGICA);
        espotenique.adicionarPoder(ConfiguracaoPoder.CHUTE);
        monho.adicionarPoder(ConfiguracaoPoder.SOCO);
        cobroso.adicionarPoder(ConfiguracaoPoder.BOLA_DE_FOGO);
    }
}
