package com.holub.ui;

import javax.swing.*;

public final class PopupSite {
    private static JFrame menuFrame = null;
    private static JPopupMenu popupMenu = null;

    private PopupSite() {}

    private static boolean valid() {
        assert menuFrame != null : "PopupSite not established";
        assert popupMenu != null : "PopupSite not established";
        return true;
    }

    public synchronized static void establish(JFrame container) {
        assert container != null;
        assert menuFrame == null :
                "Tried to establish more than one PopupSite";

        menuFrame = container;
        popupMenu = new JPopupMenu();

        assert valid();
    }
}
