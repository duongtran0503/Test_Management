package com.mycompany.qltn_mvc.UI;

import com.mycompany.qltn_mvc.UI.ButtonActionPanel;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class ActionPanelEditor extends AbstractCellEditor implements TableCellEditor {

    private ButtonActionPanel panel;
    private int row;

    public ActionPanelEditor() {
        panel = new ButtonActionPanel();
        panel.getButtonEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit button clicked in editor for row: " + row);
                fireEditingStopped();
            }
        });
        panel.getButtonDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete button clicked in editor for row: " + row);
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Action"; // Giá trị trả về không quan trọng
    }
}