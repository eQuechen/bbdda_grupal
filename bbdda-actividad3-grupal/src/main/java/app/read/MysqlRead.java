package app.read;

import com.emilio.anabel.minerva.dao.IReadDao;

public class MysqlRead {

    private MysqlRead() {}

    public static void selectEstaciones() {

        String estacionesQuery = """
        SELECT
            e.id_estacion,
            e.tipo_estacion,
            e.margen_estacion,
            e.horario_estacion,
            e.direccion_estacion,
            e.latitud_estacion,
            e.longitud_estacion,
            p.id_provincia,
            p.nombre_provincia,
            m.id_municipio,
            m.nombre_municipio,
            l.id_localidad,
            l.nombre_localidad,
            cp.id_codigo_postal,
            cp.numero_codigo_postal,
            emp.id_empresa,
            emp.nombre_empresa,
            c.id_carburante,
            c.tipo_carburante,
            pc.precio_carburante,
            pc.fecha_act_precio_carburante
        FROM
            estacion e
        LEFT JOIN localidad l ON e.id_localidad = l.id_localidad
        LEFT JOIN relacion_cp_localidad rcl ON l.id_localidad = rcl.id_localidad
        LEFT JOIN codigo_postal cp ON rcl.id_codigo_postal = cp.id_codigo_postal
        LEFT JOIN municipio m ON cp.id_municipio = m.id_municipio
        LEFT JOIN provincia p ON m.id_provincia = p.id_provincia
        LEFT JOIN empresa emp ON e.id_empresa = emp.id_empresa
        LEFT JOIN precio_carburante pc ON e.id_estacion = pc.id_estacion
        LEFT JOIN carburante c ON pc.id_carburante = c.id_carburante
        WHERE
            e.tipo_estacion IN ('Terrestre', 'Maritima')
        ORDER BY e.id_estacion;
        """;

        String petrolerasQuery = """
        SELECT
            emp.id_empresa,
            emp.nombre_empresa,
            e.id_estacion
        FROM
            empresa emp
        LEFT JOIN estacion e ON e.id_empresa = emp.id_empresa;
        """;

        String carburantesQuery = """
        SELECT
            c.id_carburante,
            c.tipo_carburante
        FROM
            carburante c;
        """;

        String preciosCarburantesQuery = """
        SELECT
            pc.id_estacion,
            pc.id_carburante,
            pc.precio_carburante,
            pc.fecha_act_precio_carburante
        FROM
            precio_carburante pc
        ORDER BY pc.id_estacion, pc.id_carburante;
        """;

        String ubicacionesQuery = """
        SELECT
            cp.id_codigo_postal,
            cp.numero_codigo_postal,
            l.id_localidad,
            l.nombre_localidad,
            m.id_municipio,
            m.nombre_municipio,
            p.id_provincia,
            p.nombre_provincia
        FROM
            codigo_postal cp
        LEFT JOIN relacion_cp_localidad rcl ON cp.id_codigo_postal = rcl.id_codigo_postal
        LEFT JOIN localidad l ON rcl.id_localidad = l.id_localidad
        LEFT JOIN municipio m ON cp.id_municipio = m.id_municipio
        LEFT JOIN provincia p ON m.id_provincia = p.id_provincia;
        """;

        IReadDao.selectEstaciones(estacionesQuery);
        IReadDao.selectPetroleras(petrolerasQuery);
        IReadDao.selectCarburantes(carburantesQuery);
        IReadDao.selectPreciosCarburantes(preciosCarburantesQuery);
        IReadDao.selectUbicaciones(ubicacionesQuery);


    }
}
