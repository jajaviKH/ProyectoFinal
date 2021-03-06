/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abonados;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author javi
 */
public interface IAbonados {
    ArrayList<AbonadosVO> getAll() throws SQLException;
    AbonadosVO findByPk(String DNI) throws SQLException;
    int insertAbonados (AbonadosVO persona) throws SQLException;
    int insertAbonados  (List<AbonadosVO> lista) throws SQLException;
     int deleteAbonados  (AbonadosVO p) throws SQLException;
    int deleteAbonados () throws SQLException;
    int updateAbonados  (String DNI, AbonadosVO nuevosDatos) throws SQLException;
}
