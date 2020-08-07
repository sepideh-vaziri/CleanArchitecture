package com.baman.example.view.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baman.example.view.item.base.BaseItem

abstract class BasePagedListAdapter<T>(
    context: Context,
    @NonNull diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    abstract fun getItemResId(viewType: Int) : Int
    abstract fun getItemViewHolder(view: View, viewType: Int) : BaseItem<T>
    abstract fun isViewTypeItem(position: Int) : Boolean
    abstract fun isItemClickable(position: Int) : Boolean
    abstract fun hasMore() : Boolean

    private val mContext = context

    private var mOnItemClick: ((position: Int, t: T) -> Unit)? = null

    private var mLoadingPosition = -1

    //Adapter methods ******************************************************************************
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)

        val view = layoutInflater.inflate(getItemResId(viewType), parent, false)
        val viewHolder = getItemViewHolder(view, viewType)
        viewHolder.setWidthSize(mContext)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (isViewTypeItem(position)) {
            (viewHolder as BaseItem<T>).setContent(mContext, getItem(position))
        }

        if (isItemClickable(position)) {
            viewHolder.itemView.setOnClickListener {

                if (mOnItemClick != null && getItem(position) != null) {
                    mOnItemClick!!.invoke(position, getItem(position)!!)
                }

            }
        }

        (viewHolder as BaseItem<T>).setVisibilityOfLoadingItem(hasMore())

        viewHolder.loading(position == mLoadingPosition)

    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() == 0) 0 else (super.getItemCount() + 1)
    }

    internal fun setOnItemClickListener(onItemClickListener: ((position: Int, t: T) -> Unit)) {
        mOnItemClick = onItemClickListener
    }

    fun showLoading(position: Int) {
        mLoadingPosition = position

        notifyItemChanged(position)
    }

    fun hideLoading(position: Int) {
        mLoadingPosition = -1

        notifyItemChanged(position)
    }

}