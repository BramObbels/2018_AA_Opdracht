package util;

import java.io.Serializable;

/**
 * TablePosition is a helper class for 2D Java Maps.
 * TablePosition holds a row and column number to create 2D Java Maps in an easy way.
 * @author Dylan Van Assche
 */
public class TablePosition implements Serializable {
    private int row;
    private int column;
        
    public TablePosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
