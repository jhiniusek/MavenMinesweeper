import org.example.src.Cell;
import org.junit.jupiter.api.*;

import java.nio.channels.AsynchronousServerSocketChannel;

public class CellTest {
    Cell testCell = new Cell(1, 1);
    @Test
    public void testCellConstructor(){
        Assertions.assertTrue(testCell.getIsCovered(), "Cell got created as revealed.");
        Assertions.assertEquals(0, testCell.getStatus(), "Cell got created with existing status.");
        Assertions.assertEquals(0, testCell.getValue(), "Cell detected neighbouring bombs before they spawn.");
    }

    @Test
    public void testCellRevealing(){
        Assertions.assertTrue(testCell.getIsCovered(), "Cell got created as revealed.");
        testCell.discoverCell();
        Assertions.assertFalse(testCell.getIsCovered(), "Cell didn't reveal.");
    }

    @Test
    public void testCellStatus(){
        Assertions.assertEquals(0, testCell.getStatus(), "Cell got created with existing status.");
        testCell.setStatus(1);
        Assertions.assertEquals(1, testCell.getStatus(), "Cell didn't become a number.");
        testCell.setStatus(2);
        Assertions.assertEquals(2, testCell.getStatus(), "Cell didn't become an incorrect flag.");
        testCell.setStatus(3);
        Assertions.assertEquals(3, testCell.getStatus(), "Cell didn't become a bomb.");
        testCell.setStatus(4);
        Assertions.assertEquals(4, testCell.getStatus(), "Cell didn't become a flagged bomb.");
    }

    @Test
    public void testCellValue(){
        Assertions.assertEquals(0, testCell.getValue(), "Cell detected neighbouring bombs before they spawn.");
        testCell.setValue(2);
        Assertions.assertEquals(2, testCell.getValue(), "Cell's value didn't change.");
    }
}
