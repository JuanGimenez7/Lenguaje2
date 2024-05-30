package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CCarro {

    int idMarca;
    int idTipo;
    int idAño;
    int idColor;

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public void setIdAño(int idAño) {
        this.idAño = idAño;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public void mostrarMarcaCombo(JComboBox comboMarca) {
        Clases.CConexion objetoConexion = new Clases.CConexion();

        String sql = "select * from marca";

        Statement st;
        try {
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);
            comboMarca.removeAllItems();

            while (rs.next()) {
                String nombreMarca = rs.getString("marca");
                this.setIdMarca(rs.getInt("id"));

                comboMarca.addItem(nombreMarca);
                comboMarca.putClientProperty(nombreMarca, idMarca);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar marca" + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void mostrarTipoCombo(JComboBox comboTipo) {
        Clases.CConexion objetoConexion = new Clases.CConexion();

        String sql = "select * from tipo";

        Statement st;
        try {
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);
            comboTipo.removeAllItems();

            while (rs.next()) {
                String nombreTipo = rs.getString("tipo");
                this.setIdTipo(rs.getInt("id"));

                comboTipo.addItem(nombreTipo);
                comboTipo.putClientProperty(nombreTipo, idTipo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar tipo" + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void mostrarAñoCombo(JComboBox comboAño) {
        Clases.CConexion objetoConexion = new Clases.CConexion();

        String sql = "select * from año";

        Statement st;
        try {
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);
            comboAño.removeAllItems();

            while (rs.next()) {
                String nombreAño = rs.getString("año");
                this.setIdAño(rs.getInt("id"));

                comboAño.addItem(nombreAño);
                comboAño.putClientProperty(nombreAño, idAño);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar año" + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void mostrarColorCombo(JComboBox comboColor) {
        Clases.CConexion objetoConexion = new Clases.CConexion();

        String sql = "select * from color";

        Statement st;
        try {
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);
            comboColor.removeAllItems();

            while (rs.next()) {
                String nombreColor = rs.getString("color");
                this.setIdColor(rs.getInt("id"));

                comboColor.addItem(nombreColor);
                comboColor.putClientProperty(nombreColor, idColor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar color" + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void agregarCarro(JTextField placa, JComboBox marca, JComboBox tipo, JComboBox año, JComboBox color, JTextField pVenta, JTextField pAlquiler, File foto) {

        CConexion objetoConexion = new CConexion();
        String consulta = "insert into carros (placa, fkmarca, fktipo, fkaño, fkcolor, $_venta, $_alquiler, foto) values (?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            FileInputStream fis = new FileInputStream(foto);

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, placa.getText());

            int idMarca = (int) marca.getClientProperty(marca.getSelectedItem());
            cs.setInt(2, idMarca);
            int idTipo = (int) tipo.getClientProperty(tipo.getSelectedItem());
            cs.setInt(3, idTipo);
            int idAño = (int) año.getClientProperty(año.getSelectedItem());
            cs.setInt(4, idAño);
            int idColor = (int) color.getClientProperty(color.getSelectedItem());
            cs.setInt(5, idColor);
            cs.setInt(6, Integer.parseInt(pVenta.getText()));
            cs.setInt(7, Integer.parseInt(pAlquiler.getText()));
            
            cs.setBinaryStream(8, fis, (int)foto.length());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó el usuario correctamente");
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al guardar:"+e.toString());
        }
    }
    
    public void mostrarUsuarios(JTable tablaTotalCarros){
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("Placa");
        modelo.addColumn("Marca");
        modelo.addColumn("Tipo");
        modelo.addColumn("Año");
        modelo.addColumn("Color");
        modelo.addColumn("$ Venta");
        modelo.addColumn("$ Alquiler (por día)");
        modelo.addColumn("Foto");
    }
}
