package com.example.flagsquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    /*implementing View.OnClickListener in order to make things clickable. It leads us to make a
    to-do onClick function at bottom*/
    //tools:text = text shall not appear when we are running the app. sets text for the designer only
    // and stored in the tool. Activity shall not show these
    //contentDescription: will be read for people who cannot see
    private var progressBar :ProgressBar?=null
    private var progressText : TextView?=null
    private var flagImage: ImageView?=null
    private var mainQuestion: TextView?=null
    private var optionOne: TextView?=null
    private var optionTwo: TextView?=null
    private var optionThree: TextView?=null
    private var optionFour: TextView?=null
    private var btnSubmit: Button ?=null

    private var mQuestionsList: ArrayList<Question>? =null
    private var mCurrentPosition: Int= 1
    private var mSelectedOptionPosition: Int=0

    private var mUserName: String?=null
    private var correctAnswers: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        //retrieving data from intent which led us to this activity
        mUserName=intent.getStringExtra(Constants.USER_NAME)

        progressBar=findViewById(R.id.progress_bar)
        progressText=findViewById(R.id.tv_progress)
        flagImage=findViewById(R.id.flagImage)
        mainQuestion=findViewById(R.id.mainQues)
        optionOne = findViewById(R.id.option_one)
        optionTwo= findViewById(R.id.option_two)
        optionThree= findViewById(R.id.option_three)
        optionFour= findViewById(R.id.option_four)
        btnSubmit =findViewById(R.id.submitBtn)

        mQuestionsList = Constants.getQuestions()

        setQuestion()
        /*creating onClickListeners for interactive views. this "QuizQuestionActivity" implements a
        View.OnClickListener which uses the onClick function
         */
        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)


    }

    private fun setQuestion() {
        /*Log.i("Questions List size is", "${mQuestionsList.size}") //i here means information
        //in order to see what is displayed in the log, check logcat after execution

        for (i in mQuestionsList) {
            Log.e("Questions", i.question) //e means error
            /*i.question means an individual question(i) accessing string question parameter in data class*/
        }
         */
        defaultOptionsView()  //resetting the option background from prev ques when setting a new ques
        val question: Question = mQuestionsList!![mCurrentPosition-1]
        //as we are sure arraylist is not empty at this point

        if(mCurrentPosition == mQuestionsList!!.size){
            btnSubmit?.text="FINISH"
        }
        else{
            btnSubmit?.text="SUBMIT"
        }

        progressBar?.progress = mCurrentPosition
        progressText?.text = "${mCurrentPosition}/${progressBar?.max}"

        //using this method as it returns an int value and we have set our imageView as int
        flagImage?.setImageResource(question.image)
        mainQuestion?.text = question.question
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

    }

    //creating a function to set the default view of unselected options
    private fun defaultOptionsView(){
        val options= ArrayList<TextView>()
        optionOne?.let {
            options.add(0, it)
        }
        optionTwo?.let {
            options.add(1, it)
        }
        optionThree?.let {
            options.add(2, it)
        }
        optionFour?.let {
            options.add(3, it)
        }
        //setting default color and typeface(design of lettering/font)
        for(i in options){
            i.setTextColor(Color.parseColor("#7A8089"))
            i.typeface= Typeface.DEFAULT
            //border of textView
            i.background= ContextCompat.getDrawable(this@QuizQuestionActivity, R.drawable.option_border_background)
        }
    }

    private fun selectedOptionView(selectedTv: TextView, selectedTvIdx :Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedTvIdx

        selectedTv.setTextColor(Color.parseColor("#2828DC"))
        selectedTv.setTypeface(selectedTv.typeface, Typeface.BOLD)
        /*ContextCompat class is used when you would like to retrieve resources,
         such as drawable or color without bother about theme*/
        selectedTv.background= ContextCompat.getDrawable(this, R.drawable.selected_option_style)
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.option_one ->{
                optionOne?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.option_two -> {
                optionTwo?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.option_three -> {
                optionThree?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.option_four -> {
                optionFour?.let{
                    selectedOptionView(it, 4)
                }
            }
            R.id.submitBtn -> {
                if(mSelectedOptionPosition==0){  //i.e. no option is selected yet
                    mCurrentPosition++           //going to next question
                    //keep moving till we have questions left and set new questions
                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            //Toast.makeText(this,"You have successfully completed the quiz.",Toast.LENGTH_SHORT).show()
                            var intent= Intent(this, ResultsActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                            intent.putExtra(Constants.TOTAL_NO_OF_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question: Question = mQuestionsList!![mCurrentPosition-1]
                    if(question.correctOption != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_style)
                    }
                    else{
                        correctAnswers++
                    }
                        answerView(question.correctOption, R.drawable.correct_option_style)

                    if(mCurrentPosition == mQuestionsList!!.size) {
                        btnSubmit?.text= "Finish"
                    }
                    else{
                        btnSubmit?.text= "Go To Next Question"
                    }
                    mSelectedOptionPosition=0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int ){  //location of drawable is in int type
           when(answer){
               1 -> {
                   optionOne?.background = ContextCompat.getDrawable(this, drawableView)
               }
               2 -> {
                   optionTwo?.background = ContextCompat.getDrawable(this, drawableView)
               }
               3 -> {
                   optionThree?.background = ContextCompat.getDrawable(this, drawableView)
               }
               4 -> {
                   optionFour?.background = ContextCompat.getDrawable(this, drawableView)
               }
           }
    }
}