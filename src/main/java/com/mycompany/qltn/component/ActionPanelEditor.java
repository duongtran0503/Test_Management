package com.mycompany.qltn.component;

import com.mycompany.qltn.component.ButtonActionPanel;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

public class ActionPanelEditor extends AbstractCellEditor implements TableCellEditor {

    public ButtonActionPanel getPanel() {
        return panel;
    }

    public int getRow() {
        return row;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public ChangeEvent getChangeEvent() {
        return changeEvent;
    }

    public void setPanel(ButtonActionPanel panel) {
        this.panel = panel;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public void setChangeEvent(ChangeEvent changeEvent) {
        this.changeEvent = changeEvent;
    }

    private ButtonActionPanel panel;
    private int row;

    public ActionPanelEditor() {
        panel = new ButtonActionPanel();
        
    }
     public void fireEditStop() {
      fireEditingStopped();
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