package com.android.hangman.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.android.hangman.R
import com.android.hangman.domain.Category
import com.android.hangman.domain.Subcategory
import com.android.hangman.domain.Word
import com.android.hangman.enums.Difficulty

import java.util.ArrayList
import java.util.Objects
import java.util.Random

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import butterknife.OnClick


class PlayGameActivity : MainActivity() {

    private var missCounter = 0
    private var score = 0
    private var bonusPoints = 0

    private var categoryList: ArrayList<Category>? = null
    private var subcategoryList: ArrayList<Subcategory>? = null
    private var wordsList: ArrayList<Word>? = null
    private var category: Category? = null
    private var word: Word? = null
    private var difficulty: Difficulty? = null

    private var handler: Handler? = null
    private var random: Random? = null
    private var categoryCount: Int = 0
    private var categoryNumber = 0
    private var difficultyLevel: Int = 0

    private var categoryName: String? = null
    private var wordName: String? = null
    private var letterTextViews: Array<TextView>? = null

    private var chosenButton: Button? = null
    private var isLetterExists: Boolean = false
    private var guessLetterCounter = 0
    private var drawElementsCounter = 0

    private var underlinesDrawable: LayerDrawable? = null
    private var dialog: Dialog? = null
    private var dialogView: View? = null

    private var statusTextView: TextView? = null
    private var statusIcon: ImageView? = null
    private var goFurtherButton: Button? = null

    @BindView(R.id.gameConstraintLayout)
    protected var mainLayout: ConstraintLayout? = null

    @BindView(R.id.wordLettersLayout)
    protected var wordLettersLayout: LinearLayout? = null

    @BindView(R.id.categoryTextView)
    protected var categoryNameTextView: TextView? = null

    @BindViews(R.id.hangmanOne, R.id.hangmanTwo, R.id.hangmanThree, R.id.hangmanFour, R.id.hangmanFive)
    protected var hangmanImageViews: Array<ImageView>? = null

    @BindViews(R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI, R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS, R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2, R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS)
    protected var letterButtons: List<Button>? = null

    private val brakeGamePoints: Int
        get() {
            var lostPoints = 0
            when (categoryCount) {
                3 -> lostPoints = THREE_ROUNDS_BREAK_GAME_POINTS
                5 -> lostPoints = FIVE_ROUNDS_BREAK_GAME_POINTS
                7 -> lostPoints = SEVEN_ROUNDS_BREAK_GAME_POINTS
                else -> {
                }
            }
            return -lostPoints
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        ButterKnife.bind(this)
        random = Random()
        handler = Handler()

        getDataFromIntent()
        initializeLists()

        hideHangmanImageViews()
        loadData(categoryNumber)
        showCategory()
        showLetterUnderlines()
        preparePopups()
    }

    private fun loadData(categoryNumber: Int) {
        category = getCategory(categoryNumber)
        val subcategory = getSubcategory(category!!)
        word = getWord(subcategory)
        setWordAttributes()
        setDifficultyLevel(difficultyLevel)
        clearLists()
    }

    private fun setDifficultyLevel(difficultyLevel: Int) {
        when (difficultyLevel) {
            1 -> difficulty = Difficulty.EASY
            2 -> difficulty = Difficulty.MEDIUM
            3 -> difficulty = Difficulty.HARD
            else -> {
            }
        }
    }

    private fun showLetterUnderlines() {
        letterTextViews = arrayOfNulls(wordName!!.length)

        for (i in 0 until wordName!!.length) {
            letterTextViews[i] = TextView(this)
            letterTextViews!![i].text = wordName!![i].toString()
            setLettersAppearance(i)
            drawUnderlines(i)
            wordLettersLayout!!.addView(letterTextViews!![i])
        }
    }

    @OnClick(R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI, R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS, R.id.buttonT, R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ, R.id.buttonPolishZ1, R.id.buttonPolishZ2, R.id.buttonPolishA, R.id.buttonPolishC, R.id.buttonPolishE, R.id.buttonPolishL, R.id.buttonPolishN, R.id.buttonPolishO, R.id.buttonPolishS)
    protected fun onLetterButtonClick(view: View) {
        chosenButton = view as Button
        checkIfLetterExistsInWord(chosenButton!!.text.toString())
        disableOneLetterButton()
    }

    private fun checkIfLetterExistsInWord(letter: String) {
        isLetterExists = false
        for (letterTextView in letterTextViews!!) {
            if (letterTextView.text.toString() == letter) {
                guessLetterCounter++
                isLetterExists = true
                letterTextView.setTextColor(Color.parseColor(difficulty!!.color))
            }
        }

        processLetterCheckResult()
    }

    private fun processLetterCheckResult() {
        if (isLetterExists) {
            if (guessLetterCounter == wordName!!.replace(SPACE, EMPTY_STRING).length) {
                guessLetterCounter = 0
                drawElementsCounter = 0
                disableLetterButtons()
                sumPoints()
                delayAndShowWinPopup()
            }
        } else if (drawElementsCounter < HANGMAN_ELEMENTS) {
            drawHangman(drawElementsCounter)
            drawElementsCounter++
            if (drawElementsCounter == HANGMAN_ELEMENTS) {
                drawElementsCounter = 0
                guessLetterCounter = 0
                missCounter++
                disableLetterButtons()
                substractPoints()
                delayAndShowLosePopup()
            }
        }
    }

    private fun delayAndShowWinPopup() {
        handler!!.postDelayed({ this.showWinPopup() }, OPEN_POPUP_DELAY.toLong())
    }

    private fun delayAndShowLosePopup() {
        handler!!.postDelayed({ this.showLosePopup() }, OPEN_POPUP_DELAY.toLong())
    }

    private fun delayAndClosePopup() {
        handler!!.postDelayed({ dialog!!.dismiss() }, CLOSE_POPUP_DELAY.toLong())
    }

    private fun sumPoints() {
        when (difficulty) {
            Difficulty.EASY -> score += EASY_LEVEL_WORD_GUESS_POINTS
            Difficulty.MEDIUM -> score += MEDIUM_LEVEL_WORD_GUESS_POINTS
            Difficulty.HARD -> score += HARD_LEVEL_WORD_GUESS_POINTS
            else -> {
            }
        }
    }

    private fun substractPoints() {
        when (difficulty) {
            Difficulty.EASY -> score -= EASY_LEVEL_WORD_MISS_POINTS
            Difficulty.MEDIUM -> score -= MEDIUM_LEVEL_WORD_MISS_POINTS
            Difficulty.HARD -> score -= HARD_LEVEL_WORD_MISS_POINTS
            else -> {
            }
        }
    }

    private fun preparePopups() {
        setDialogProperties()
        setDialogWindowProperties()
        initializeDialogComponents()
    }

    private fun removePreviousRoundContent() {
        removeWordLettersFromView()
        removeCategoryFromView()
        hideHangmanImageViews()
        enableLetterButtons()
    }

    private fun removeWordLettersFromView() {
        wordLettersLayout!!.removeAllViews()
    }

    private fun removeCategoryFromView() {
        categoryNameTextView!!.text = EMPTY_STRING
    }

    private fun showWinPopup() {
        setWinPopupAttributes()
        showDialog()
        goFurtherButton!!.setOnClickListener { v -> onGoFurtherButtonClick() }
    }

    private fun showLosePopup() {
        setLosePopupAttributes()
        showDialog()
        goFurtherButton!!.setOnClickListener { v -> onGoFurtherButtonClick() }
    }

    private fun showDialog() {
        dialog!!.show()
    }

    private fun onGoFurtherButtonClick() {
        categoryNumber++
        if (categoryNumber < categoryCount) {
            removePreviousRoundContent()
            loadNextRound()
            delayAndClosePopup()
        } else if (categoryNumber == categoryCount) {
            calculateExtraPoints()
            goToSummaryActivity()
        }
    }

    private fun calculateExtraPoints() {
        if (missCounter == 0) {
            when (categoryCount) {
                3 -> bonusPoints = THREE_ROUNDS_BONUS_POINTS
                5 -> bonusPoints = FIVE_ROUNDS_BONUS_POINTS
                7 -> bonusPoints = SEVEN_ROUNDS_BONUS_POINTS
                else -> {
                }
            }
        }
    }

    private fun calculateBreakGamePoints() {
        when (categoryCount) {
            3 -> score = THREE_ROUNDS_BREAK_GAME_POINTS
            5 -> score = FIVE_ROUNDS_BREAK_GAME_POINTS
            7 -> score = SEVEN_ROUNDS_BREAK_GAME_POINTS
            else -> {
            }
        }
    }

    private fun goToSummaryActivity() {
        val intentSummary = Intent(this@PlayGameActivity, SummaryActivity::class.java)
        intentSummary.putExtra(INTENT_KEY_SCORE, score)
        intentSummary.putExtra(INTENT_KEY_BONUS_POINTS, bonusPoints)
        startActivity(intentSummary)
        finish()
    }

    private fun loadNextRound() {
        setCategoryNumber(categoryNumber)
        loadData(categoryNumber)
        showLetterUnderlines()
        showCategory()
    }

    private fun getDataFromIntent() {
        val intent = intent
        categoryCount = intent.getSerializableExtra(INTENT_KEY_CATEGORY_COUNT) as Int
        categoryList = intent.getSerializableExtra(INTENT_KEY_CATEGORY_LIST) as ArrayList<Category>
    }

    private fun initializeLists() {
        subcategoryList = ArrayList()
        wordsList = ArrayList()
    }

    private fun getCategory(categoryNumber: Int): Category {
        return categoryList!![categoryNumber]
    }

    private fun getSubcategory(category: Category): Subcategory {
        subcategoryList!!.addAll(category.subcategories)
        val randomSubcategoryNumber = random!!.nextInt(subcategoryList!!.size)
        return subcategoryList!![randomSubcategoryNumber]
    }

    private fun getWord(subcategory: Subcategory): Word {
        wordsList!!.addAll(subcategory.words)
        val randomWordNumber = random!!.nextInt(wordsList!!.size)
        return wordsList!![randomWordNumber]
    }

    private fun setWordAttributes() {
        categoryName = category!!.categoryName
        //String subcategoryName = subcategory.getSubcategoryName();
        difficultyLevel = word!!.difficultyLevel
        wordName = word!!.name
    }

    private fun clearLists() {
        subcategoryList!!.clear()
        wordsList!!.clear()
    }

    private fun hideHangmanImageViews() {
        for (hangmanImageView in hangmanImageViews!!) {
            hangmanImageView.visibility = View.GONE
        }
    }

    private fun enableLetterButtons() {
        for (button in letterButtons!!) {
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor(BUTTON_ENABLED_BACKGROUND_COLOR))
            button.setTextColor(Color.parseColor(BUTTON_ENABLED_TEXT_COLOR))
            button.isEnabled = true
        }
    }

    private fun disableLetterButtons() {
        for (button in letterButtons!!) {
            button.isEnabled = false
        }
    }

    private fun disableOneLetterButton() {
        chosenButton!!.backgroundTintList = ColorStateList.valueOf(Color.parseColor(BUTTON_DISABLED_BACKGROUND_COLOR))
        chosenButton!!.setTextColor(Color.parseColor(BUTTON_DISABLED_TEXT_COLOR))
        chosenButton!!.isEnabled = false
    }

    private fun drawHangman(drawElementsCounter: Int) {
        hangmanImageViews!![drawElementsCounter].visibility = View.VISIBLE
    }

    private fun drawUnderlines(number: Int) {
        if (letterTextViews!![number].text != SPACE) {
            setUnderlinesStyle()
            letterTextViews!![number].background = underlinesDrawable
        }
    }

    private fun setLettersAppearance(number: Int) {
        letterTextViews!![number].layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        letterTextViews!![number].gravity = Gravity.CENTER
        letterTextViews!![number].setTextColor(Color.TRANSPARENT)
        letterTextViews!![number].textSize = 22f

        if (letterTextViews!![number].text == SPACE) {
            letterTextViews!![number].letterSpacing = 0.5.toFloat()
        }
    }

    private fun setUnderlinesStyle() {
        underlinesDrawable = ContextCompat.getDrawable(this, R.drawable.style_letter_underline) as LayerDrawable?
        underlinesDrawable!!.setTint(Color.parseColor(difficulty!!.color))
    }

    private fun showCategory() {
        val fullCategory = CATEGORY + SPACE + categoryName
        categoryNameTextView!!.text = fullCategory
    }

    private fun initializeDialogComponents() {
        statusTextView = dialogView!!.findViewById(R.id.statusTextView)
        statusIcon = dialogView!!.findViewById(R.id.statusIcon)
        goFurtherButton = dialogView!!.findViewById(R.id.goFurtherButton)
    }

    @SuppressLint("InflateParams")
    private fun setDialogProperties() {
        dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val inflater = this.layoutInflater
        dialogView = inflater.inflate(R.layout.dialog_game_popup, null)
        dialog!!.setContentView(dialogView!!)

        Objects.requireNonNull(dialog!!.window).setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
    }

    private fun setDialogWindowProperties() {
        val window = dialog!!.window
        val windowAttributes = Objects.requireNonNull(window).attributes
        windowAttributes.gravity = Gravity.CENTER
        window!!.attributes = windowAttributes
    }

    private fun setWinPopupAttributes() {
        Objects.requireNonNull(dialog!!.window).setBackgroundDrawable(ColorDrawable(Color.parseColor(WIN_COLOR)))
        statusTextView!!.text = DIALOG_MESSAGE_WIN
        statusIcon!!.setImageResource(R.drawable.icon_win)
        goFurtherButton!!.setTextColor(Color.parseColor(WIN_COLOR))
    }

    private fun setLosePopupAttributes() {
        Objects.requireNonNull(dialog!!.window).setBackgroundDrawable(ColorDrawable(Color.parseColor(LOSE_COLOR)))
        statusTextView!!.text = DIALOG_MESSAGE_LOSE
        statusIcon!!.setImageResource(R.drawable.icon_lose)
        goFurtherButton!!.setTextColor(Color.parseColor(LOSE_COLOR))
    }

    private fun setCategoryNumber(categoryNumber: Int) {
        this.categoryNumber = categoryNumber
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(DIALOG_MESSAGE_BREAK_GAME + SPACE + brakeGamePoints + SPACE + DIALOG_MESSAGE_BREAK_GAME_POINTS).setCancelable(false).setPositiveButton(DIALOG_POSITIVE_ANSWER) { dialog, id ->
            calculateBreakGamePoints()
            val intentSummary = Intent(this@PlayGameActivity, SummaryActivity::class.java)
            intentSummary.putExtra(INTENT_KEY_SCORE, score)
            intentSummary.putExtra(INTENT_KEY_BONUS_POINTS, bonusPoints)
            finish()
            startActivity(intentSummary)
        }.setNegativeButton(DIALOG_NEGATIVE_ANSWER) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        private val HANGMAN_ELEMENTS = 5
        private val OPEN_POPUP_DELAY = 600
        private val CLOSE_POPUP_DELAY = 400

        private val SPACE = " "
        private val EMPTY_STRING = ""
        private val INTENT_KEY_CATEGORY_COUNT = "categoryCount"
        private val INTENT_KEY_CATEGORY_LIST = "categoryList"
        private val INTENT_KEY_SCORE = "score"
        private val INTENT_KEY_BONUS_POINTS = "bonusPoints"

        private val CATEGORY = "Kategoria:"
        private val DIALOG_MESSAGE_WIN = "Wygrana!"
        private val DIALOG_MESSAGE_LOSE = "Porażka :("
        private val DIALOG_MESSAGE_BREAK_GAME = "Czy chcesz przerwać rozgrywkę? Dotychczasowy postęp zostanie utracony. Z Twojego konta zostanie odebranych"
        private val DIALOG_MESSAGE_BREAK_GAME_POINTS = "punktów."
        private val DIALOG_POSITIVE_ANSWER = "Tak"
        private val DIALOG_NEGATIVE_ANSWER = "Nie"

        private val WIN_COLOR = "#2E8E3D"
        private val LOSE_COLOR = "#E51616"
        private val BUTTON_ENABLED_BACKGROUND_COLOR = "#0D2C4B"
        private val BUTTON_ENABLED_TEXT_COLOR = "#F4D170"
        private val BUTTON_DISABLED_BACKGROUND_COLOR = "#D1D1D1"
        private val BUTTON_DISABLED_TEXT_COLOR = "#EAEAEA"

        private val EASY_LEVEL_WORD_GUESS_POINTS = 5
        private val MEDIUM_LEVEL_WORD_GUESS_POINTS = 10
        private val HARD_LEVEL_WORD_GUESS_POINTS = 15

        private val EASY_LEVEL_WORD_MISS_POINTS = 2
        private val MEDIUM_LEVEL_WORD_MISS_POINTS = 4
        private val HARD_LEVEL_WORD_MISS_POINTS = 6

        private val THREE_ROUNDS_BONUS_POINTS = 16
        private val FIVE_ROUNDS_BONUS_POINTS = 32
        private val SEVEN_ROUNDS_BONUS_POINTS = 48

        private val THREE_ROUNDS_BREAK_GAME_POINTS = -18
        private val FIVE_ROUNDS_BREAK_GAME_POINTS = -30
        private val SEVEN_ROUNDS_BREAK_GAME_POINTS = -42
    }

}