package com.example.drinkwaterreminder.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkwaterreminder.DrinkContainerAdaptor
import com.example.drinkwaterreminder.R
import com.example.drinkwaterreminder.data.AppDataBase
import com.example.drinkwaterreminder.data.AppRepository
import com.example.drinkwaterreminder.data.Dao
import com.example.drinkwaterreminder.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Dao
        val dao = container?.let { AppDataBase.getInstance(it.context).daoInstance }
        //binding
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container,false)

        lifecycleScope.launch{

            val drunkAmount = dao?.let { getDrinkAmount(it) } // the amount of drinks which user used
            val repository = dao?.let { AppRepository(it) }
            val user = repository?.readUserData()
            val waterAmount = user?.waterAmount // this is what app calculates for user = target

            navigateToSettingFragment(waterAmount)
            progressWheelAdjuster(drunkAmount,waterAmount)

            val today = SimpleDateFormat().format("yyy-MM-dd").format(Calendar.getInstance().time)
            val drinks = dao?.let { AppRepository(it).readDrinkDataSelectedDay(today) }

            withContext(Dispatchers.Main) {
                binding.recyclerViewHome.adapter = drinks?.let { DrinkContainerAdaptor(it) }
            }

            binding.recyclerViewHome.layoutManager =
                LinearLayoutManager(container?.context, LinearLayoutManager.HORIZONTAL,false)

            binding.recyclerViewHome.isNestedScrollingEnabled = false

            binding.btnDrink.setOnClickListener {
                it.findNavController().navigate(R.id.action_homeNavFrag_to_drinksNavFrag)
            }


        }

        return binding.root


    }




    //when user open the app for the first time , this function navigates him to setting
    private suspend fun navigateToSettingFragment(waterAmount: Int?) {
        withContext(Dispatchers.Main){
            if (waterAmount==0 || waterAmount==null) {
                findNavController().navigate(R.id.action_homeNavFrag_to_settingNavFrag)
            }
        }
    }

    private suspend fun getDrinkAmount(dao : Dao) : Int? {
        val sum = AppRepository(dao).readDrinkSumData() // it returns MutableList<Sum>
        val date = SimpleDateFormat("yyy-MM-dd").format(Calendar.getInstance().time).toString()
        return if (sum!![sum.lastIndex].date == date) {
            sum[sum.lastIndex].total
        } else {
            0
            //returns zero if the date is not today
        }
    }

    private suspend fun progressWheelAdjuster(drunkAmount: Int? , waterAmount:Int?) {
        withContext(Dispatchers.Main) {
            binding.circularProgressBar.progressMax = waterAmount!!.toFloat()
            binding.circularProgressBar.progress = drunkAmount!!.toFloat()
            binding.tvDrunkML.text = drunkAmount.toString()
            binding.tvTargetML.text = waterAmount.toString()
        }
    }

}