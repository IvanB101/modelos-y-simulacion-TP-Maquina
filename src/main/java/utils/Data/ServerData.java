package utils.Data;

import java.text.DecimalFormat;

import resources.Server;
import utils.Statistics;

public class ServerData extends Data {
    private static final int columns = 8;
    private int rows;

    public ServerData(Statistics statistics) {
        super(statistics);
    }

    @Override
    public void calculateData() {
        double[][] data = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            Server server = super.getStatistics().getServers().get(i);

            data[i][0] = server.getId();
            data[i][1] = server.getClassServerid();
            data[i][2] = server.getIdleTime();
            data[i][3] = server.getMaxIdleTime();
            data[i][4] = server.getMaxIdleTime() / server.getIdleTime();
            data[i][5] = server.getIdleTime() / super.getStatistics().getEndTime();
            data[i][6] = server.getQueue().getMaxSize();
            data[i][7] = server.getDurability();
        }

        super.setData(data);
    }

    @Override
    public String toString() {
        double[][] data = super.getData();

        Statistics statistics = super.getStatistics();

        DecimalFormat format = new DecimalFormat("#0.00"), dformat = new DecimalFormat("#0.00%");

        String ret = "Estadísticas por Servidor según Id\n\n";

        String[] analytics = new String[statistics.getServers().size() + 1];

        String formatanalytics = "%-8s%18s%17s%16s%22s%24s%22s%12s";

        analytics[0] = String.format(formatanalytics, "", "", "", "", "Porcentaje de tiempo",
                "Porcentaje del maximo", "", "\n") +
                String.format(formatanalytics, "", "", "Tiempo total", "Tiempo máximo",
                        "de ocio respecto",
                        "de ocio respecto al", "Tamaño máximo de la", "\n")
                +
                String.format(formatanalytics, "Server", "Tipo", "de ocio",
                        "de ocio", "tiempo total de ocio", "al total", "cola de espera", "Durabilidad");

        for (int i = 0; i < statistics.getServerAmount(0); i++) {
            analytics[i + 1] = String.format(formatanalytics, (int) data[i][0],
                    statistics.getClassServerName((int) data[i][1]),
                    format.format(data[i][2]),
                    format.format(data[i][3]), dformat.format(data[i][4]),
                    dformat.format(data[i][5]),
                    (int) data[i][6],
                    format.format(data[i][7]));
        }
        ret += String.join("\n", analytics);
        ret += "\n\n\n";

        return ret;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
