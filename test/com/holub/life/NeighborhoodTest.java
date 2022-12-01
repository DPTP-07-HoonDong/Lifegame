package com.holub.life;

import com.holub.life.feature.Feature;
import com.holub.life.feature.color.ColorBehavior;
import com.holub.life.feature.color.ColorGreen;
import com.holub.life.feature.ttl.TTL2;
import com.holub.life.feature.ttl.TTL3;
import com.holub.life.feature.ttl.TTLInfinite;
import com.holub.ui.Colors;
import com.holub.ui.MenuSite;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NeighborhoodTest extends JFrame {

    // Given
    Point p1 = new Point(221, 222);
    Point p2 = new Point(228, 222);
    Point p3 = new Point(215, 222);
    Point p4 = new Point(150, 150);
    Point p5 = new Point(77, 77);

    Rectangle r1 = new Rectangle(0, 0, 512, 512);

    @BeforeAll
    void init() {
        MenuSite.establish(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(Universe.instance(), BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    @DisplayName("TTL 2 Test")
    @Test
    void ttl2Test() {

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p1, bounds, TTL2.getInstance());

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[3][3].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[3][3]);

            // When
            for (int i = 0; i < 1; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            boolean actual = grid2[3][3].isAlive();
            boolean expected = true;

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("TTL 3 Test")
    @Test
    void ttl3Test() {
        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p4, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p4, bounds, TTL3.getInstance());

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[2][2].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[2][2]);

            // When
            for (int i = 0; i < 2; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            boolean actual = grid2[2][2].isAlive();
            boolean expected = true;

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("TTL Infinite Test")
    @Test
    void ttlInfiniteTest() {
        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p5, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p5, bounds, TTLInfinite.getInstance());

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[1][1].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[1][1]);

            // When
            for (int i = 0; i < 6; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            boolean actual = grid2[1][1].isAlive();
            boolean expected = true;

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Cell Color Test")
    @Test
    void getCellFeature() {

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);
            outermostcell.userClicked(p2, r1);
            outermostcell.userClicked(p3, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p3, bounds, ColorGreen.getInstance());

            // When
            for (int i = 0; i < 3; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            List<Feature> features = new ArrayList<>();
            Color expected = Colors.GREEN;
            Color actual = null;
            features = outermostcell.getCellFeature(p3, r1);
            for (Feature f : features) {
                if (f instanceof ColorBehavior) {
                    actual = ((ColorBehavior) f).getLiveColor();
                    break;
                }
            }

            // Then
            assertEquals(actual, expected);
            
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}