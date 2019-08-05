package pandatech.hangman_

import java.util.Random

/**
 * Created by fernando on 11/27/14.
 */

class WordToGuess internal constructor(word: String) {
    internal var current_state = ""    // Current state of the word. "_" for un-guessed letters

    init {
        raw_text = word
        for (i in 0 until raw_text.length())
            current_state = current_state + "_"
        System.out.println("Current State: $current_state")
        val r = Random()
        val i1 = r.nextInt(raw_text.length() - 1)
        System.out.println("Length of raw_text: " + raw_text.length())
        System.out.println("Random Number: $i1")
        System.out.println("Random Letter: " + raw_text.substring(i1, i1 + 1))
        insert_letter(raw_text.substring(i1, i1 + 1))
    }

    internal fun insert_letter(letter: String) {
        for (i in 0 until raw_text.length()) {
            val c = raw_text.toLowerCase().charAt(i)
            if (c == letter.toLowerCase().toCharArray()[0]) {
                current_state = current_state.substring(0, i) + c + current_state.substring(i + 1, current_state.length())
            }
        }
    }

    internal fun letter_belongs_to_word(letter: String): Boolean {
        val c: Char
        c = letter.toLowerCase().toCharArray()[0]

        for (i in 0 until raw_text.length()) {
            val w = raw_text.toLowerCase().charAt(i)
            if (w == c) {
                return true
            }
        }
        return false
    }

    internal fun letter_already_in_word(letter: String): Boolean {
        val c: Char
        c = letter.toLowerCase().toCharArray()[0]

        for (i in 0 until current_state.length()) {
            val w = current_state.toLowerCase().charAt(i)
            System.out.println("Current char: $c word char: $w")
            if (w == c) {
                return true
            }
        }
        return false
    }

    internal fun display_word(): String {
        var temp = ""
        // Append and pre append a space over
        for (i in 0 until current_state.length()) {
            temp = temp + " " + current_state.charAt(i) + " "
        }
        // Remove the leading and trailing blank space
        temp = temp.substring(1, temp.length() - 1)
        return temp
    }

    internal fun return_raw_word(): String {
        return raw_text
    }

    internal fun word_completed(): Boolean {
        return current_state.equals(raw_text)
    }

    companion object {
        internal var raw_text = ""    // Stores the main body of the word to guess
    }

}
