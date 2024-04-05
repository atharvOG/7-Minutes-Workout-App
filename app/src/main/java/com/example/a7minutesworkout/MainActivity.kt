package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Below we're preparing our binding object
        //Using .infilate() to inflate the layout inflater to inflate the xml file
        binding = ActivityMainBinding.inflate(layoutInflater)
    //above we prepared our binding object and now we can use it below first we'll comment out the below line and do it with the method of viewBinding
        //setContentView(R.layout.activity_main)
        setContentView(binding?.root)
        /**
        So what basically happens is it says use this XML file called Activity Main and inflate it with the
        layout inflator and store it inside of this binding object.
        And now use the route of this binding object which is the main activities XML files root container,
         */


        //Now what we can do is instead of using the findviewby ID
        //approach like we did below(now commented oue),we can use viewBinding Instead



        //val flStartButton: FrameLayout = findViewById(R.id.flstart)


// ADVANTAGE OF VIEWBINDING
       /** we don't need to create this variable anymore.
        We don't need to create any variable for our user interface items.
        We can directly access their IDs inside of this binding object as you know it.
        It contains this entire XML file which also contains the individual IDs
        So we can now access the IDs directly, even though we need to use question marks because the binding
        object is available and the frame layout is also ineligible.*/

            binding?.flstart?.setOnClickListener {
                //Toast.makeText(this, "Clicked The Start Button", Toast.LENGTH_SHORT).show()
                // Here we are treating our frame layout as a clickable button that's why we earlier in xml file we assigned it an id. and made it a clickable button here.
                //

                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)

            }
        binding?.flHistory?.setOnClickListener {
            //Toast.makeText(this, "Clicked The Start Button", Toast.LENGTH_SHORT).show()
            // Here we are treating our frame layout as a clickable button that's why we earlier in xml file we assigned it an id. and made it a clickable button here.
            //

            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)

        }
        binding?.flBMI?.setOnClickListener {
            //Toast.makeText(this, "Clicked The Start Button", Toast.LENGTH_SHORT).show()
            // Here we are treating our frame layout as a clickable button that's why we earlier in xml file we assigned it an id. and made it a clickable button here.
            //

            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)

        }



    }
//setting binding back to null its very important otherwise thinks will  not run proper in your app
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}