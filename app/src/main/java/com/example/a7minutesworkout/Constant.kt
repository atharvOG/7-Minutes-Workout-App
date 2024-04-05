package com.example.a7minutesworkout

object Constant {
    fun defaultExerciseList(): ArrayList<ExerciseModel1>{
        val exerciseList = ArrayList<ExerciseModel1>()
        val jumpingJacks = ExerciseModel1(1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)


    val abdominalCrunch = ExerciseModel1(2,
        "Abdominal Crunches",
        R.drawable.ic_abdominal_crunch,
        false,
        false
    )
    exerciseList.add(abdominalCrunch)



        val highKneesRunnningInPlace = ExerciseModel1(3,
            "High Knees Runnning In Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKneesRunnningInPlace)


        val lunges = ExerciseModel1(4,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunges)


        val plank = ExerciseModel1(5,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)


        val plush = ExerciseModel1(6,
            "Push Up",
            R.drawable.ic_push_up,
        false,
            false
        )
        exerciseList.add(plush)


      val pushUpRoatation = ExerciseModel1(7,
          "Push Up And Rotation ",
          R.drawable.ic_push_up_and_rotation,
          false,
          false
      )
        exerciseList.add(pushUpRoatation)




        val sidePlank = ExerciseModel1(8,
        "Side Plank ",
        R.drawable.ic_side_plank,
        false,
        false)
        exerciseList.add(sidePlank)



        val squat = ExerciseModel1(9,
        "Squats",
        R.drawable.ic_squat,
        false,
        false)
        exerciseList.add(squat)



        val setUpOntoChair = ExerciseModel1(10,
        "Set Up Onto Chair",
        R.drawable.ic_step_up_onto_chair,
        false,
        false)
        exerciseList.add(setUpOntoChair)



        val tricepsDipOnChair = ExerciseModel1(11,
        "Triceps Dip On Chair", R.drawable.ic_step_up_onto_chair,
        false,
        false)
        exerciseList.add(tricepsDipOnChair)




        val wallSit = ExerciseModel1(12,
        "Wall Sit",
        R.drawable.ic_wall_sit,
        false,
        false)
        exerciseList.add(wallSit)


        return  exerciseList
}
}