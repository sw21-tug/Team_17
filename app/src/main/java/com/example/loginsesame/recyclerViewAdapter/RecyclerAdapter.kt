package com.example.loginsesame.recyclerViewAdapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsesame.R
import com.example.loginsesame.Account
import kotlinx.android.synthetic.main.item_password_overview.view.*

class RecyclerAdapter(
    private val accountList: MutableList<Account>,
    private val openOptionsMenuListener: OpenOptionsMenu
) :
    RecyclerView.Adapter<RecyclerAdapter.AccountsViewHolder>() {

    class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_password_overview,
                parent,
                false
            )
        )
    }

    fun addAccount(newAccount: Account) {
        accountList.add(newAccount)
        notifyItemInserted(accountList.size - 1)
    }

    fun resetList() {
        accountList.clear()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val curAccount = accountList[position]

        holder.itemView.apply {
            tvAccountName.text = curAccount.accountName
            tvAccountUser.text = curAccount.accountUser
            btnDots.setOnClickListener{
                openOptionsMenuListener.onOptionsMenuClicked(position)
            }
        }
    }

    interface OpenOptionsMenu {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}

