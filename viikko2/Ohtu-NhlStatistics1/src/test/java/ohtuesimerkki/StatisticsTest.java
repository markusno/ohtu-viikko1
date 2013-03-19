/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author markus
 */
public class StatisticsTest {
    
    Statistics stats;
    
    public StatisticsTest() {
    }
    
    Reader readerStub = new Reader() {
 
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
    
    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }

    /**
     * Test of search method, of class Statistics.
     */
    @Test
    public void testSearchExisting() {
        String name = "Kurri";
        Player p = stats.search(name);
        assertTrue(p != null);
        assertEquals(name, p.getName());
    }

    @Test
    public void testSearchNonExisting() {
        String name = "Selänne";
        Player p = stats.search(name);
        assertTrue(p == null);
    }
    /**
     * Test of team method, of class Statistics.
     */
    @Test
    public void testTeamRightSize() {
        List<Player> team = stats.team("EDM");
        assertEquals(3, team.size());
    }

    /**
     * Test of topScorers method, of class Statistics.
     */
    @Test
    public void testTopScorersRightSize() {
        List<Player> topScores = stats.topScorers(2);
        assertEquals(3, topScores.size());
    }
    
    @Test
    public void testTopScorersRightOnTop() {
        List<Player> topScores = stats.topScorers(3);
        assertEquals("Gretzky", topScores.get(0).getName());
    }
}