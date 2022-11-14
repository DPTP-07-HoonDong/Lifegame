package com.holub.life;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.holub.life.feature.Feature;
import com.holub.ui.Colors;

/*** ****************************************************************
 * The Resident class implements a single cell---a "resident" of a
 * block.
 * <p>
 * {@code @include} /etc/license.txt
 */

public final class Resident implements Cell {
//    TTLFeature ttlFeature;
//    ColorFeature colorFeature;

    private static final Color BORDER_COLOR = Colors.DARK_YELLOW;

    private static final Color DEAD_COLOR = Colors.LIGHT_YELLOW;

    private boolean amAlive = false;
    private boolean willBeAlive = false;

	TTLBehavior ttlBehavior;
	EffectBehavior effectBehavior;
	NextBehavior nextBehavior;
	ColorBehavior colorBehavior;

	Resident(TTLBehavior ttlBehavior, EffectBehavior effectBehavior, NextBehavior nextBehavior, ColorBehavior colorBehavior) {
		this.ttlBehavior = ttlBehavior;
		this.effectBehavior = effectBehavior;
		this.nextBehavior = nextBehavior;
		this.colorBehavior = colorBehavior;
	}

    private boolean isStable() {
        return amAlive == willBeAlive;
    }

    /**
     * figure the next state.
     *
     * @return true if the cell is not stable (will change state on the
     * next transition().
     */
    public boolean figureNextState(
            Cell north, Cell south,
            Cell east, Cell west,
            Cell northeast, Cell northwest,
            Cell southeast, Cell southwest
    ) {
        verify(north, "north");
        verify(south, "south");
        verify(east, "east");
        verify(west, "west");
        verify(northeast, "northeast");
        verify(northwest, "northwest");
        verify(southeast, "southeast");
        verify(southwest, "southwest");

        int neighbors = 0;

        if (north.isAlive()) ++neighbors;
        if (south.isAlive()) ++neighbors;
        if (east.isAlive()) ++neighbors;
        if (west.isAlive()) ++neighbors;
        if (northeast.isAlive()) ++neighbors;
        if (northwest.isAlive()) ++neighbors;
        if (southeast.isAlive()) ++neighbors;
        if (southwest.isAlive()) ++neighbors;

        willBeAlive = (neighbors == 3 || (amAlive && neighbors == 2));
        return !isStable();
    }

    private void verify(Cell c, String direction) {
        assert (c instanceof Resident) || (c == Cell.DUMMY)
                : "incorrect type for " + direction + ": " + c.getClass().getName();
    }

    /**
     * This cell is monetary, so it's at every edge of itself. It's
     * an internal error for any position except for (0,0) to be
     * requsted since the width is 1.
     */
    public Cell edge(int row, int column) {
        assert row == 0 && column == 0;
        return this;
    }

    public boolean transition() {
        boolean changed = isStable();
        amAlive = willBeAlive;
        return changed;
    }

    public void redraw(Graphics g, Rectangle here, boolean drawAll) {
        g = g.create();
        g.setColor(amAlive ? colorBehavior.setLiveColor() : DEAD_COLOR);
        g.fillRect(here.x + 1, here.y + 1, here.width - 1, here.height - 1);

        // Doesn't draw a line on the far right and bottom of the
        // grid, but that's life, so to speak. It's not worth the
        // code for the special case.

        g.setColor(BORDER_COLOR);
        g.drawLine(here.x, here.y, here.x, here.y + here.height);
        g.drawLine(here.x, here.y, here.x + here.width, here.y);
        g.dispose();
    }

    public void userClicked(Point here, Rectangle surface) {
        amAlive = !amAlive;
    }

    @Override
    public List<Feature> getCellFeature(Point here, Rectangle surface) {
        List<Feature> features = new ArrayList<>();
        features.add(Feature.DUMMY);
//        features.add(ttlFeature);
//        features.add(colorFeature);

        return features;
    }

    public void clear() {
        amAlive = willBeAlive = false;
    }

    public boolean isAlive() {
        return amAlive;
    }

    public Cell create() {
        return new Resident(ttlBehavior, effectBehavior, nextBehavior, colorBehavior);
    }

    public int widthInCells() {
        return 1;
    }

    public Direction isDisruptiveTo() {
        return isStable() ? Direction.NONE : Direction.ALL;
    }

    public boolean transfer(Storable blob, Point upperLeft, boolean doLoad) {
        Memento memento = (Memento) blob;
        if (doLoad) {
            amAlive = willBeAlive = memento.isAlive(upperLeft);
            return amAlive;
        } else if (amAlive) {                   // store only live cells
            memento.markAsAlive(upperLeft);
        }
        return false;
    }

    /**
     * Mementos must be created by Neighborhood objects. Throw an
     * exception if anybody tries to do it here.
     */
    public Storable createMemento() {
        throw new UnsupportedOperationException("May not create memento of a unitary cell");
    }
}
