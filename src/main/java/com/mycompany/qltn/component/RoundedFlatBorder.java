/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.component;

import com.formdev.flatlaf.ui.FlatBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 *
 * @author ACER
 */
public class RoundedFlatBorder extends FlatBorder {
    private final int radius;
    private final Color borderColor;
    private final int thickness; // Độ dày border

    public RoundedFlatBorder(int radius, Color borderColor, int thickness) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor); // Đặt màu viền
        g2.setStroke(new BasicStroke(thickness)); // Đặt độ dày viền
        g2.drawRoundRect(x + thickness / 2, y + thickness / 2, 
                         width - thickness, height - thickness, radius, radius);
        g2.dispose();
    }
}
