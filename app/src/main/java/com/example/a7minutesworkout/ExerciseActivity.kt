package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConformationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var binding: ActivityExerciseBinding? = null
    private var restProgress=0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress=0

    private var exerciseTimeDuration: Long = 30

    private var exerciseList : ArrayList<ExerciseModel1>? = null
    private var currentExercisePosition = -1 // this variable will tell us at which exercise we're currently are.

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //setSupportActionBar -> Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(binding?.toolbarExercises)
        if(supportActionBar !=null)//Support library version of android.app.Activity.getActionBar. Retrieve a reference to this activity's ActionBar.
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // it'll display the back button if we'll not use it we'll not see th back button
        }
        binding?.toolbarExercises?.setNavigationOnClickListener {
            //setNavigationOnClickListener -> this is an onClockListener for the navigation, and the toolbar is a navigaton bar
            // onBackPressed()//This method will work in the same our back button on phone work
        // Above method is commented out because now we're implementing a custom Dialog before the back button action is performed

        customDialogForBackButton()
        }

        exerciseList = Constant.defaultExerciseList()

        setupRestView()


        tts = TextToSpeech(this,this)

        setUpRecyclerViewStatus()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        customDialogForBackButton()

        //super.onBackPressed()

    }

    private fun customDialogForBackButton() { 
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConformationBinding.inflate(layoutInflater)// we're inflating the xml layout file for our customDialog
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false) // Here we're saying that if we'll click anywhere other than the DialogBox the dialog box will not disappear
        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()

        }
            customDialog.show()


    }

    private fun setUpRecyclerViewStatus() {
        // layoutManager property help you to set a view to your
        // recyclerView like horizontal, verical, or in gridview
         binding?.rvExerciseStats?.layoutManager =
             // below is just LinearLayout Manager you want then you can can also use GridLayoutManager
             LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStats?.adapter = exerciseAdapter
    }


    private fun setupRestView(){

        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minutesworkout/"+
                    R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false // it'll not let the music play on  loop
            player?.start()
        }
        catch (e: Exception){
            e.printStackTrace()

        }



        binding?.flRestView?.visibility= View.VISIBLE
        binding?.joBhiHai?.visibility= View.VISIBLE
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE
        binding?.txtUpcomingExercise?.visibility = View.VISIBLE
        binding?.txtUpcomingExerciseName?.visibility = View.VISIBLE



        if (restTimer!=null){
            restTimer?.cancel()
            restProgress= 0
        }

        binding?.txtUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()

}
    private fun setupExerciseView(){ // when the rest timer is gonna get replaced by the exercise timer and progressBar
        binding?.flRestView?.visibility= View.INVISIBLE
        binding?.joBhiHai?.visibility= View.INVISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.txtUpcomingExercise?.visibility = View.INVISIBLE
        binding?.txtUpcomingExerciseName?.visibility = View.INVISIBLE
        // Resetting the Exercise timer everytime before we make our exercise timer visible in the below code
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        //setImageResource-> If you don't want to set image to xml file and show image programmatically then you need to use this method.
       binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()


        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress // so however much the rest progress will be, that will be the progress of the progressBar
        restTimer = object: CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) { // What we want to do with every single tick(After every 1000 miliseconds what we want to do),
                // so after every tick call the code written in onTick Code.
                restProgress++
                // we did this -10 because we want to use the timer in the backward counting if
                // you'll remove the -10 the timer will start running in from 0 to 10 unlike right now i.e. from 10 to 0
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            //Once your timer is done, onFinsih code will be called automatically

            override fun onFinish() {
                currentExercisePosition++
                // Below we are setting up the colour of the previous exercise color in the bottom recyclerView
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()  //Notifying our adapter about this change coz if we dont do this it will not change the recyclerView  appearnce or anything
                // because it doesn't know that data is changed  because what we've now is entirely data driven recyclerView  manually, but what we're doing is we're saying
                //
                //we change the data and recycler view itself should change the appearance based on the data.
                //
                //So now we're well notifying the adapter that the data has changed.
                //
                //So the adapter will run on bind view holder once again and also on create view order.So basically we'll call these methods once again and it will then see.
                //
                //Okay, well, I'm just going to use the latest data or the latest property values for those data.
                //
                //So I'm going to see, is the item selected so the current exercise or is the exercise completed?
                setupExerciseView()
            }

        }.start()
    }
    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress // so however much the rest progress will be, that will be the progress of the progressBar
        exerciseTimer = object: CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) { // What we want to do with every single tick(After every 1000 miliseconds what we want to do),
                // so after every tick call the code written in onTick Code.
                exerciseProgress++
                // we did this -10 because we want to use the timer in the backward counting if
                // you'll remove the -10 the timer will start running in from 0 to 10 unlike right now i.e. from 10 to 0
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }
            //Once your timer is done, onFinsih code will be called automatically

            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setisCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{
                    finish() // it'll finish this activity
                    val intent = Intent(this@ExerciseActivity, finish::class.java)
                    //So the problem here and this is something we have not seen yet, but this is something that is really
                    //important as well, is we need to add more details to this to the this keyword, because now this is
                    //not going to be at this point, our class, we think it is it's not going to be the exercise activity.
                    //But if you have a overt, you see it will be the countdown timer because this countdown timer is an
                    //object itself.
                    //So now when you're using the this keyword, you're always talking about the context of your current
                    //class.
                    //And the current class that we're creating.
                    //Here is an object countdown timer.
                    //So this entire code here, if you look at it, let me put it into a separate line here.
                    //Like, so this entire code, this here is an object that we're creating.
                    //And if you're using the this keyword, then it will use the nearest class, so to speak, in which it
                    //exists.
                    //So if you have a nesting situation, then it will take the one that is in its current nesting state.
                    //So if it's that this and the countdown timer and let's say you had another class inside of or create
                    //another object inside of this countdown timer, and then you use this, then it will be this internal
                    //object.
                    //And now as we're using the countdown timer, it will be the countdown timer that is used by this.
                    //So how do we get around this?
                    //How can we use this with our exercise activity?
                    //Well, we can do that by using this at exercise activity.
                    startActivity(intent)
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) { // call to signal the completion of text to speech engine initialization
        // This method is basically used to set the language of the speak
        val result = tts!!.setLanguage(Locale.UK)

        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
            // This will check if the data of that language is avilable in the system or not and second OR statement will check if the enterd language is supported by your device or not
            Log.e("TTS", "The Specified Language Is Not Supported")

        }
        else{
            Log.e("TTS", "Initialization Failed!!")
        }
    }
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "" )
    }
}