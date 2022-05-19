package com.example.flagsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        var usernameTv: TextView = findViewById(R.id.usernameView)
        var score: TextView= findViewById(R.id.scoreView)
        var finishBtn: Button= findViewById(R.id.finishBtn)

        usernameTv.text=intent.getStringExtra(Constants.USER_NAME)

        val totalQues= intent.getIntExtra(Constants.TOTAL_NO_OF_QUESTIONS, 0)
        val correctAnswer= intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        score.text= "Your score is $correctAnswer out of $totalQues"

        finishBtn.setOnClickListener {
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}