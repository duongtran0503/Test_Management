package com.mycompany.qltn_mvc.UI;

import com.mycompany.qltn_mvc.UI.ButtonActionPanel;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionPanelRenderer extends ButtonActionPanel implements TableCellRenderer {

    public ActionPanelRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}