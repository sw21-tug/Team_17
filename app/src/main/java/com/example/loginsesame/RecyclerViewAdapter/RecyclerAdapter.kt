package com.example.loginsesame.RecyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsesame.Account
import com.example.loginsesame.EditVaultEntry
import com.example.loginsesame.R
import com.example.loginsesame.helper.LogTag
import kotlinx.android.synthetic.main.item_password_overview.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private val accountList: MutableList<Account>) :
    RecyclerView.Adapter<RecyclerAdapter.AccountsViewHolder>(), Filterable {

    var accountFilterList = ArrayList<Account>()

    init {
        accountFilterList = accountList as ArrayList<Account>
    }

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
        val curAccount = accountFilterList[position]
        val curFilterAccount = accountFilterList[position]

        holder.itemView.apply {
            tvAccountName.text = curAccount.accountName
            tvAccountUser.text = curAccount.accountUser

            holder.itemView.setOnClickListener(View.OnClickListener {

                Log.d(logTag.LOG_RECYCLER_ADAPTER, "Recycler Pressed Position $position")
                Log.d(logTag.LOG_RECYCLER_ADAPTER, "Database Id = ${curAccount.accountId}")

                openEditActivity(context, curAccount.accountId)

            })
        }

        holder.itemView.apply {
            tvAccountName.text = curFilterAccount.accountName
            tvAccountUser.text = curFilterAccount.accountUser
        }

    }

    private fun openEditActivity(context: Context, accountId: Int) {
        val i = Intent(context, EditVaultEntry::class.java)
        i.putExtra("accountId", accountId)
        context.startActivity(i)
    }

    override fun getItemCount(): Int {
        return accountFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    accountFilterList = accountList as ArrayList<Account>
                } else {
                    val resultList = ArrayList<Account>()
                    for (row in accountList) {
                        if (row.accountName.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                        else if (row.accountUser.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    accountFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = accountFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                accountFilterList = results?.values as ArrayList<Account>
                notifyDataSetChanged()
            }
        }
    }
}
