package com.holub.life;

import com.holub.life.feature.Feature;
import com.holub.life.feature.color.*;
import com.holub.life.feature.ttl.TTL2;
import com.holub.life.feature.ttl.TTL3;
import com.holub.life.feature.ttl.TTLDefault;
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

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[4][4].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[4][4]);
            outermostcell.setCellFeature(p0, bounds, RuleDefault.getInstance());

            for (int i = 1; i < 9; i++) {

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
        } catch (NoSuchFieldException | IllegalAccessException e) {
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

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[5][5].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[5][5]);
            outermostcell.setCellFeature(p0, bounds, RuleGnarl.getInstance());

            for (int i = 1; i < 9; i++) {

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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
    }

    @DisplayName("3-4 Rule Test")
    @Test
    void threefourRuleTest() {

        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();

        // Given
        List<Point> points = new ArrayList<>();
        Point p0 = new Point(436, 439);
        points.add(new Point(428, 431));
        points.add(new Point(436, 431));
        points.add(new Point(444, 431));
        points.add(new Point(428, 439));
        points.add(new Point(444, 439));
        points.add(new Point(428, 447));
        points.add(new Point(436, 447));
        points.add(new Point(444, 447));

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[6][6].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[6][6]);
            outermostcell.setCellFeature(p0, bounds, Rule34Life.getInstance());

            for (int i = 1; i < 9; i++) {

                if (i == 3 || i == 4) {
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
                actual.add(grid2[6][6].isAlive());

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
                actual.add(grid2[6][6].isAlive());

                outermostcell.clear();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
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

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[5][2].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[5][2]);
            outermostcell.setCellFeature(p0, bounds, RuleStains.getInstance());

            for (int i = 1; i < 9; i++) {

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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertEquals(actual, expected);
    }

    @DisplayName("TTL Default Test")
    @Test
    void ttlDefaultTest() {

        // Given
        Point p1 = new Point(149, 223);

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());
            outermostcell.userClicked(p1, r1);

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;
            outermostcell.setCellFeature(p1, bounds, TTLDefault.getInstance());

            Field field2 = outermostcell.getClass().getDeclaredField("grid");
            field2.setAccessible(true);
            Cell[][] grid = (Cell[][]) field2.get(outermostcell);

            Field field3 = grid[3][2].getClass().getDeclaredField("grid");
            field3.setAccessible(true);
            Cell[][] grid2 = (Cell[][]) field3.get(grid[3][2]);

            // When
            for (int i = 0; i < 1; i++) {
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
            }

            boolean actual = grid2[3][2].isAlive();
            boolean expected = false;

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("TTL 2 Test")
    @Test
    void ttl2Test() {

        // Given
        Point p1 = new Point(220, 151);
        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();
        expected.add(true);
        expected.add(false);

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
            for (int i = 0; i < 2; i++) {
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[2][3].isAlive());
            }

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("TTL 3 Test")
    @Test
    void ttl3Test() {

        // Given
        Point p1 = new Point(150, 150);
        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();
        expected.add(true);
        expected.add(true);
        expected.add(false);

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
            for (int i = 0; i < 3; i++) {
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[2][2].isAlive());
            }

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("TTL Infinite Test")
    @Test
    void ttlInfiniteTest() {

        // Given
        Point p1 = new Point(77, 77);
        List<Boolean> actual = new ArrayList<>();
        List<Boolean> expected = new ArrayList<>();
        expected.add(true);
        expected.add(true);
        expected.add(true);
        expected.add(true);
        expected.add(true);
        expected.add(true);

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
                outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
                outermostcell.transition();
                actual.add(grid2[1][1].isAlive());
            }

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Cell Color Test")
    @Test
    void getCellFeature() {

        List<Color> actual = new ArrayList<>();
        List<Color> expected = new ArrayList<>();

        expected.add(Colors.MEDIUM_RED);
        expected.add(Colors.GREEN);
        expected.add(Colors.MEDIUM_BLUE);
        expected.add(Colors.BLACK);
        expected.add(Colors.MEDIUM_RED);
        expected.add(Colors.GREEN);
        expected.add(Colors.MEDIUM_BLUE);
        expected.add(Colors.BLACK);

        // Given
        List<Point> points = new ArrayList<>();
        points.add(new Point(215, 222));
        points.add(new Point(221, 222));
        points.add(new Point(215, 230));
        points.add(new Point(221, 230));
        points.add(new Point(228, 222));
        points.add(new Point(235, 222));
        points.add(new Point(228, 230));
        points.add(new Point(235, 230));

        try {
            // Given
            Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
            field.setAccessible(true);

            Cell outermostcell = (Cell) field.get(Universe.instance());

            for (int i = 0; i < 4; i++) {
                outermostcell.userClicked(points.get(i), r1);
            }

            Rectangle bounds = getBounds();
            bounds.x = 0;
            bounds.y = 0;

            outermostcell.setCellFeature(points.get(0), bounds, ColorRed.getInstance());
            outermostcell.setCellFeature(points.get(1), bounds, ColorGreen.getInstance());
            outermostcell.setCellFeature(points.get(2), bounds, ColorBlue.getInstance());
            outermostcell.setCellFeature(points.get(3), bounds, ColorBlack.getInstance());
            outermostcell.setCellFeature(points.get(4), bounds, ColorRed.getInstance());
            outermostcell.setCellFeature(points.get(5), bounds, ColorGreen.getInstance());
            outermostcell.setCellFeature(points.get(6), bounds, ColorBlue.getInstance());
            outermostcell.setCellFeature(points.get(7), bounds, ColorBlack.getInstance());

            // When
            outermostcell.figureNextState(Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY);
            outermostcell.transition();

            for (int i = 4; i < 8; i++) {
                outermostcell.userClicked(points.get(i), r1);
            }

            for (int i = 0; i < 8; i++) {
                List<Feature> features = new ArrayList<>();
                features = outermostcell.getCellFeature(points.get(i), r1);
                for (Feature f : features) {
                    if (f instanceof ColorBehavior) {
                        actual.add(((ColorBehavior) f).getLiveColor());
                        break;
                    }
                }
            }

            // Then
            assertEquals(actual, expected);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}