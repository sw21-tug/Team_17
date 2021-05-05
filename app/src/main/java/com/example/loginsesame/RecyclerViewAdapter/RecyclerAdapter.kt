package com.example.loginsesame.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsesame.R
import com.example.loginsesame.account
import kotlinx.android.synthetic.main.item_password_overview.view.*

class RecyclerAdapter
(
        private val account_list: MutableList<account>

) : RecyclerView.Adapter<RecyclerAdapter.AccountsViewHolder>() {

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

    fun addAccount(newAccount : account) {
        account_list.add(newAccount)
        notifyItemInserted(account_list.size -1)
    }

    fun listAccounts(AccountList: List<account>){
        for(account in AccountList)
        {
            addAccount(account)
        }

    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val curAccount = account_list[position]



        holder.itemView.apply {
            tv_account_name.text = curAccount.account_name
            tv_account_user.text = curAccount.account_user
        }
    }

    override fun getItemCount(): Int {
        return account_list.size
    }
}

