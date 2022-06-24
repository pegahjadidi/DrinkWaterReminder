package com.example.drinkwaterreminder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkwaterreminder.R
import com.example.drinkwaterreminder.data.AppDataBase
import com.example.drinkwaterreminder.data.AppRepository
import com.example.drinkwaterreminder.data.Drink
import com.example.drinkwaterreminder.databinding.SingleDrinkContainerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinkContainerAdaptor(var drinksList : MutableList<Drink>)
    :RecyclerView.Adapter<DrinkContainerAdaptor.DrinkContainerViewHolder>() {

    inner class DrinkContainerViewHolder(private val binding : SingleDrinkContainerBinding)
        :RecyclerView.ViewHolder(binding.root){

        private val drinkImage = binding.drinkImage
        private val drinkText = binding.drinkText
        private val drinkAmount = binding.amountText


        fun viewHolderBinder(drink : Drink) {
            lateinit var view: View

            drinkText.text = drinkNameGetter(drink.drink,view.context )
            drinkAmount.text = drink.amount

            val imageId = view.context.resources.getIdentifier(
                "drawable/ic_${drink.drink}",
                null,
                null
            )
            drinkImage.setImageResource(imageId)

            view.setOnClickListener {
                MaterialAlertDialogBuilder(view.context)
                    .setMessage(view.context.resources.getString(R.string.alert_dialog_builder_homeFrag))
                    .setPositiveButton(view.context.resources.getString(R.string.alert_dialog_builder_homeFrag_yes)) {
                            _, _ ->
                        val dao = AppDataBase.getInstance(view.context).daoInstance
                        CoroutineScope(Dispatchers.IO).launch {
                            val repository = AppRepository(dao)
                            repository.deleteSelectedDrinkData(drink)
                            it.findNavController().navigate(R.id.action_homeNavFrag_self)
                        }
                    }
                    .setNegativeButton(view.context.resources.getString(R.string.alert_dialog_builder_homeFrag_no)) {
                        dialogInterface,_ -> dialogInterface.cancel()
                    }
                    .show()
            }
            /* for clicking each item in recycler view -> alert dialog (yes-no button)
             -> if yes , delete and go back to home fragment
             -> if no , cancel the alert dialog
             */
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkContainerViewHolder {
        val layoutInflater = SingleDrinkContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DrinkContainerViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: DrinkContainerViewHolder, position: Int) {

        holder.viewHolderBinder(drinksList[position])

    }

    override fun getItemCount(): Int {
       return drinksList.size
    }

    private fun drinkNameGetter(drink: String,context: Context): String{
        when (drink) {
            "water" -> context.getString(R.string.water)
            "coffee" -> context.getString(R.string.coffee)
            "juice" -> context.getString(R.string.juice)
            "milk" -> context.getString(R.string.milk)
            "energy" -> context.getString(R.string.energy)
            "yogurt" -> context.getString(R.string.yogurt)
            "tea" -> context.getString(R.string.tea)
            "beer" -> context.getString(R.string.beer)
        }
        return drink
    }



}
