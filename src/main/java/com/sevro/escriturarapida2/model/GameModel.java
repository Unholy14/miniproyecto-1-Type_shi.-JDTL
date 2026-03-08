package com.sevro.escriturarapida2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Represents the core game logic for the "Escritura Rapida" game.
 * Manages levels, timing, word bank and scoring.
 */
public class GameModel {

    /** Initial time per level in seconds. */
    public static final int INITIAL_TIME = 20;

    /** Minimum time per level in seconds. */
    public static final int MIN_TIME = 2;

    /** Time reduction every few levels in seconds. */
    public static final int TIME_REDUCTION = 2;

    /** Number of levels before time decreases. */
    public static final int LEVELS_PER_REDUCTION = 5;

    /** Maximum number of levels in the game. */
    public static final int MAX_LEVEL = 45;

    private int currentLevel;
    private int currentTime;
    private int timePerLevel;
    private String currentWord;

    private final List<String> wordBank;
    private final Random random;

    /** Combo ranks from lowest to highest. */
    public static final String[] COMBO_RANKS = {"D", "C", "B", "A", "S", "SS", "SSS"};

    /** Time threshold in seconds to increase combo. */
    public static final int COMBO_THRESHOLD = 5;

    private int comboIndex;

    /**
     * Constructs a new GameModel and initializes the word bank.
     */
    public GameModel() {
        wordBank = new ArrayList<>();
        random = new Random();
        initWordBank();
        reset();
    }

    /**
     * Populates the word bank with words.
     */
    private void initWordBank() {
        try {
            InputStream is = GameModel.class.getResourceAsStream(
                    "/com/sevro/escriturarapida2/Words/words_es.txt"
            );
            if (is == null) {
                System.err.println("words_es.txt not found");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    wordBank.add(line.trim());
                }
            }
            System.out.println("Words loaded: " + wordBank.size());
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading word bank: " + e.getMessage());
        }
    }

    /**
     * Resets the game to its initial state.
     */
    public void reset() {
        currentLevel = 1;
        timePerLevel = INITIAL_TIME;
        currentTime = INITIAL_TIME;
        currentWord = "";
    }

    /**
     * Loads a random word that is different from the current one.
     */
    public void loadNewWord() {
        String previous = currentWord;
        int index;
        do {
            index = random.nextInt(wordBank.size());
        } while (wordBank.get(index).equals(previous));
        currentWord = wordBank.get(index);
    }

    /**
     * Validates the player's answer against the current word.
     *
     * @param answer the text entered by the player
     * @return true if the answer exactly matches the current word
     */
    public boolean validateAnswer(String answer) {
        return currentWord.equals(answer);
    }

    /**
     * Advances the game to the next level and reduces time every 5 levels.
     */
    public void advanceLevel() {
        currentLevel++;
        if (currentLevel % LEVELS_PER_REDUCTION == 1 && currentLevel > 1) {
            timePerLevel = Math.max(MIN_TIME, timePerLevel - TIME_REDUCTION);
        }
        currentTime = timePerLevel;
        loadNewWord();
    }

    /**
     * Decrements the timer by one second.
     *
     * @return true if time has run out
     */
    public boolean tickTimer() {
        currentTime--;
        return currentTime <= 0;
    }

    /**
     * Increases the combo rank if not already at maximum.
     */
    public void increaseCombo() {
        if (comboIndex < COMBO_RANKS.length - 1) {
            comboIndex++;
        }
    }

    public void decreaseCombo() {
        if (comboIndex > 0) {
            comboIndex--;
        }
    }

    /**
     * Resets the combo rank to the lowest level.
     */
    public void resetCombo() {
        comboIndex = 0;
    }

    /**
     * Returns the current combo rank string.
     *
     * @return current combo rank
     */
    public String getCurrentCombo() {
        return COMBO_RANKS[comboIndex];
    }

    /**
     * Returns the current level number.
     *
     * @return current level
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Returns the remaining time in seconds.
     *
     * @return current time
     */
    public int getCurrentTime() { return currentTime; }

    /**
     * Returns the current word the player must type.
     *
     * @return current word
     */
    public String getCurrentWord() { return currentWord; }

    /**
     * Checks whether the player has completed all levels.
     *
     * @return true if the game is won
     */
    public boolean isGameWon() { return currentLevel > MAX_LEVEL; }
}