package br.unicamp.tretacamp.config;

import br.unicamp.tretacamp.modelo.Diferencial;
import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Tipo;

import java.util.stream.Stream;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@200491)
 * @since 15/06/18
 */
public class ConfiguracaoDregos {
    private final Drego mago = new Drego(
        "Mago implacavel", "src/resources/mago.png", 100.0, 100.0, Tipo.AGUA, null);
    private final Drego espotenique = new Drego(
        "Espotenique", "src/resources/espotenique.png", 100.0, 100.0, Tipo.TERRA, Diferencial.PROTECAO_FOGO);
    private final Drego monho = new Drego(
        "Monho", "src/resources/monho.gif", 100.0, 100.0, Tipo.TREVAS, null);
    private final Drego cobroso = new Drego(
        "Cobroso", "src/resources/cobroso.png", 100.0, 100.0, Tipo.FOGO, null);
    private final Drego satan = new Drego(
            "Satan", "src/resources/satan.gif", 100.0, 100.0, Tipo.FOGO, Diferencial.DEFESA_PERFURANTE);
    private final Drego robot = new Drego(
            "Robot", "src/resources/robot.png", 100.0, 100.0, Tipo.AGUA, null);
    private final Drego taurus = new Drego(
            "Taurus", "src/resources/taurus.png", 100.0, 100.0, Tipo.TERRA, null);
    private final Drego gladio = new Drego(
            "Gladio", "src/resources/gladio.gif", 100.0, 100.0, Tipo.TREVAS, null);
    private final Drego draconis = new Drego(
        "Draconis", "src/resources/draconis.png", 120.0, 100.0, Tipo.METAL, null);
    private final Drego moradne = new Drego(
        "M O R A D N E", "src/resources/moradne.png", 50.0, 120.0, Tipo.SONORO, Diferencial.CACADOR_DE_MANA);
    private final Drego esther = new Drego(
            "ESTHER", "src/resources/esuter2.png", 302.0, 2000.0, Tipo.SONORO, Diferencial.CACADOR_DE_MANA);
    private final Drego gpt = new Drego(
            "ESTHER", "src/resources/easterEggs/gpt.png", 202.0, 2000.0, Tipo.SONORO, Diferencial.CACADOR_DE_MANA);
    private final Drego guido = new Drego(
            "ESTHER", "src/resources/easterEggs/guido.png", 102.0, 2000.0, Tipo.SONORO, Diferencial.CACADOR_DE_MANA);

    private final Drego[] dregosIni = {mago, espotenique, monho, cobroso, satan, robot, taurus, gladio};
    private final Drego[] dregosMid = {draconis, moradne};
    private final Drego[] dregosFim = {esther};
    private static ConfiguracaoDregos instancia = null;
    public static ConfiguracaoDregos getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoDregos();
        }

        return instancia;
    }

    public Drego[] getDregos(int fase) {
        switch (fase) {
            case 1: return dregosIni;
            case 2: return dregosMid;
            case 3: return dregosFim;
            default: return Stream.of(dregosIni, dregosMid, dregosFim).flatMap(Stream::of).toArray(Drego[]::new);
        }
    }

    private ConfiguracaoDregos() {
        mago.adicionarPoder(ConfiguracaoPoder.PEPITA_MAGICA);
        mago.adicionarPoder(ConfiguracaoPoder.RAIO);
        mago.adicionarPoder(ConfiguracaoPoder.SOCO);
        espotenique.adicionarPoder(ConfiguracaoPoder.CHUTE);
        espotenique.adicionarPoder(ConfiguracaoPoder.GELO);
        monho.adicionarPoder(ConfiguracaoPoder.SOCO);
        monho.adicionarPoder(ConfiguracaoPoder.NECROMANCIA);
        cobroso.adicionarPoder(ConfiguracaoPoder.BOLA_DE_FOGO);
        cobroso.adicionarPoder(ConfiguracaoPoder.MORDIDA);
        satan.adicionarPoder(ConfiguracaoPoder.NECROMANCIA);
        satan.adicionarPoder(ConfiguracaoPoder.MORDIDA);
        robot.adicionarPoder(ConfiguracaoPoder.CHUTE);
        robot.adicionarPoder(ConfiguracaoPoder.GATO_DONUT);
        taurus.adicionarPoder(ConfiguracaoPoder.CHUTE);
        taurus.adicionarPoder(ConfiguracaoPoder.BOLA_DE_FOGO);
        gladio.adicionarPoder(ConfiguracaoPoder.CHUTE);
        gladio.adicionarPoder(ConfiguracaoPoder.ESPADA);
        draconis.adicionarPoder(ConfiguracaoPoder.BOLA_DE_FOGO);
        draconis.adicionarPoder(ConfiguracaoPoder.MORDIDA);
        draconis.adicionarPoder(ConfiguracaoPoder.PEPITA_MAGICA);
        moradne.adicionarPoder(ConfiguracaoPoder.GATO_DONUT);
        moradne.adicionarPoder(ConfiguracaoPoder.SOCO);
        esther.adicionarPoder(ConfiguracaoPoder.LAB);
        guido.adicionarPoder(ConfiguracaoPoder.LAB);
        gpt.adicionarPoder(ConfiguracaoPoder.LAB);
    }
}
