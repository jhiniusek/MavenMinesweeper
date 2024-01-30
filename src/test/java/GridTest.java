import org.example.src.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ArrayList;

public class GridTest {

    @Test
    public void testPlayability(){
        GameLogic game = new GameLogic();
        Map grid = game.startGame(5,5,5);
        Assertions.assertTrue(grid.isPlayable(),"5x5 with 5 bombs detected as unplayable.");

        GameLogic game2 = new GameLogic();
        Map grid2 = game2.startCustomGame(25,25,25);
        Assertions.assertTrue(grid2.isPlayable(),"25x25 with 25 bombs detected as unplayable.");

        GameLogic game3 = new GameLogic();
        Map grid3 = game3.startCustomGame(1,5,10);
        Assertions.assertFalse(grid3.isPlayable(),"1x5 with 10 bombs detected as playable.");

        GameLogic game4 = new GameLogic();
        Map grid4 = game4.startCustomGame(6,6,70);
        Assertions.assertFalse(grid4.isPlayable(),"6x6 with 70 bombs detected as playable.");
    }

    @Test
    public void testCellsAmount(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,5);
        Assertions.assertEquals(25, grid.getMap().size(),"Grid 5x5 doesn't have 25 cells.");
        Assertions.assertEquals(25, grid.getToDecideFate().size(),"Grid 5x5 doesn't have 25 cells.");

        GameLogic game2 = new GameLogic();
        Map grid2 = game2.startCustomGame(10,10,5);
        Assertions.assertEquals(100, grid2.getMap().size(),"Grid 10x10 doesn't have 100 cells.");
        Assertions.assertEquals(100, grid2.getToDecideFate().size(),"Grid 10x10 doesn't have 100 cells.");
    }

    @Test
    public void testCellChoice(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,5);
        Cell testcell = grid.chooseCell(1,2);
        Assertions.assertEquals(2,testcell.getX(),"Cell(1,2) row position not equal to 2.");
        Assertions.assertEquals(1,testcell.getY(),"Cell(1,2) column position not equal to 1.");

        Cell testCell2 = grid.chooseCell(6,6);
        Assertions.assertNotEquals(6,testCell2.getX(),"Not existing cell detected.");
        Assertions.assertNotEquals(6,testCell2.getY(),"Not existing cell detected.");
    }

    @Test
    public void testFlagging(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,5);
        Cell testcell = grid.chooseCell(1,1);
        testcell.setStatus(1);
        Assertions.assertEquals(0, grid.getFlagCounter(), "Grid created with flags.");
        grid.flagCell(testcell);
        Assertions.assertEquals(2, testcell.getStatus(), "Number didn't get flagged.");
        Assertions.assertEquals(1, grid.getFlagCounter(), "Flag didn't get counted.");
        grid.flagCell(testcell);
        Assertions.assertEquals(1, testcell.getStatus(), "Number didn't get unflagged.");
        Assertions.assertEquals(0, grid.getFlagCounter(), "Flag didn't get removed.");

        testcell.setStatus(3);
        grid.flagCell(testcell);
        Assertions.assertEquals(4, testcell.getStatus(), "Bomb didn't get flagged.");
        grid.flagCell(testcell);
        Assertions.assertEquals(3, testcell.getStatus(), "Bomb didn't get unflagged.");
    }

    @Test
    public void testGettingNeighbours(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,5);
        Cell testcell = grid.chooseCell(2,2);

        ArrayList<Cell> testNeighbours = grid.getNeighbours(testcell);
        Assertions.assertEquals(9,testNeighbours.size(),"Didn't find 8 neighbours of cell 2x2");

        Cell testcell2 = grid.chooseCell(1,1);
        ArrayList<Cell> testNeighbours2 = grid.getNeighbours(testcell2);
        Assertions.assertEquals(4,testNeighbours2.size(),"Didn't find 3 neighbours of cell 1x1");

        Cell testcell3 = grid.chooseCell(1,2);
        testcell3.discoverCell();

        ArrayList<Cell> testNeighbours3 = grid.getUnrevealedNeighbours(testcell);
        Assertions.assertEquals(8,testNeighbours3.size(),"Didn't find 7 unrevealed neighbours of cell 2x2");

        ArrayList<Cell> testNeighbours4 = grid.getNeighbours(testcell2);
        Assertions.assertEquals(4,testNeighbours4.size(),"Didn't find 2 unrevealed neighbours of cell 1x1");
    }

    @Test
    public void testWinLoseConditions(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,24);
        Assertions.assertEquals(0, grid.checkWin(), "Win of Lose before playing.");

        Cell testCell = grid.chooseCell(1,1);
        grid.spawnBombs(24,testCell);
        Assertions.assertEquals(1, grid.checkWin(), "Didn't win after revealing all number cells.");

        GameLogic game2 = new GameLogic();
        Map grid2 = game2.startCustomGame(5,5,10);
        Cell testCell2 = grid2.chooseCell(1,1);
        testCell2.setStatus(1);
        Cell testCell3 = grid2.chooseCell(1,2);
        testCell3.setStatus(3);
        testCell3.discoverCell();
        Assertions.assertEquals(2, grid2.checkWin(), "Didn't lose after revealing a bomb.");
    }

    @Test
    public void testCellGuessing(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,10);
        Cell testCell = grid.chooseCell(1,1);
        grid.spawnBombs(10,testCell);
        Assertions.assertFalse(testCell.getIsCovered(), "First guess not revealed.");

        Cell testCell2 = grid.chooseCell(1,2);
        grid.guess(testCell2);
        Assertions.assertFalse(testCell.getIsCovered(), "Guessed cell not revealed.");

        GameLogic game2 = new GameLogic();
        Map grid2 = game2.startCustomGame(5,5,10);
        Cell testCell3 = grid2.chooseCell(3,3);
        Cell testCell4 = grid2.chooseCell(3,4);
        testCell4.setStatus(1);
        testCell3.setValue(1);
        testCell3.setStatus(1);
        testCell3.setValue(0);
        grid2.guess(testCell3);

        Assertions.assertFalse(testCell4.getIsCovered(), "Neighbour of 0 not revealed.");
    }

    @Test
    public void testSpawnNumbers(){
        GameLogic game = new GameLogic();
        Map grid = game.startCustomGame(5,5,24);
        Cell testCell = grid.chooseCell(3,3);
        grid.spawnBombs(24,testCell);
        Assertions.assertEquals(8,testCell.getValue(), "Incorrect number of nearby bombs displayed.");
    }

    @Test
    public void testInput(){
        GameLogic game = new GameLogic();
        int x = game.checkInt("1");
        Assertions.assertEquals(1, x, "Input 1 didn't turn into integer.");

        x = game.checkInt("test");
        Assertions.assertEquals(0, x, "Input test turned into integer different than 0.");

    }

}
