package com.architecture.clean.view.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.clean.view.item.base.BaseItem

abstract class BaseRecyclerAdapter<T>(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun getItemResId(viewType: Int) : Int
    abstract fun getItemViewHolder(view: View) : BaseItem<T>


    private var mDataList: MutableList<T> = ArrayList()

    private val mContext = context

    private var mOnItemClick: ((position: Int, t: T) -> Unit)? = null

    private var mLoadingPosition = -1

    //**********************************************************************************************
    internal fun addDataList(dataList: List<T>) {
        mDataList.addAll(dataList)

        notifyDataSetChanged()
    }

    internal fun setDataList(dataList: List<T>) {
        mDataList = dataList as MutableList<T>

        notifyDataSetChanged()
    }

    internal fun addData(t: T) {
        mDataList.add(t)

        notifyDataSetChanged()
    }

    internal fun getDataList() : List<T> {
        return mDataList
    }

    internal fun setOnItemClickListener(onItemClickListener: ((position: Int, t: T) -> Unit)) {
        mOnItemClick = onItemClickListener
    }

    //**********************************************************************************************
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(getItemResId(viewType), parent, false)

        return getItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as BaseItem<T>).setContent(mContext, mDataList[position])

        holder.itemView.setOnClickListener {

            if (mOnItemClick != null) {
                mOnItemClick!!.invoke(position, mDataList[position])
            }

        }

        holder.loading(position == mLoadingPosition)

    }

    override fun getItemCount(): Int {
        return mDataList.size
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