package com.example.smart_food_planner.ui

import android.app.AlertDialog
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CalendarView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.smart_food_planner.R
import com.example.smart_food_planner.database.dataclasses.Meal_Data
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.model.dataClasses.Meals
import com.example.smart_food_planner.ui.adapters.Meal_Adapter
import com.example.smart_food_planner.viewmodel.ChatViewModel
import com.example.smart_food_planner.viewmodel.Meal_Database_Viewmodel
import com.example.smart_food_planner.viewmodel.MealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar
import kotlin.getValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Calender.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calender : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var backToHome: ImageButton
    private lateinit var TodaybreakfastImage: ImageView
    private lateinit var TodaylunchImage: ImageView
    private lateinit var TodayDinnerImage: ImageView
    private lateinit var TodaybreakfastText: TextView
    private lateinit var TodaylunchText: TextView
    private lateinit var TodayDinnerText: TextView
    private lateinit var numberOfMealsInWeekText: TextView
    private lateinit var percentOfMealsInWeek: TextView

    private lateinit var goToMealsButton: Button

    private lateinit var calendarView: CalendarView

    var allMealsInDatabase = listOf<Meal_Data>()


    val calendar = Calendar.getInstance()

    var today = calendar.get(Calendar.DAY_OF_MONTH)
    var thisMonth = calendar.get(Calendar.MONTH)+1
    var thisYear = calendar.get(Calendar.YEAR)

    var calenderDay = today
    var calenderMonth = thisMonth
    var calenderyear =  thisYear


    private var mealsList: List<Meal> = emptyList()

    var TodayBreakFastId = -1
    var TodayLaunchId = -1
    var TodayDinnerId = -1



    class Meal_Database_ViewmodelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(Meal_Database_Viewmodel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return Meal_Database_Viewmodel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    private val mealDatabaseViewModel: Meal_Database_Viewmodel by viewModels<Meal_Database_Viewmodel> {
        Meal_Database_ViewmodelFactory(requireActivity().application)
    }


    private val mealViewModel: MealsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backToHome = view.findViewById(R.id.btnBack)
        TodaylunchImage = view.findViewById(R.id.ivLunchPlus)
        TodaybreakfastImage = view.findViewById(R.id.ivBreakfastPlus)
        TodayDinnerImage = view.findViewById(R.id.ivDinnerPlus)

        TodaybreakfastText = view.findViewById(R.id.tvBreakfastLabel)
        TodaylunchText = view.findViewById(R.id.tvLunchLabel)
        TodayDinnerText = view.findViewById(R.id.tvDinnerLabel)
        goToMealsButton = view.findViewById(R.id.btnGoToMeals)
        numberOfMealsInWeekText = view.findViewById(R.id.numberOfMealsInWeek)
        percentOfMealsInWeek = view.findViewById(R.id.percentOfMealsInWeek)

        calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        observeAllMeals()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            val realMonth = month + 1

            calenderDay = dayOfMonth
            calenderMonth = realMonth
            calenderyear = year
        }


        val clickAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.add_button_click)

        goToMealsButton.setOnClickListener {
            checkMealExistOnce(calenderDay, calenderMonth, calenderyear, 1) { id ->
                val MealIsExist = id != -1
                showGoToMealsDialog(MealIsExist, calenderDay, calenderMonth, calenderyear)
            }
        }


        mealViewModel.getMeals()

        mealViewModel.meals.observe(viewLifecycleOwner) { list ->
            mealsList = list
        }







        TodaybreakfastImage.setOnClickListener {
            view.startAnimation(clickAnim)
            if (TodayBreakFastId == -1) {
                showAddEditMealDialog(1, true, today, thisMonth, thisYear)

            } else {
                showEditDeleteDialog(TodayBreakFastId, today, thisMonth, thisYear, 1)
            }

        }
        TodaylunchImage.setOnClickListener {
            view.startAnimation(clickAnim)


            if (TodayLaunchId == -1) {
                showAddEditMealDialog(2, true, today, thisMonth, thisYear)

            } else {
                showEditDeleteDialog(TodayLaunchId, today, thisMonth, thisYear, 2)
            }

        }
        TodayDinnerImage.setOnClickListener {
            view.startAnimation(clickAnim)
            if (TodayDinnerId == -1) {
                showAddEditMealDialog(3, true, today, thisMonth, thisYear)
            } else {
                showEditDeleteDialog(TodayDinnerId, today, thisMonth, thisYear, 3)
            }

        }




        backToHome.setOnClickListener {



                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, home())
                    .commit()

                val mainActivity = activity as MainActivity
                val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav.visibility = View.VISIBLE
                bottomNav.selectedItemId = R.id.nav_home

        }




        checkMealExists(today, thisMonth, thisYear, 1)
        checkMealExists(today, thisMonth, thisYear, 2)
        checkMealExists(today, thisMonth, thisYear, 3)



        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Calender.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Calender().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun showAddEditMealDialog(
        numberOfMeal: Int,
        isAdd: Boolean,
        day: Int,
        month: Int,
        year: Int
    ) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_meal, null)

        val rvMeals = dialogView.findViewById<RecyclerView>(R.id.meals_recyclerView)

        rvMeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvMeals.setHasFixedSize(true)


        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<ImageButton>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()


        val width = (350 * resources.displayMetrics.density).toInt()
        val height = (550 * resources.displayMetrics.density).toInt()

        dialog.window?.setLayout(width, height)



        rvMeals.adapter = Meal_Adapter(
            mealsList,
            mealDatabaseViewModel,
            numberOfMeal,
            dialog,
            isAdd,
            day,
            month,
            year
        )


    }


    private fun checkMealExists(day: Int, month: Int, year: Int, mealNumber: Int) {

        mealDatabaseViewModel.getIdOfMeal(day, month, year, mealNumber)
            .observe(viewLifecycleOwner) { mealId ->
                if (mealId != null && mealId != 0) {

                    putSelectedMealInUi(mealId, mealNumber)

                } else {
                    if (mealNumber == 1) {
                        TodaybreakfastImage.setImageResource(R.drawable.ic_add)
                        TodaybreakfastText.text = "Breakfast"
                        TodayBreakFastId = -1
                    } else if (mealNumber == 2) {
                        TodaylunchImage.setImageResource(R.drawable.ic_add)
                        TodaylunchText.text = "Lunch"
                        TodayLaunchId = -1
                    } else if (mealNumber == 3) {
                        TodayDinnerImage.setImageResource(R.drawable.ic_add)
                        TodayDinnerText.text = "Dinner"
                        TodayDinnerId = -1
                    }
                }
            }
    }

    private fun putSelectedMealInUi(idOfMeal: Int, mealNumber: Int) {

        mealViewModel.meals.observe(viewLifecycleOwner) { mealsList ->

            val selectedMeal = mealsList.find { it.idMeal.toInt() == idOfMeal }

            selectedMeal?.let { meal ->

                when (mealNumber) {
                    1 -> { // Breakfast

                        Glide.with(this)
                            .load(meal.strMealUrl)
                            .into(TodaybreakfastImage)

                        TodaybreakfastText.text = meal.strMealTitle
                        TodayBreakFastId = meal.idMeal.toInt()

                    }

                    2 -> { // Lunch
                        Glide.with(this)
                            .load(meal.strMealUrl)
                            .into(TodaylunchImage)

                        TodaylunchText.text = meal.strMealTitle
                        TodayLaunchId = meal.idMeal.toInt()
                    }

                    3 -> { // Dinner
                        Glide.with(this)
                            .load(meal.strMealUrl)
                            .into(TodayDinnerImage)

                        TodayDinnerText.text = meal.strMealTitle
                        TodayDinnerId = meal.idMeal.toInt()
                    }
                }
            }
        }
    }


    private fun showEditDeleteDialog(
        mealId: Int,
        day: Int,
        month: Int,
        year: Int,
        mealNumber: Int
    ) {
        val dialogView = layoutInflater.inflate(R.layout.edit_or_delet_dialog, null)

        val mealImage = dialogView.findViewById<ShapeableImageView>(R.id.Meal_Image_edit_delete)
        val mealTitle = dialogView.findViewById<TextView>(R.id.Meal_Title_edit_delete)
        val mealDesc = dialogView.findViewById<TextView>(R.id.Meal_Description_edit_delete)
        val editButton = dialogView.findViewById<MaterialButton>(R.id.EditButton)
        val deleteButton = dialogView.findViewById<MaterialButton>(R.id.DeleteButton)





        getMealByIdFromApi(mealId) { meal ->
            mealTitle.text = meal?.strMealTitle
            mealDesc.text = meal?.strMealDescription
            Glide.with(this)
                .load(meal?.strMealUrl)
                .into(mealImage)
        }


        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<ImageButton>(R.id.btn_close_edit_delete).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(R.drawable.chat_background)

        editButton.setOnClickListener {
            dialog.dismiss()
            showAddEditMealDialog(mealNumber, false, today, thisMonth, thisYear)
        }


        deleteButton.setOnClickListener {
            deleteMealFromDatabasee(mealId, day, month, year, mealNumber)
            dialog.dismiss()

        }

        dialog.show()

        val width = (350 * resources.displayMetrics.density).toInt()
        val height = (550 * resources.displayMetrics.density).toInt()
        dialog.window?.setLayout(width, height)
    }

    private fun getMealByIdFromApi(mealId: Int, onResult: (Meal?) -> Unit) {
        mealViewModel.meals.observe(viewLifecycleOwner) { mealsList ->
            val foundMeal = mealsList.find { it.idMeal.toInt() == mealId }
            onResult(foundMeal)
        }


    }


    private fun deleteMealFromDatabasee(id: Int, day: Int, month: Int, year: Int, mealNumber: Int) {
        mealDatabaseViewModel.deleteMeal(Meal_Data(id, day, month, year, mealNumber))
    }




    private fun showGoToMealsDialog(MealIsExist: Boolean, day: Int, month: Int, year: Int) {
        val dialogView = layoutInflater.inflate(R.layout.add_meal_from_calender_dialog, null)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.chat_background)




        val MealcardView = dialogView.findViewById<CardView>(R.id.cvMealCard)
        val EmptycardView = dialogView.findViewById<CardView>(R.id.cvEmptyMealCard)

        val tvSelectedDate = dialogView.findViewById<TextView>(R.id.tvSelectedDate)
        val monthName = when (month) {
            1 -> "Jan"; 2 -> "Feb"; 3 -> "Mar"; 4 -> "Apr"; 5 -> "May"; 6 -> "Jun"
            7 -> "Jul"; 8 -> "Aug"; 9 -> "Sep"; 10 -> "Oct"; 11 -> "Nov"; 12 -> "Dec"
            else -> ""
        }
        tvSelectedDate.text = "$day $monthName $year"

        if (MealIsExist) {
            EmptycardView.visibility = View.GONE
            MealcardView.visibility = View.VISIBLE
        } else {
            MealcardView.visibility = View.GONE
            EmptycardView.visibility = View.VISIBLE
        }

        // ------------------- CardView components -------------------
        val mealImage = dialogView.findViewById<ShapeableImageView>(R.id.ivMealImage)
        val mealTitle = dialogView.findViewById<TextView>(R.id.tvMealTitle)
        val mealDescription = dialogView.findViewById<TextView>(R.id.tvMealDescription)
        val btnEditMeal = dialogView.findViewById<MaterialButton>(R.id.btnEditMeal)
        val btnDeleteMeal = dialogView.findViewById<MaterialButton>(R.id.btnDeleteMeal)
        val btnClose = dialogView.findViewById<ImageButton>(R.id.btnCloseMealType)

        // ------------------- Meal selection buttons -------------------
        val btnBreakfast = dialogView.findViewById<MaterialButton>(R.id.btnBreakfast)
        val btnLunch = dialogView.findViewById<MaterialButton>(R.id.btnLunch)
        val btnDinner = dialogView.findViewById<MaterialButton>(R.id.btnDinner)
        val addNewMealButton = dialogView.findViewById<Button>(R.id.btnAddMealFromEmpty)

        var numberOfMeal = 1
        val mealButtons = listOf(btnBreakfast, btnLunch, btnDinner)





        fun updateSelection(selectedButton: MaterialButton) {
            mealButtons.forEach { button ->
                if (button == selectedButton) {
                    button.setBackgroundColor(Color.parseColor("#FF9800"))
                    button.setTextColor(Color.WHITE)
                } else {
                    button.setBackgroundColor(Color.TRANSPARENT)
                    button.setTextColor(Color.parseColor("#B0BEC5"))
                }
            }
        }



        updateSelection(btnBreakfast)

        // ------------------- Fun to put meal on CardView -------------------
        fun putMealOnCardView(mealId: Int) {


            val selectedMeal = mealsList.find { it.idMeal.toInt() == mealId }

            selectedMeal?.let { meal ->
                mealTitle.text = meal.strMealTitle
                mealDescription.text = meal.strMealDescription
                Glide.with(dialogView.context)
                    .load(meal.strMealUrl)
                    .into(mealImage)





                btnEditMeal.setOnClickListener {
                    dialog.dismiss()
                     showAddEditMealDialog(numberOfMeal, false, day, month, year)
                  //  Log.d("TTTTEST", "$day    $month   $year   ")
                }


                btnDeleteMeal.setOnClickListener {
                    dialog.dismiss()
                     mealDatabaseViewModel.deleteMeal(Meal_Data(meal.idMeal.toInt(), day, month, year, numberOfMeal))
                }


            }
        }


        fun updateUi() {


            checkMealExistOnce(day, month, year, numberOfMeal) { mealId ->
                if (mealId != -1) {
                    putMealOnCardView(mealId)
                    EmptycardView.visibility = View.GONE
                    MealcardView.visibility = View.VISIBLE
                } else {
                    MealcardView.visibility = View.GONE
                    EmptycardView.visibility = View.VISIBLE
                }
            }


        }




        btnBreakfast.setOnClickListener {
            updateSelection(btnBreakfast)
            numberOfMeal = 1
            updateUi()


        }
        btnLunch.setOnClickListener {
            updateSelection(btnLunch)
            numberOfMeal = 2
            updateUi()
        }
        btnDinner.setOnClickListener {
            updateSelection(btnDinner)
            numberOfMeal = 3
            updateUi()
        }

        addNewMealButton.setOnClickListener {
            showAddEditMealDialog(numberOfMeal, true, day, month, year)
            dialog.dismiss()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
        updateUi()
        val width = (350 * resources.displayMetrics.density).toInt()
        val height = (600 * resources.displayMetrics.density).toInt()
        dialog.window?.setLayout(width, height)
    }


    fun checkMealExistOnce(
        day: Int,
        month: Int,
        year: Int,
        mealNumber: Int,
        onResult: (Int) -> Unit
    ) {
        val liveData = mealDatabaseViewModel.getIdOfMeal(day, month, year, mealNumber)
        val observer = object : androidx.lifecycle.Observer<Int?> {
            override fun onChanged(mealId: Int?) {
                liveData.removeObserver(this)
                if (mealId != null && mealId != 0) {
                    onResult(mealId)
                } else {
                    onResult(-1)
                }
            }
        }
        liveData.observeForever(observer)
    }


    private fun observeAllMeals() {
        mealDatabaseViewModel.getAllMealsLive().observe(viewLifecycleOwner) { meals ->
            allMealsInDatabase = meals
            countMealsThisWeek()

        }
    }


    fun countMealsThisWeek() {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, thisMonth - 1)
        calendar.set(Calendar.YEAR, thisYear)


        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, todayDay)


        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)


        val daysSinceFriday = (dayOfWeek + 1) % 7
        calendar.add(Calendar.DAY_OF_MONTH, -daysSinceFriday)
        val startOfWeekDay = calendar.get(Calendar.DAY_OF_MONTH)
        val startOfWeekMonth = calendar.get(Calendar.MONTH) + 1
        val startOfWeekYear = calendar.get(Calendar.YEAR)


        calendar.add(Calendar.DAY_OF_MONTH, 6)
        val endOfWeekDay = calendar.get(Calendar.DAY_OF_MONTH)
        val endOfWeekMonth = calendar.get(Calendar.MONTH) + 1
        val endOfWeekYear = calendar.get(Calendar.YEAR)


        var mealCount = 0
        allMealsInDatabase.forEach { meal ->

            val mealDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, meal.year)
                set(Calendar.MONTH, meal.month - 1)
                set(Calendar.DAY_OF_MONTH, meal.day)
            }

            val startCal = Calendar.getInstance().apply {
                set(Calendar.YEAR, startOfWeekYear)
                set(Calendar.MONTH, startOfWeekMonth - 1)
                set(Calendar.DAY_OF_MONTH, startOfWeekDay)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val endCal = Calendar.getInstance().apply {
                set(Calendar.YEAR, endOfWeekYear)
                set(Calendar.MONTH, endOfWeekMonth - 1)
                set(Calendar.DAY_OF_MONTH, endOfWeekDay)
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }

            if (mealDate in startCal..endCal) {
                mealCount++
            }

            numberOfMealsInWeekText.text = mealCount.toString()+"/21"
            val percent = (mealCount.toDouble() / 21) * 100
            percentOfMealsInWeek.text = String.format("%.1f%%", percent)
        }


    }



}