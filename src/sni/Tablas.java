/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author vladi
 */
public class Tablas {
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width 
            
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            
            if (width > 200) {
                width = 200;
            }
            
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    
    public String selected(JTable tabla) {
        String id = null;
        TableModel tablaModelo;
        tablaModelo = (TableModel) tabla.getModel();
        id = String.valueOf(tablaModelo.getValueAt(tabla.getSelectedRow(), 0));

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un dato", "No seleccionó un dato", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return id;
        }
    }
}
