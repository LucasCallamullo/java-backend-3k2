package utnfc.isi.back.infra;

import org.h2.tools.RunScript;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

public final class DatabaseInitializer {

    private static final String URL = "jdbc:h2:mem:boardgames;DB_CLOSE_DELAY=-1;MODE=LEGACY";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static final String DDL_CLASSPATH = "/sql/ddl_board_games.sql";

    private DatabaseInitializer() {}

    public static void recreateSchema() {
        try (Connection cn = DriverManager.getConnection(URL, USER, PASS)) {
            var in = DatabaseInitializer.class.getResourceAsStream(DDL_CLASSPATH);
            if (in == null) {
                throw new IllegalStateException("❌ No se encontró el archivo " + DDL_CLASSPATH);
            }

            // Ejecuta todo el script DDL
            try (var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                RunScript.execute(cn, reader);
            }

            // ===============================
            // VALIDACIÓN POST-CREACIÓN
            // ===============================
            validateSchema(cn);

            System.out.println("✅ Base de datos creada correctamente desde " + DDL_CLASSPATH);

        } catch (Exception e) {
            throw new RuntimeException("❌ Error ejecutando DDL con RunScript", e);
        }
    }

    private static void validateSchema(Connection cn) throws Exception {
        // Verificar que las tablas principales existan
        String[] requiredTables = {"BOARD_GAMES", "DESIGNERS", "PUBLISHERS", "CATEGORIES"};
        for (String table : requiredTables) {
            try (var ps = cn.prepareStatement(
                    "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?")) {
                ps.setString(1, table);
                try (var rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        throw new IllegalStateException("❌ Falta la tabla " + table + " en el esquema.");
                    }
                }
            }
        }

        // Verificar que las secuencias se hayan creado
        String[] requiredSeqs = {
                "SEQ_BOARD_GAME_ID", "SEQ_DESIGNER_ID", "SEQ_PUBLISHER_ID", "SEQ_CATEGORY_ID"
        };
        for (String seq : requiredSeqs) {
            try (var ps = cn.prepareStatement(
                    "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_NAME = ?")) {
                ps.setString(1, seq);
                try (var rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        throw new IllegalStateException("❌ Falta la secuencia " + seq + " en el esquema.");
                    }
                }
            }
        }
    }
}


