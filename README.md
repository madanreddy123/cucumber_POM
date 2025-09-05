
    /**
     * Helper method to check if a word is a preposition or conditional term
     */
    private static boolean isPrepositionOrCondition(String word, String[] prepositions) {
        word = word.toLowerCase();
        for (String prep : prepositions) {
            if (word.equals(prep)) return true;
        }
        return false;
    }
}