/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abonados;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author javie
 */
public class AbonadosDAO implements IAbonados {

    private Connection con = null;

    public AbonadosDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public ArrayList<AbonadosVO> getAll() throws SQLException {
        ArrayList<AbonadosVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from abonados");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                AbonadosVO p = new AbonadosVO();
                // Recogemos los datos de la persona, guardamos en un objeto
                p.setDNI(res.getString("DNI"));
                p.setNombre(res.getString("nombre"));
                p.setApellidos(res.getString("apellidos"));               
                p.setPinAbonados(res.getString("pinAbonados"));
                p.setTarjetaCredito(res.getString("tarjetaCredito"));
                p.setEmail(res.getString("email"));
                p.setTipoAbonados(res.getInt("tipoAbonado"));
                p.setMatricula(res.getString("matricula"));
                p.setFechaInicioAbono(res.getDate("fechaInicioAbono").toLocalDate());
                p.setFechaFinAbono(res.getDate("fechaFinAbono").toLocalDate());
                //Añadimos el objeto a la lista
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public AbonadosVO findByPk(String DNI) throws SQLException {
        ResultSet res = null;
        AbonadosVO abonados = new AbonadosVO();

        String sql = "select * from abonados where DNI=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, DNI);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos de la persona, guardamos en un objeto

                abonados.setNombre(res.getString("nombre"));
                abonados.setApellidos(res.getString("apellidos"));
                abonados.setDNI(res.getString("DNI"));
                abonados.setPinAbonados(res.getString("pinAbonados"));
                abonados.setTarjetaCredito(res.getString("tarjetaCredito"));
                abonados.setEmail(res.getString("email"));
                abonados.setTipoAbonados(res.getInt("tipoAbonado"));
                abonados.setMatricula(res.getString("matricula"));
                abonados.setFechaInicioAbono(res.getDate("fechaInicioAbono").toLocalDate());
                abonados.setFechaFinAbono(res.getDate("fechaFinAbono").toLocalDate());
                return abonados;
            }

            return null;
        }
    }

    @Override
    public int insertAbonados(AbonadosVO abonados) throws SQLException {
        int numFilas = 0;
        String sql = "insert into abonados values (?,?,?,?,?,?,?,?,?,?)";

        if (findByPk(abonados.getDNI()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, abonados.getDNI());
                prest.setString(2, abonados.getNombre());
                prest.setString(3, abonados.getApellidos());
                prest.setString(4, abonados.getPinAbonados());
                prest.setString(5, abonados.getTarjetaCredito());
                prest.setString(6, abonados.getEmail());
                prest.setInt(7, abonados.getTipoAbonados());
                prest.setString(8, abonados.getMatricula());
                prest.setDate(9, Date.valueOf(abonados.getFechaInicioAbono()));
                prest.setDate(10, Date.valueOf(abonados.getFechaFinAbono()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int insertAbonados(List<AbonadosVO> lista) throws SQLException {
        int numFilas = 0;

        for (AbonadosVO tmp : lista) {
            numFilas += insertAbonados(tmp);
        }

        return numFilas;
    }

    @Override
    public int deleteAbonados(AbonadosVO abonados) throws SQLException {
        int numFilas = 0;

        String sql = "delete from abonados where DNI = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, abonados.getDNI());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deleteAbonados() throws SQLException {
        String sql = "delete from abonados";

        int nfilas = 0;

        // Preparamos el borrado de datos  mediante un Statement
        // No hay parámetros en la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecución de la sentencia
            nfilas = st.executeUpdate(sql);
        }

        // El borrado se realizó con éxito, devolvemos filas afectadas
        return nfilas;
    }

    @Override
    public int updateAbonados(String DNI, AbonadosVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update abonados set dni=?, nombre = ?, apellidos = ?,pinAbonados=?,"
                + "tarjetaCredito=?,email=?,tipoAbonado=?,"
                + "fechaInicioAbono=?, fechaFinAbono=?   where DNI=?";

        if (findByPk(DNI) == null) {
            // La persona a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, nuevosDatos.getDNI());
                prest.setString(10, DNI);
                prest.setString(2, nuevosDatos.getNombre());
                prest.setString(3, nuevosDatos.getApellidos());
                prest.setString(4, nuevosDatos.getPinAbonados());
                prest.setString(5, nuevosDatos.getTarjetaCredito());
                prest.setString(6, nuevosDatos.getEmail());
                prest.setInt(7, nuevosDatos.getTipoAbonados());
                /*prest.setString(7, nuevosDatos.getMatricula());
                System.out.println(nuevosDatos.getMatricula());*/
                prest.setDate(8, Date.valueOf(nuevosDatos.getFechaInicioAbono()));
                prest.setDate(9, Date.valueOf(nuevosDatos.getFechaFinAbono()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

}
