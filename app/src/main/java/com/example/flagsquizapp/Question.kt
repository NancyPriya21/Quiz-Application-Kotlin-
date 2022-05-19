package com.example.flagsquizapp
//this data class is our model of a question
data class Question(
    val id:Int,
    val question: String,
    val image: Int,
    val optionOne:String,
    val optionTwo:String,
    val optionThree:String,
    val optionFour:String,
    val correctOption: Int
)
