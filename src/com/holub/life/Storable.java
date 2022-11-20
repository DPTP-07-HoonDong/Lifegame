package com.holub.life;

import java.io.*;

/***
 * All mementos created by the Cells are Storable.
 * <p>
 * {@code @include} /etc/license.txt
 */

public interface Storable {
    void load(InputStream in) throws IOException;

    void flush(OutputStream out) throws IOException;
}
