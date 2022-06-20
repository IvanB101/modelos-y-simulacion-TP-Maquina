package utils.Data;

import java.text.DecimalFormat;

import utils.Statistics;

public class EntityData extends Data {
    private static final String[] header = { "", "Cantidad total de aterrizajes:",
            "Tiempo total de espera en cola:",
            "Tiempo medio de espera en cola:", "Tiempo máximo de espera en cola:",
            "Tiempo medio de transito:", "Tiempo máximo de transito:" };
    private static final int rows = header.length;
    private int columns;
    private static DecimalFormat format = new DecimalFormat("#0.00");

    public EntityData(Statistics statistics) {
        super(statistics);
        // The -1 is for not taking Maintenance Entities into account
        this.columns = statistics.getEntityClassesNumber() - 1;
    }

    @Override
    public void calculateData() {
        double[][] data = new double[rows][columns];

        Statistics statistics = super.getStatistics();

        for (int i = 0; i < columns; i++) {
            int landings = (statistics.getEntityIdCount(i) - statistics.getInQueueAircrafts(i));

            data[0][i] = i;
            data[1][i] = landings;
            data[2][i] = statistics.getTotalWaitingTime(i);
            data[3][i] = statistics.getTotalWaitingTime(i) / landings;
            data[4][i] = statistics.getMaxWaitingTime(i);
            data[5][i] = statistics.getTotalTransitTime(i) / landings;
            data[6][i] = statistics.getMaxTransitTime(i);

            super.setData(data);
        }
    }

    @Override
    public String toString() {
        double[][] data = super.getData();

        Statistics statistics = super.getStatistics();

        String ret = "Estadísticas disciminadas por tipo de Entidad:\n\n";

        String[] analytics = new String[rows];

        String f1 = "%-35s", f2 = "%21s";

        for (int i = 0; i < header.length; i++) {
            analytics[i] = String.format(f1, header[i]);
        }

        for (int i = 0; i < columns; i++) {
            analytics[0] += String.format(f2, statistics.getClassEntityName((int) data[0][i]));
            analytics[1] += String.format(f2, format.format(data[1][i]));
            analytics[2] += String.format(f2, format.format(data[2][i]));
            analytics[3] += String.format(f2, format.format(data[3][i]));
            analytics[4] += String.format(f2, format.format(data[4][i]));
            analytics[5] += String.format(f2, format.format(data[5][i]));
            analytics[6] += String.format(f2, format.format(data[6][i]));
        }

        ret += String.join("\n", analytics);
        ret += "\n\n\n";

        return ret;
    }
}
