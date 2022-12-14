package com.holub.life;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.holub.life.feature.*;
import com.holub.life.feature.color.ColorBehavior;
import com.holub.life.feature.color.ColorRed;
import com.holub.life.feature.rule.RuleBehavior;
import com.holub.life.feature.rule.RuleDefault;
import com.holub.life.feature.ttl.TTLBehavior;
import com.holub.life.feature.ttl.TTLDefault;
import com.holub.ui.Colors;

/*** ****************************************************************
 * The Resident class implements a single cell---a "resident" of a
 * block.
 * <p>
 * {@code @include} /etc/license.txt
 */

public final class Resident implements Cell {
    private static final Color BORDER_COLOR = Colors.DARK_YELLOW;
    private static final Color DEAD_COLOR = Colors.LIGHT_YELLOW;

	private int amAlive = 0;
	private boolean willBeAlive	= false;

	TTLBehavior ttlBehavior;
	RuleBehavior ruleBehavior;
	ColorBehavior colorBehavior;

    Resident() {
        this.ttlBehavior = TTLDefault.getInstance();
        this.ruleBehavior = RuleDefault.getInstance();
        this.colorBehavior = ColorRed.getInstance();
    }

	Resident(TTLBehavior ttlBehavior, RuleBehavior ruleBehavior, ColorBehavior colorBehavior) {
		this.ttlBehavior = ttlBehavior;
		this.ruleBehavior = ruleBehavior;
		this.colorBehavior = colorBehavior;
	}

    private boolean isStable() {
        if (willBeAlive) {
            return amAlive == 1 || amAlive == 4;
        } else {
            return amAlive == 0;
        }
    }

	public void setTTLBehavior(TTLBehavior ttlBehavior) {
		this.ttlBehavior = ttlBehavior;
        this.amAlive = isAlive() ? ttlBehavior.getTimeToLive() : 0;
	}

	public void setRuleBehavior(RuleBehavior nextBehavior) {
		this.ruleBehavior = nextBehavior;
	}

	public void setColorBehavior(ColorBehavior colorBehavior) {
		this.colorBehavior = colorBehavior;
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

		willBeAlive = amAlive > 1 || ruleBehavior.getNextState(neighbors, amAlive);

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
        if (willBeAlive) {
            if (amAlive < 2) {
                amAlive = ttlBehavior.getTimeToLive();
            } else if (amAlive < 4) {
                amAlive--;
            }
        } else {
            if (amAlive > 0 && amAlive < 4) {
                amAlive--;
            }
        }
        return changed;
    }

    public void redraw(Graphics g, Rectangle here, boolean drawAll) {
        g = g.create();
        g.setColor(amAlive > 0 ? colorBehavior.getLiveColor() : DEAD_COLOR);
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
		    amAlive = isAlive() ? 0 : ttlBehavior.getTimeToLive();
    }

    @Override
    public List<Feature> getCellFeature(Point here, Rectangle surface) {
        List<Feature> features = new ArrayList<>();
        features.add(ttlBehavior);
        features.add(ruleBehavior);
        features.add(colorBehavior);
        return features;
    }

    public void setCellFeature(Point here, Rectangle surface, Feature feature) {
        feature.setFeature(this);
    }

    public void clear() {
		amAlive = 0;
		willBeAlive = false;
    }

    public boolean isAlive() {
        return amAlive > 0;
    }

    public Cell create() {
        return new Resident(ttlBehavior, ruleBehavior, colorBehavior);
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
            willBeAlive = memento.isAlive(upperLeft);
            amAlive = willBeAlive ? ttlBehavior.getTimeToLive() : 0;
            return amAlive > 0;
        } else if (amAlive > 0) {                   // store only live cells
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
