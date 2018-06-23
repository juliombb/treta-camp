package br.unicamp.tretacamp.util;

import com.sun.javafx.geom.Vec2d;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */
public class FormatacaoTabelar {
    final Double larguraColuna;
    final Double alturaColuna;
    final Integer numColunas;
    final Double espacamento;

    Integer colunaAtual = 0;
    Integer linhaAtual = 0;

    public FormatacaoTabelar(
        Double larguraColuna,
        Double alturaColuna,
        Integer numColunas,
        Double espacamento) {
        this.larguraColuna = larguraColuna;
        this.alturaColuna = alturaColuna;
        this.numColunas = numColunas;
        this.espacamento = espacamento;
    }

    public Vec2d proxCelula() {
        if (colunaAtual + 1 > numColunas) {
            colunaAtual = 0;
            linhaAtual++;
        }

        return new Vec2d(
            (colunaAtual++)*(larguraColuna + espacamento),
            linhaAtual*(alturaColuna + espacamento));
    }

    public void zerar() {
        this.colunaAtual = 0;
        this.linhaAtual = 0;
    }
}
