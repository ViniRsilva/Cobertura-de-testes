package com.vev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RankingTest {

    private Ranking ranking;

    @BeforeEach
    void setUp() {
        ranking = new Ranking();
    }

    @Test
    void testAddWhenRankingIsNotFull() {
        Record record1 = new Record("Player1", 100);
        assertTrue(ranking.add(record1));
        assertEquals(1, ranking.numRecords());
        assertEquals(record1, ranking.getScore(0));

        Record record2 = new Record("Player2", 50);
        assertTrue(ranking.add(record2));
        assertEquals(2, ranking.numRecords());
        assertEquals(record1, ranking.getScore(0));
        assertEquals(record2, ranking.getScore(1));

        Record record3 = new Record("Player3", 75);
        assertTrue(ranking.add(record3));
        assertEquals(3, ranking.numRecords());
        assertEquals(record1, ranking.getScore(0));
        assertEquals(record3, ranking.getScore(1));
        assertEquals(record2, ranking.getScore(2));
    }

    @Test
    void testAddWhenRankingIsFullAndNewScoreIsWorse() {
        for (int i = 0; i < 20; i++) {
            ranking.add(new Record("Player" + i, 100 - i));
        }
        assertEquals(20, ranking.numRecords());

        Record newRecord = new Record("PlayerX", 0);
        assertFalse(ranking.add(newRecord));
        assertEquals(20, ranking.numRecords());
        assertEquals(new Record("Player19", 100 - 19).getScore(), ranking.worstScore().getScore());
    }

    @Test
    void testAddWhenRankingIsFullAndNewScoreIsBetter() {
        for (int i = 0; i < 20; i++) {
            ranking.add(new Record("Player" + i, 100 - i));
        }
        assertEquals(20, ranking.numRecords());

        Record newRecord = new Record("PlayerY", 101);
        assertTrue(ranking.add(newRecord));
        assertEquals(20, ranking.numRecords());
        assertEquals(newRecord, ranking.bestScore());
        assertEquals(new Record("Player18", 100 - 18).getScore(), ranking.worstScore().getScore());
    }

    @Test
    void testNumRecords() {
        assertEquals(0, ranking.numRecords());
        ranking.add(new Record("P1", 10));
        assertEquals(1, ranking.numRecords());
    }

    @Test
    void testGetScore() {
        Record r1 = new Record("P1", 10);
        ranking.add(r1);
        assertEquals(r1, ranking.getScore(0));
        assertNull(ranking.getScore(1));
        assertNull(ranking.getScore(-1));
    }

    @Test
    void testWorstScoreAndBestScore() {
        ranking.add(new Record("P1", 10));
        ranking.add(new Record("P2", 20));
        ranking.add(new Record("P3", 5));

        assertEquals(new Record("P2", 20).getScore(), ranking.bestScore().getScore());
        assertEquals(new Record("P3", 5).getScore(), ranking.worstScore().getScore());
    }

    @Test
    void testRecordEquality() {
        Record r1 = new Record("Test", 10);
        Record r2 = new Record("Test", 10);
        Record r3 = new Record("Another", 20);

        assertNotEquals(r1, r2);
        assertEquals(r1.getScore(), r2.getScore());
        assertNotEquals(r1, r3);
    }
}


