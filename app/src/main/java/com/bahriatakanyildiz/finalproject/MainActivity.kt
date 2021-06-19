package com.bahriatakanyildiz.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var currentQuestionNo: Int = 1
    private var selectedOpt: Int = -2 //-2 is the default state (nothing selected yet)
    private var trueOption: Int = -1
    private val questionCount = 4

    //The ArrayList that store the questions
    private val questionList: ArrayList<Question> = arrayListOf()

    //Shared preferences for keeping score data
    private lateinit var sharedPreferences: SharedPreferences

    private val questionCountdown = object : CountDownTimer(16000, 1000) {
        override fun onFinish() {
            selectedOpt = -2 //-2 is the default state (nothing selected yet)
            submitButton.performClick()
        }

        override fun onTick(millisUntilFinished: Long) {
            time.text = "Time: ${millisUntilFinished / 1000}"
        }
    }
    private val extraTimeLifelineCountdown = object : CountDownTimer(30000, 1000) {
        override fun onFinish() {
            selectedOpt = -2 //-2 is the default state (nothing selected yet)
            submitButton.performClick()
        }

        override fun onTick(millisUntilFinished: Long) {
            time.text = "Time: ${millisUntilFinished / 1000}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SharedPreferences for keeping score data
        sharedPreferences =
            this.getSharedPreferences("com.bahriatakanyildiz.finalproject", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("score", 0).apply()
        QuestionInitializer.initializeQuestions(questionList)
        Log.d("DEBUG_TAG", "Debug1")

        //Show Start Screen
        setBackgroundColorToDefault()
        questionText.text = "Press Start Button"
        resetTextsFromViews()
        disableButtons()
        submitButton.text = "START"

        //Setup Start Button OnClick Listener
        submitButton.setOnClickListener {

            showQuestion()
            extraTimeLifelineButton.isEnabled = true
            eliminateTwoLifelineButton.isEnabled = true
            enableOptionButtons()
            submitButton.text = "SUBMIT"

            Log.d("DEBUG_TAG", "Debug2")

            //Change the OnClick Listener As Button Changes to SUBMIT
            submitButton.setOnClickListener {
                Log.d("DEBUG_TAG", "Debug3")

                questionCountdown.cancel()
                extraTimeLifelineCountdown.cancel()
                when (selectedOpt) {
                    trueOption -> {
                        increaseScoreByOne()
                        Toast.makeText(this@MainActivity, "True", Toast.LENGTH_SHORT).show()

                    }
                    -2 -> {
                        Toast.makeText(this@MainActivity, "Time is up", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        Toast.makeText(this@MainActivity, "False", Toast.LENGTH_SHORT).show()

                    }
                }
                currentQuestionNo++
                selectedOpt = -2

                if (currentQuestionNo <= questionCount) {
                    showQuestion()
                    setBackgroundColorToDefault()

                } else finishGame()
            }

        }


        //Set up option OnClick Listeners
        optOneText.setOnClickListener {
            selectedOpt = 1
            setBackgroundColorToDefault()
            it.setBackgroundColor(Color.GRAY)
        }

        optTwoText.setOnClickListener {
            selectedOpt = 2
            setBackgroundColorToDefault()
            it.setBackgroundColor(Color.GRAY)
        }

        optThreeText.setOnClickListener {
            selectedOpt = 3
            setBackgroundColorToDefault()
            it.setBackgroundColor(Color.GRAY)
        }

        optFourText.setOnClickListener {
            selectedOpt = 4
            setBackgroundColorToDefault()
            it.setBackgroundColor(Color.GRAY)
        }


        //Extra Time Lifeline - Button Listener
        extraTimeLifelineButton.setOnClickListener {
            questionCountdown.cancel()
            extraTimeLifelineCountdown.start()
            it.isEnabled = false
        }

        //Eliminate Two Wrong Answers 50/50 Lifeline - Button Listener
        eliminateTwoLifelineButton.setOnClickListener {
            setBackgroundColorToDefault()
            selectedOpt = -2 //-2 is the default state (nothing selected yet)
            when (trueOption) {
                1 -> {
                    optFourText.isEnabled = false
                    optTwoText.isEnabled = false
                }
                2 -> {
                    optFourText.isEnabled = false
                    optOneText.isEnabled = false
                }
                3 -> {
                    optFourText.isEnabled = false
                    optTwoText.isEnabled = false
                }
                4 -> {
                    optThreeText.isEnabled = false
                    optTwoText.isEnabled = false
                }
            }
            it.isEnabled = false
        }


    }

    private fun setBackgroundColorToDefault() {
        optOneText.setBackgroundColor(Color.LTGRAY)
        optTwoText.setBackgroundColor(Color.LTGRAY)
        optThreeText.setBackgroundColor(Color.LTGRAY)
        optFourText.setBackgroundColor(Color.LTGRAY)
    }

    private fun showQuestion() {

        //Check if the lifeline disable the buttons in the previous question
        if (!optOneText.isEnabled || !optTwoText.isEnabled || !optThreeText.isEnabled || !optFourText.isEnabled) {
            enableOptionButtons()
        }
        selectedOpt = -2 //-2 is the default state (nothing selected yet)

        //Get a random question and display it
        val randomValue = Random.nextInt(questionList.size)
        val question: Question = questionList[randomValue]
        pBar.progress = currentQuestionNo
        pBar.max = questionCount
        progressText.text = "$currentQuestionNo/$questionCount"
        questionText.text = question.question
        optOneText.text = question.opt1
        optTwoText.text = question.opt2
        optThreeText.text = question.opt3
        optFourText.text = question.opt4
        trueOption = question.correctOpt

        //To prevent same question coming again, remove the question from the list.
        questionList.removeAt(randomValue)
        questionCountdown.start()
    }


    private fun increaseScoreByOne() {
        var current = sharedPreferences.getInt("score", 0)
        current++
        sharedPreferences.edit().putInt("score", current).apply()
    }

    private fun disableButtons() {
        extraTimeLifelineButton.isEnabled = false
        eliminateTwoLifelineButton.isEnabled = false
        optOneText.isEnabled = false
        optTwoText.isEnabled = false
        optThreeText.isEnabled = false
        optFourText.isEnabled = false
    }


    private fun enableOptionButtons() {
        optOneText.isEnabled = true
        optTwoText.isEnabled = true
        optThreeText.isEnabled = true
        optFourText.isEnabled = true
    }

    private fun resetTextsFromViews() {
        optOneText.text = "-"
        optTwoText.text = "-"
        optThreeText.text = "-"
        optFourText.text = "-"
        time.text = "-"
    }

    private fun finishGame() {

        //Reset UI
        setBackgroundColorToDefault()
        disableButtons()
        resetTextsFromViews()

        //Cancel countdowns if there is any
        extraTimeLifelineCountdown.cancel()
        questionCountdown.cancel()

        //Remove Button OnClick Listeners
        optOneText.setOnClickListener(null)
        optTwoText.setOnClickListener(null)
        optThreeText.setOnClickListener(null)
        optFourText.setOnClickListener(null)

        //Show End Screen and The Score
        val score = sharedPreferences.getInt("score", 0)
        val newScore = 100 * score / questionCount
        val endText = "Score: $newScore"
        Toast.makeText(
            this@MainActivity, endText,
            Toast.LENGTH_LONG
        ).show()
        questionText.text = "Game Finished!\n Score: $newScore"


        //If clicked, will restart the game
        submitButton.text = "REPLAY"
        submitButton.setOnClickListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }
}