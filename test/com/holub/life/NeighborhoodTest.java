package com.holub.life;

import com.holub.life.feature.Feature;
import com.holub.life.feature.color.ColorBehavior;
import com.holub.life.feature.color.ColorGreen;
import com.holub.life.feature.ttl.TTL2;
import com.holub.life.feature.ttl.TTL3;
import com.holub.life.feature.ttl.TTLInfinite;
import com.holub.life.feature.rule.RuleDefault;
import com.holub.life.feature.rule.RuleGnarl;
import com.holub.life.feature.rule.Rule34Life;
import com.holub.life.feature.rule.RuleStains;

import java.util.Collections;

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

    @AfterEach
    void clean() {
        Field field = null;
        try {
            field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.clear();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Default Rule Test")
    @Test
    void defaultRuleTest() {

        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();

        // Given
        List<Point> points = new ArrayList<>();
        Point p0 = new Point(293, 293);
        points.add(new Point(284, 287));
        points.add(new Point(292, 287));
        points.add(new Point(300, 287));
        points.add(new Point(284, 295));
        points.add(new Point(300, 295));
        points.add(new Point(284, 303));
        points.add(new Point(292, 303));
        points.add(new Point(300, 303));

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;

            for (int i = 1; i < 9; i++) {

                Field field2 = outermostcell.getClass().getDeclaredField("grid");
                field2.setAccessible(true);
                Cell[][] grid = (Cell[][]) field2.get(outermostcell);

                Field field3 = grid[4][4].getClass().getDeclaredField("grid");
                field3.setAccessible(true);
                Cell[][] grid2 = (Cell[][]) field3.get(grid[4][4]);
                outermostcell.setCellFeature(p0, bounds, RuleDefault.getInstance());

                if (i == 3) {
                    expected.add(true);
                    expected.add(true);
                } else if (i == 2) {
                    expected.add(false);
                    expected.add(true);
                } else {
                    expected.add(false);
                    expected.add(false);
                }

                Collections.shuffle(points);

                // When
                // Target Cell(p0) is not alive
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[4][4].isAlive());

                outermostcell.clear();

                // Target Cell(p0) is alive
                outermostcell.userClicked(p0, r1);
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[4][4].isAlive());

                outermostcell.clear();
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
    }

    @DisplayName("Gnarl Rule Test")
    @Test
    void gnarlRuleTest() {

        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();

        // Given
        List<Point> points = new ArrayList<>();
        Point p0 = new Point(364, 367);
        points.add(new Point(356, 358));
        points.add(new Point(364, 358));
        points.add(new Point(372, 358));
        points.add(new Point(356, 367));
        points.add(new Point(372, 367));
        points.add(new Point(356, 375));
        points.add(new Point(364, 375));
        points.add(new Point(372, 375));

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;

            for (int i = 1; i < 9; i++) {

                Field field2 = outermostcell.getClass().getDeclaredField("grid");
                field2.setAccessible(true);
                Cell[][] grid = (Cell[][]) field2.get(outermostcell);

                Field field3 = grid[5][5].getClass().getDeclaredField("grid");
                field3.setAccessible(true);
                Cell[][] grid2 = (Cell[][]) field3.get(grid[5][5]);
                outermostcell.setCellFeature(p0, bounds, RuleGnarl.getInstance());

                if (i == 1) {
                    expected.add(true);
                    expected.add(true);
                } else {
                    expected.add(false);
                    expected.add(false);
                }

                Collections.shuffle(points);

                // When
                // Target Cell(p0) is not alive
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[5][5].isAlive());

                outermostcell.clear();

                // Target Cell(p0) is alive
                outermostcell.userClicked(p0, r1);
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[5][5].isAlive());

                outermostcell.clear();
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
    }

    @DisplayName("3-4 Rule Test")
    @Test
    void threefourRuleTest() {

        // Given
        Point p1 = new Point(436, 437);
        Point p2 = new Point(443, 437);
        Point p3 = new Point(428, 438);
        Point p4 = new Point(443, 430);
        Point p5 = new Point(437, 431);
        Point p6 = new Point(429, 431);
        Point p7 = new Point(420, 430);
        Point p8 = new Point(443, 422);
        Point p9 = new Point(437, 423);
        Point p0 = new Point(428, 423);

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);
            outermostcell.userClicked(p4, r1);
            outermostcell.userClicked(p5, r1);
            outermostcell.userClicked(p6, r1);
            outermostcell.userClicked(p7, r1);
            outermostcell.userClicked(p9, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p1, bounds, Rule34Life.getInstance());
            outermostcell.setCellFeature(p2, bounds, Rule34Life.getInstance());
            outermostcell.setCellFeature(p3, bounds, Rule34Life.getInstance());
            outermostcell.setCellFeature(p4, bounds, RuleDefault.getInstance());
            outermostcell.setCellFeature(p5, bounds, RuleDefault.getInstance());
            outermostcell.setCellFeature(p6, bounds, Rule34Life.getInstance());
            outermostcell.setCellFeature(p7, bounds, RuleDefault.getInstance());
            outermostcell.setCellFeature(p8, bounds, RuleDefault.getInstance());
            outermostcell.setCellFeature(p9, bounds, RuleDefault.getInstance());
            outermostcell.setCellFeature(p0, bounds, RuleDefault.getInstance());

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[6][6].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[6][6]);

            // When
            for (int i = 0; i < 1; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            List<Boolean> actual = new ArrayList<>();
            actual.add(grid2[6][6].isAlive());
            actual.add(grid2[6][7].isAlive());
            actual.add(grid2[6][5].isAlive());
            actual.add(grid2[5][7].isAlive());
            actual.add(grid2[5][6].isAlive());
            actual.add(grid2[5][5].isAlive());
            actual.add(grid2[5][4].isAlive());
            actual.add(grid2[4][7].isAlive());
            actual.add(grid2[4][6].isAlive());
            actual.add(grid2[4][5].isAlive());

            List<Boolean> expected = new ArrayList<>();
            expected.add(true);
            expected.add(true);
            expected.add(true);
            expected.add(true);
            expected.add(false);
            expected.add(true);
            expected.add(false);
            expected.add(true);
            expected.add(true);
            expected.add(false);

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Stains Rule Test")
    @Test
    void stainsRuleTest() {

        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();

        // Given
        List<Point> points = new ArrayList<>();
        Point p0 = new Point(148, 367);
        points.add(new Point(140, 359));
        points.add(new Point(148, 359));
        points.add(new Point(156, 359));
        points.add(new Point(140, 367));
        points.add(new Point(156, 367));
        points.add(new Point(140, 375));
        points.add(new Point(148, 375));
        points.add(new Point(156, 375));

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;

            for (int i = 1; i < 9; i++) {
                Field field2 = outermostcell.getClass().getDeclaredField("grid");
                field2.setAccessible(true);
                Cell[][] grid = (Cell[][]) field2.get(outermostcell);

                Field field3 = grid[5][2].getClass().getDeclaredField("grid");
                field3.setAccessible(true);
                Cell[][] grid2 = (Cell[][]) field3.get(grid[5][2]);
                outermostcell.setCellFeature(p0, bounds, RuleStains.getInstance());

                if (i == 1 || i == 4) {
                    expected.add(false);
                    expected.add(false);
                } else if (i == 2 || i == 5) {
                    expected.add(false);
                    expected.add(true);
                } else { // i == 3 || i == 6 || i == 7 || i == 8
                    expected.add(true);
                    expected.add(true);
                }

                Collections.shuffle(points);

                // When
                // Target Cell(p0) is not alive
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[5][2].isAlive());

                outermostcell.clear();

                // Target Cell(p0) is alive
                outermostcell.userClicked(p0, r1);
                for (int j = 0; j < i; j++) {
                    outermostcell.userClicked(points.get(j), r1);
                    outermostcell.setCellFeature(points.get(j), bounds, RuleDefault.getInstance());
                }
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[5][2].isAlive());

                outermostcell.clear();
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
    }

    @DisplayName("TTL 2 Test")
    @Test
    void ttl2Test() {

        // Given
        Point p1 = new Point(220, 151);

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

            Field field3 = grid[2][3].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[2][3]);

            // When
            for (int i = 0; i < 1; i++) {
                if (outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY)) {
                    outermostcell.transition();
                }
            }

            boolean actual = grid2[2][3].isAlive();
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

        // Given
        Point p1 = new Point(150, 150);

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p1, bounds, TTL3.getInstance());

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

        // Given
        Point p1 = new Point(77, 77);

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p1, bounds, TTLInfinite.getInstance());

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

        // Given
        Point p1 = new Point(221, 222);
        Point p2 = new Point(228, 222);
        Point p3 = new Point(215, 222);

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