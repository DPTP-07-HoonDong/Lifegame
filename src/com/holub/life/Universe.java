package com.holub.life;

import java.io.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.holub.io.Files;
import com.holub.life.feature.Feature;
import com.holub.life.feature.color.*;
import com.holub.life.feature.rule.*;
import com.holub.life.feature.ttl.TTL2;
import com.holub.life.feature.ttl.TTL3;
import com.holub.life.feature.ttl.TTLDefault;
import com.holub.life.feature.ttl.TTLInfinite;
import com.holub.ui.MenuSite;

/**
 * The Universe is a mediator that sits between the Swing
 * event model and the Life classes. It is also a singleton,
 * accessed via Universe.instance(). It handles all
 * Swing events and translates them into requests to the
 * outermost Neighborhood. It also creates the Composite
 * Neighborhood.
 * {@code @include} /etc/license.txt
 */

public class Universe extends JPanel {
    private final Cell outermostCell;

    private static Point lastClickedPoint;

    private static final Universe theInstance = new Universe();

    /**
     * The default height and width of a Neighborhood in cells.
     * If it's too big, you'll run too slowly because
     * you have to update the entire block as a unit, so there's more
     * to do. If it's too small, you have too many blocks to check.
     * I've found that 8 is a good compromise.
     */
    private static final int DEFAULT_GRID_SIZE = 8;

    /**
     * The size of the smallest "atomic" cell---a Resident object.
     * This size is extrinsic to a Resident (It's passed into the
     * Resident's "draw yourself" method.
     */
    private static final int DEFAULT_CELL_SIZE = 8;

    // The constructor is private so that the universe can be created
    // only by an outer-class method [Neighborhood.createUniverse()].

    private Universe() {
        // Create the nested Cells that comprise the "universe." A bug
        // in the current implementation causes the program to fail
        // miserably if the overall size of the grid is too big to fit
        // on the screen.

        outermostCell = new Neighborhood(
                DEFAULT_GRID_SIZE,
                new Neighborhood(
                        DEFAULT_GRID_SIZE,
                        new Resident()
                )
        );

        final Dimension PREFERRED_SIZE = new Dimension(
                outermostCell.widthInCells() * DEFAULT_CELL_SIZE,
                outermostCell.widthInCells() * DEFAULT_CELL_SIZE
        );

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Make sure that the cells fit evenly into the
                // total grid size so that each cell will be the
                // same size. For example, in a 64x64 grid, the
                // total size must be an even multiple of 63.

                Rectangle bounds = getBounds();
                bounds.height /= outermostCell.widthInCells();
                bounds.height *= outermostCell.widthInCells();
                bounds.width = bounds.height;
                setBounds(bounds);
            }
        });

        setBackground(Color.white);
        setPreferredSize(PREFERRED_SIZE);
        setMaximumSize(PREFERRED_SIZE);
        setMinimumSize(PREFERRED_SIZE);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { //{=Universe.mouse}
                lastClickedPoint = e.getPoint();
                Rectangle bounds = getBounds();
                bounds.x = 0;
                bounds.y = 0;
                if (e.getButton() == MouseEvent.BUTTON1) {
                    outermostCell.userClicked(lastClickedPoint, bounds);
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    MenuSite.showPopup(
                            Universe.instance(),
                            lastClickedPoint,
                            outermostCell.getCellFeature(e.getPoint(), bounds)
                    );
                }
            }
        });

        MenuSite.addLine(
                this, false, "Grid", "Clear",
                e -> {
                    outermostCell.clear();
                    repaint();
                }
        );

        MenuSite.addLine(this, false, "Grid", "Load", e -> doLoad()); // {=Universe.load.setup}
        MenuSite.addLine(this, false, "Grid", "Store", e -> doStore());
        MenuSite.addLine(this, false, "Grid", "Exit", e -> System.exit(0));

//        MenuSite.addLine(this, true, "Dummy", "Default", e -> setCellFeature(Feature.DUMMY));
//        MenuSite.addLine(this, true, "Dummy", "Other", e -> setCellFeature(Feature.DUMMY_OTHER));

        MenuSite.addLine(this, true, "TTL", "Default (1)", e -> setCellFeature(TTLDefault.getInstance()));
        MenuSite.addLine(this, true, "TTL", "2", e -> setCellFeature(TTL2.getInstance()));
        MenuSite.addLine(this, true, "TTL", "3", e -> setCellFeature(TTL3.getInstance()));
        MenuSite.addLine(this, true, "TTL", "Infinite", e -> setCellFeature(TTLInfinite.getInstance()));

        MenuSite.addLine(this, true, "Rule", "Default (3 / 2-3)", e -> setCellFeature(RuleDefault.getInstance()));
        MenuSite.addLine(this, true, "Rule", "Gnarl (1 / 1)", e -> setCellFeature(RuleGnarl.getInstance()));
        MenuSite.addLine(this, true, "Rule", "34Life (3-4 / 3-4)", e -> setCellFeature(Rule34Life.getInstance()));
        MenuSite.addLine(this, true, "Rule", "Stains (3,6-8 / 2-3,5-8)", e -> setCellFeature(RuleStains.getInstance()));

        MenuSite.addLine(this, true, "Color", "Default (Red)", e -> setCellFeature(ColorRed.getInstance()));
        MenuSite.addLine(this, true, "Color", "Green", e -> setCellFeature(ColorGreen.getInstance()));
        MenuSite.addLine(this, true, "Color", "Blue", e -> setCellFeature(ColorBlue.getInstance()));
        MenuSite.addLine(this, true, "Color", "Black", e -> setCellFeature(ColorBlack.getInstance()));

        Clock.instance().addClockListener(() -> { //{=Universe.clock.subscribe}
            if (outermostCell.figureNextState(
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY,
                    Cell.DUMMY, Cell.DUMMY, Cell.DUMMY, Cell.DUMMY
            )) {
                if (outermostCell.transition()) {
                    refreshNow();
                }
            }
        });
    }

    /**
     * Singleton Accessor. The Universe object itself is manufactured
     * in Neighborhood.createUniverse()
     */

    public static Universe instance() {
        return theInstance;
    }

    private void doLoad() {
        try {
            FileInputStream in = new FileInputStream(
                    Files.userSelected(".", ".life", "Life File", "Load")
            );

            Clock.instance().stop();        // stop the game and
            outermostCell.clear();            // clear the board.

            Storable memento = outermostCell.createMemento();
            memento.load(in);
            outermostCell.transfer(memento, new Point(0, 0), Cell.LOAD);

            in.close();
        } catch (IOException theException) {
            JOptionPane.showMessageDialog(
                    null,
                    "Read Failed!",
                    "The Game of Life",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        repaint();
    }

    private void doStore() {
        try {
            FileOutputStream out = new FileOutputStream(
                    Files.userSelected(".", ".life", "Life File", "Write")
            );

            Clock.instance().stop();        // stop the game

            Storable memento = outermostCell.createMemento();
            outermostCell.transfer(memento, new Point(0, 0), Cell.STORE);
            memento.flush(out);

            out.close();
        } catch (IOException theException) {
            JOptionPane.showMessageDialog(
                    null,
                    "Write Failed!",
                    "The Game of Life",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void setCellFeature(Feature feature) {
        Rectangle bounds = getBounds();
        bounds.x = 0;
        bounds.y = 0;
        outermostCell.setCellFeature(lastClickedPoint, bounds, feature);
        refreshNow();
    }

    /**
     * Override paint to ask the outermost Neighborhood
     * (and any subcells) to draw themselves recursively.
     * All knowledge of screen size is also encapsulated.
     * (The size is passed into the outermost <code>Cell</code>.)
     */

    public void paint(Graphics g) {
        Rectangle panelBounds = getBounds();
        Rectangle clipBounds = g.getClipBounds();

        // The panel bounds is relative to the upper-left
        // corner of the screen. Pretend that it's at (0,0)
        panelBounds.x = 0;
        panelBounds.y = 0;
        outermostCell.redraw(g, panelBounds, true);        //{=Universe.redraw1}
    }

    /**
     * Force a screen refresh by queing a request on
     * the Swing event queue. This is an example of the
     * Active Object pattern (not covered by the Gang of Four).
     * This method is called on every clock tick. Note that
     * the redraw() method on a given <code>Cell</code>
     * does nothing if the <code>Cell</code> doesn't
     * have to be refreshed.
     */

    private void refreshNow() {
        SwingUtilities.invokeLater(() -> {
            Graphics g = getGraphics();
            if (g == null) {       // Universe not displayable
                return;
            }
            try {
                Rectangle panelBounds = getBounds();
                panelBounds.x = 0;
                panelBounds.y = 0;
                outermostCell.redraw(g, panelBounds, false); //{=Universe.redraw2}
            } finally {
                g.dispose();
            }
        });
    }
}
