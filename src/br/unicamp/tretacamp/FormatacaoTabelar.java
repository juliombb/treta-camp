package br.unicamp.tretacamp;

import com.sun.javafx.geom.Vec2d;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */
public class FormatacaoTabelar {
    final Double larguraColuna;
    final Double alturaColuna;
    final Integer numColunas;

    Integer colunaAtual = 0;
    Double alturaAtual = 0.0;

    public FormatacaoTabelar(Double larguraColuna, Double alturaColuna, Integer numColunas) {
        this.larguraColuna = larguraColuna;
        this.alturaColuna = alturaColuna;
        this.numColunas = numColunas;
    }

    public Vec2d proxCelula() {
        if (colunaAtual + 1 >= numColunas) {
            colunaAtual = 0;
            alturaAtual += alturaColuna;
        }

        return new Vec2d((colunaAtual++)*larguraColuna, alturaAtual);
    }
}
