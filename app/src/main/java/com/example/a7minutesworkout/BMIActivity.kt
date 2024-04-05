package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITSVIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView: String =
        METRIC_UNITSVIEW // A variable to hold a value to make a selected view visible
    private var binding : ActivityBmiactivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar !=null)//Support library version of android.app.Activity.getActionBar. Retrieve a reference to this activity's ActionBar.
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // it'll display the back button if we'll not use it we'll not see th back button
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
           onBackPressed()
        }
        makeVisibleMetricUnitsView()

        // setOnCheckedChangeListener {} -> will give the id of the item(in this radioItem) which is currently active
        // here we could have used the setOnClickListener() but implementing the logic of it with radioItems would have been tricky that's why we ain't using it
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId : Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else{
                makeVisibleUsUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
          calculateUnits()
        }



    }

    private fun calculateUnits(){
        if (validateMetricUnits()) {
            val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
            val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

            val bmi = (weightValue) / (heightValue * heightValue)
            dispayBMIResult(bmi)


        } else {
            Toast.makeText(this, "Please Enter The Values", Toast.LENGTH_SHORT).show()
        }
        if(validateUsUnits()){
            val usUnitsHeightValuEfEET : String =
                binding?.etUsMetricUnitHeightFeet?.text.toString()
            val usUnitHeigthValueInch : String =
                binding?.etUsMetricUnitHeightInch?.text.toString()
            val usUnitWeightValue: Float = binding?.etUsMetricUnitWeight?.text.toString().toFloat()

            val heightValue = usUnitHeigthValueInch.toFloat() + usUnitsHeightValuEfEET.toFloat() * 12

            val bmi = 703* (usUnitWeightValue / (heightValue * heightValue))

            dispayBMIResult(bmi)
        }
        else {
            Toast.makeText(this, "Please Enter The Values", Toast.LENGTH_SHORT).show()
        }
    }
    private fun makeVisibleMetricUnitsView(){ // This function will make our view(those two edit texts which are invisible by default ) visible when we will click on
            currentVisibleView = METRIC_UNITSVIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility =View.INVISIBLE

    }


    private fun makeVisibleUsUnitsView(){ // This function will make our view(those two edit texts which are invisible by default ) visible when we will click on
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility =View.INVISIBLE

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean{
        var isVlaid = true
        when{
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() ->{
                isVlaid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() ->{
                isVlaid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() ->
            {
                isVlaid = false
            }
        }
        return isVlaid
    }


    private fun dispayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription : String

        if(bmi.compareTo(15f)<= 0){
            // compareTo() function -> Compares this value with the specified value for order. Returns zero if this value is equal to the specified other value,
            // a negative number if it's less than other,
            // or a positive number if it's greater than other.
            bmiLabel ="Very Severely UnderWeight"
            bmiDescription = "Oops! You really need to take care of yourself! Eat More "
        }
        else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()// basically what we're doing is we're taking this float and then we're breaking it down into a value
        // that will not display anything like 25.211111333, whatever.
        //But it will be just cutting it off.
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription


    }
}