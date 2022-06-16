package utils.Data;

import java.text.DecimalFormat;

import utils.Statistics;

public class ServerByTypeData extends Data {
    private static final String[] header = {
            "", "Tiempo total de ocio", "Tiempo máximo de ocio", "Porcentaje de tiempo de ocio respecto al total",
            "Porcentaje del maximo de ocio respecto al tiempo total de ocio", "Tamaño máximo de la cola de espera"
    };
    private static final int rows = header.length;
    private static final DecimalFormat format = new DecimalFormat("#0.00");
    private static final DecimalFormat dformat = new DecimalFormat("#0.00%");
    private int columns = super.getStatistics().getServerClassesNumber();

    public ServerByTypeData(Statistics statistics) {
        super(statistics);
    }

    @Override
    public void calculateData() {
        double[][] data = new double[rows][columns];

        Statistics statistics = super.getStatistics();

        for (int i = 0; i < columns; i++) {
            data[0][i] = i;
            data[1][i] = statistics.getTotalIdleTime(i);
            data[2][i] = statistics.getMaxIdleTime(i);
            data[3][i] = statistics.getTotalIdleTime(i)
                    / (statistics.getEndTime() * statistics.getServerAmount(i));
            data[4][i] = statistics.getMaxIdleTime(i) / statistics.getTotalIdleTime(i);
            data[5][i] = statistics.getMaxQueueSize(i);
        }

        super.setData(data);
    }

    @Override
    public String toString() {
        String[] analytics = new String[rows + 1];
        double[][] data = super.getData();
        Statistics statistics = super.getStatistics();

        String ret = "Estadísticas por Servidor discriminadas por tipo\n\n";

        analytics = new String[rows];

        String f1 = "%-65s", f2 = "%21s";

        for (int i = 0; i < rows; i++) {
            analytics[i] = String.format(f1, header[i]);
        }

        for (int i = 0; i < columns; i++) {
            analytics[0] += String.format(f2, statistics.getClassServerName((int) data[0][i]));
            analytics[1] += String.format(f2, format.format(data[1][i]));
            analytics[2] += String.format(f2, format.format(data[2][i]));
            analytics[3] += String.format(f2, dformat.format(data[3][i]));
            analytics[4] += String.format(f2, dformat.format(data[4][i]));
            analytics[5] += String.format(f2, data[5][i]);
        }

        ret += String.join("\n", analytics);
        ret += "\n\n\n";

        return ret;
    }
}