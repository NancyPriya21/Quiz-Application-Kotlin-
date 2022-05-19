package com.example.flagsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

/*to remove the action bar at top, have changed manifest.xml theme from theme.flagsQuizApp
 to theme.MaterialComponents.DayNight.NoActionBar. Same can be done using themes.xml parent style*/
// to avoid app rotation when screen is rotated, added android:screenOrientation="portrait" in manifest//
//added a background image(imageView) in drawable folder.res>new>image asset. choose action bar and tab items as image type
//status bar removed in themes.xml file
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn: Button =findViewById(R.id.btnStart)
        val etName: EditText=findViewById(R.id.et_name)

        startBtn.setOnClickListener {
            if(etName.text.isEmpty()){
                Toast.makeText(this@MainActivity,
                    "Please enter your name", Toast.LENGTH_LONG ).show()
            }
            else{
               val intent= Intent(this, QuizQuestionActivity::class.java)
                //passing additional data from one activity to another, here input username
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()  //exiting the first activity entirely
            }
        }
    }
}