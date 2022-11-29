package com.holub.life;

import com.holub.life.feature.Feature;
import com.holub.life.feature.color.ColorBehavior;
import com.holub.life.feature.color.ColorGreen;
import com.holub.ui.Colors;
import com.holub.ui.MenuSite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest extends JFrame {

    @DisplayName("Next State Test")
    @Test
    void figureNextState() {

        // Given
        //defaultset();
        Universe instance = Universe.instance();

        Point p1 = new Point(221,222);
        Point p2 = new Point(228,222);
        Point p3 = new Point(228,229);
        Point p4 = new Point(221,230);
        Rectangle r1 = new Rectangle(0,0,512,512);

        try {
            Field field = instance.getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell)field.get(instance);
            outermostcell.userClicked(p1, r1);
            //outermostcell.userClicked(p2, r1);
            //outermostcell.userClicked(p3, r1);
            //outermostcell.userClicked(p4, r1);

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);

            Cell[][] grid = (Cell[][])field2.get(outermostcell);

            // When
            boolean actual = outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
            boolean expected = true;

            // Then
            assertEquals(actual,expected);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Transition Test")
    @Test
    void transition() {
    }

    @DisplayName("Cell Color Test")
    @Test
    void getCellFeature() {

        // Given
        defaultset();
        Universe instance = Universe.instance();

        Point p1 = new Point(221,222);
        Point p2 = new Point(228,222);
        Point p3 = new Point(215,222);
        Rectangle r1 = new Rectangle(0,0,512,512);

        try {
            // Given
            Field field = instance.getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell)field.get(instance);
            outermostcell.userClicked(p1, r1);
            outermostcell.userClicked(p2, r1);
            outermostcell.userClicked(p3, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p3, bounds, ColorGreen.getInstance());

            if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)){
                outermostcell.transition();
            }

            // When
            List<Feature> features = new ArrayList<>();
            Color expected = Colors.GREEN;
            if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)){
                if (outermostcell.transition()){
                    features = outermostcell.getCellFeature(p3, r1);
                    for (Feature f: features) {
                        if (f instanceof ColorBehavior) {
                            Color actual = ((ColorBehavior) f).getLiveColor();

                            // Then
                            assertEquals(actual, expected);
                        }
                    }
                }
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void defaultset() {

        MenuSite.establish(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(Universe.instance(), BorderLayout.CENTER);
        pack();
        setVisible(true);

    }
}