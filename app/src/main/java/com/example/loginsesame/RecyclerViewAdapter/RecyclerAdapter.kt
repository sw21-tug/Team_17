package com.example.loginsesame.RecyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsesame.Account
import com.example.loginsesame.EditVaultEntry
import com.example.loginsesame.R
import com.example.loginsesame.helper.LogTag
import kotlinx.android.synthetic.main.item_password_overview.view.*

class RecyclerAdapter(private val accountList: MutableList<Account>) :
    RecyclerView.Adapter<RecyclerAdapter.AccountsViewHolder>() {
    private val logTag = LogTag()
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


    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val curAccount = accountList[position]

        holder.itemView.apply {
            tvAccountName.text = curAccount.accountName
            tvAccountUser.text = curAccount.accountUser

            holder.itemView.setOnClickListener(View.OnClickListener {

                Log.d(logTag.LOG_RECYCLER_ADAPTER, "Recycler Pressed Position $position")
                Log.d(logTag.LOG_RECYCLER_ADAPTER, "Database Id = ${curAccount.accountId}")

                openEditActivity(context, curAccount.accountId)

            })
        }


    }

    private fun openEditActivity(context: Context, accountId: Int) {
        val i = Intent(context, EditVaultEntry::class.java)
        //i.putExtra("val", accountId);
        context.startActivity(i)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}

