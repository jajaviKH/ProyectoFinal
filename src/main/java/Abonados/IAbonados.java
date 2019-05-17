/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abonados;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author javi
 */
public interface IAbonados {
    List<AbonadosVO> getAll() throws SQLException;
    int insertPersona (AbonadosVO persona) throws SQLException;
    int insertPersona (List<AbonadosVO> lista) throws SQLException;
     int deletePersona (AbonadosVO p) throws SQLException;
    int deletePersona() throws SQLException;
    int updatePersona (int codAbonados, AbonadosVO nuevosDatos) throws SQLException;
}
