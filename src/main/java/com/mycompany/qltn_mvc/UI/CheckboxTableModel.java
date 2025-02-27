package com.mycompany.qltn_mvc.UI;


import javax.swing.table.DefaultTableModel;



public class CheckboxTableModel extends DefaultTableModel {

    public CheckboxTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class; // Cột đầu tiên là checkbox
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0 || super.isCellEditable(row, column); // Cho phép chỉnh sửa cột checkbox và các cột khác
    }
}