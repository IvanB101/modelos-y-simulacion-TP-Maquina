package utils.Data;

import java.text.DecimalFormat;
import java.util.LinkedList;

import utils.Statistics;

public class ReplicationData {
    private static final DecimalFormat format = new DecimalFormat("#0.00");
    private static final DecimalFormat dformat = new DecimalFormat("#0.00%");

    private LinkedList<Statistics> replications;
    private String entitys;
    private String servers;
    private String serversByType;

    public ReplicationData() {
        replications = new LinkedList<Statistics>();
        entitys = null;
        servers = null;
        serversByType = null;
    }

    public void addReplication(Statistics statistics) {
        replications.add(statistics);
    }

    public String getEntitys() {
        if (entitys == null)
            setEntitys();

        return entitys;
    }

    private void setEntitys() {
        Data[] set = new Data[replications.size()];

        for (int i = 0; i < set.length; i++) {
            set[i] = replications.get(i).getEntityData();
        }

        double[][][] data = Data.mergeData(set);

        entitys = "Estadísticas disciminadas por tipo de Entidad:\n\n";

        String[] header = { "", "Cantidad total de aterrizajes:",
                "Tiempo total de espera en cola:",
                "Tiempo medio de espera en cola:", "Tiempo máximo de espera en cola:",
                "Tiempo medio de transito:", "Tiempo máximo de transito:" };

        String[] analytics = new String[data[0].length];

        String f1 = "%-35s", f2 = "%-22s", f3 = "%-10s ± %-9s";

        for (int i = 0; i < header.length; i++) {
            analytics[i] = String.format(f1, header[i]);
        }

        //The -1 is for not having the maintenance statistics
        for (int i = 0; i < replications.get(0).getEntityClassesNumber() - 1; i++) {
            analytics[0] += String.format(f2, replications.get(0).getClassEntityName(i));
            analytics[1] += String.format(f3, format.format(data[0][1][i]), format.format(data[1][1][i]));
            analytics[2] += String.format(f3, format.format(data[0][2][i]), format.format(data[1][2][i]));
            analytics[3] += String.format(f3, format.format(data[0][3][i]), format.format(data[1][3][i]));
            analytics[4] += String.format(f3, format.format(data[0][4][i]), format.format(data[1][4][i]));
            analytics[5] += String.format(f3, format.format(data[0][5][i]), format.format(data[1][5][i]));
            analytics[6] += String.format(f3, format.format(data[0][6][i]), format.format(data[1][6][i]));
        }

        entitys += String.join("\n", analytics);
        entitys += "\n\n\n";
    }

    public String getServers() {
        if (servers == null)
            setServers();

        return servers;
    }

    private void setServers() {
        Data[] set = new Data[replications.size()];

        for (int i = 0; i < set.length; i++) {
            set[i] = replications.get(i).getServerData();
        }

        double[][][] data = Data.mergeData(set);

        servers = "Estadísticas por Servidor según Id\n\n";

        String[] analytics = new String[replications.get(0).getServers().size() + 1];

        String formatheader = "%-8s%-18s%-17s%-16s%-22s%-24s%-22s%12s";
        String formatanalytics = "%-8s%-18s%-8s ± %-7s%-7s ± %-6s%-10s ± %-9s%-11s ± %-10s%-10s ± %-9s%5s ± %4s";

        analytics[0] = String.format(formatheader, "", "", "", "", "Porcentaje de tiempo",
                "Porcentaje del maximo", "", "\n") +
                String.format(formatheader, "", "", "Tiempo máximo", "Tiempo total",
                        "de ocio respecto",
                        "de ocio respecto al", "Tamaño máximo de la", "\n") +
                String.format(formatheader, "Server", "Tipo", "de ocio",
                        "de ocio", "al total", "tiempo total de ocio", "cola de espera", "Durabilidad");

        for (int i = 0; i < analytics.length - 1; i++) {
            analytics[i + 1] = String.format(formatanalytics, (int) data[0][i][0],
                    replications.get(0).getClassServerName((int) data[0][i][1]),
                    format.format(data[0][i][2]), format.format(data[1][i][2]),
                    format.format(data[0][i][3]), format.format(data[1][i][3]),
                    dformat.format(data[0][i][4]), dformat.format(data[1][i][4]),
                    dformat.format(data[0][i][5]), dformat.format(data[1][i][5]),
                    (int) data[0][i][6], data[1][i][6],
                    format.format(data[0][i][7]), format.format(data[1][i][7]));
        }

        servers += String.join("\n", analytics);
        servers += "\n\n\n";
    }

    public String getServersByType() {
        if (serversByType == null)
            setServersByType();

        return serversByType;
    }

    private void setServersByType() {
        Data[] set = new Data[replications.size()];

        for (int i = 0; i < set.length; i++) {
            set[i] = replications.get(i).getServerByTypeData();
        }

        double[][][] data = Data.mergeData(set);

        String[] header = {
                "", "Tiempo total de ocio", "Tiempo máximo de ocio", "Porcentaje de tiempo de ocio respecto al total",
                "Porcentaje del maximo de ocio respecto al tiempo total de ocio", "Tamaño máximo de la cola de espera"
        };

        int rows = header.length, columns = replications.get(0).getServerClassesNumber();
        String[] analytics = new String[rows + 1];

        serversByType = "Estadísticas por Servidor discriminadas por tipo\n\n";

        analytics = new String[rows];

        String f1 = "%-65s", f2 = "%-21s", f3 = "%-9s ± %-9s";

        for (int i = 0; i < rows; i++) {
            analytics[i] = String.format(f1, header[i]);
        }

        for (int i = 0; i < columns; i++) {
            analytics[0] += String.format(f2, replications.get(0).getClassServerName((int) data[0][0][i]));
            analytics[1] += String.format(f3, format.format(data[0][1][i]), format.format(data[1][1][i]));
            analytics[2] += String.format(f3, format.format(data[0][2][i]), format.format(data[1][2][i]));
            analytics[3] += String.format(f3, dformat.format(data[0][3][i]), dformat.format(data[1][3][i]));
            analytics[4] += String.format(f3, dformat.format(data[0][4][i]), dformat.format(data[1][4][i]));
            analytics[5] += String.format(f3, data[0][5][i], data[1][5][i]);
        }

        serversByType += String.join("\n", analytics);
        serversByType += "\n\n\n";
    }

    public void showReport() {
        String report = "Reporte replicacion de ejecuciones:\n\n" + getEntitys() + getServers() + getServersByType();

        System.out.println(report);
    }

    /**
     * used if statistics of parcial number of repetitions is needed
     */
    public void actualiceData() {
        setEntitys();
        setServers();
        setServersByType();
    }
}
