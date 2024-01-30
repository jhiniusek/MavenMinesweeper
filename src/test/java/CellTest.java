import org.example.src.*;
import org.junit.jupiter.api.*;

public class CellTest {
    @Test
    public void testCellConstructor(){
        Cell testCellJacocoPLS = new Cell(1, 1);
        Assertions.assertTrue(testCellJacocoPLS.getIsCovered(), "Cell got created as revealed.");
        Assertions.assertEquals(0, testCellJacocoPLS.getStatus(), "Cell got created with existing status.");
        Assertions.assertEquals(0, testCellJacocoPLS.getValue(), "Cell detected neighbouring bombs before they spawn.");
    }

    @Test
    public void testCellRevealing(){
        Cell testCell = new Cell(1, 1);
        Assertions.assertTrue(testCell.getIsCovered(), "Cell got created as revealed.");
        testCell.discoverCell();
        Assertions.assertFalse(testCell.getIsCovered(), "Cell didn't reveal.");
    }

    @Test
    public void testCellStatus(){
        Cell testCell = new Cell(1, 1);
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
        Cell testCell = new Cell(1, 1);
        Assertions.assertEquals(0, testCell.getValue(), "Cell detected neighbouring bombs before they spawn.");
        testCell.setValue(2);
        Assertions.assertEquals(2, testCell.getValue(), "Cell's value didn't change.");
    }

    @Test
    public void testDisplay(){
        Cell testCell = new Cell(1,1);
        testCell.setStatus(0);
        Assertions.assertEquals("[ ]", testCell.display(), "Empty cell displayed incorrectly.");
        testCell.setStatus(2);
        Assertions.assertEquals("\u001B[32m[F]\u001B[0m", testCell.display(), "Incorrect flag cell displayed incorrectly.");
        testCell.setStatus(4);
        Assertions.assertEquals("\u001B[32m[F]\u001B[0m", testCell.display(), "Correct flag cell displayed incorrectly");
        testCell.setStatus(1);
        testCell.setValue(1);
        testCell.discoverCell();
        Assertions.assertEquals("[1]", testCell.display(), "Number cell displayed incorrectly.");
        testCell.setStatus(3);
        Assertions.assertEquals("\u001B[31m[*]\u001B[0m", testCell.display(), "Bomb correct cell displayed incorrectly");
    }
}
