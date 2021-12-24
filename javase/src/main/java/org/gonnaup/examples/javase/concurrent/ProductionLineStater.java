package org.gonnaup.examples.javase.concurrent;

/**
 * @author gonnaup
 * @version created at 2021/12/24 12:35
 */
public class ProductionLineStater {

    private final ProductionLine productionLine;

    public ProductionLineStater(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    public void startProductionLine() {
        new Thread(() -> productionLine.consume()).start();
        productionLine.produce();
    }
}
