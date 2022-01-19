package com.surelabsid.newmrjempoot.settings.helpcenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.surelabsid.newmrjempoot.databinding.ItemAdapterFaqBinding
import com.surelabsid.newmrjempoot.databinding.ListGroupFaqBinding
import com.surelabsid.newmrjempoot.response.DataFaqItem


class AdapterFaq(private var context: Context, private val data: List<DataFaqItem?>) :
    BaseExpandableListAdapter() {

    private lateinit var groupBinding: ListGroupFaqBinding
    private lateinit var itemBinding: ItemAdapterFaqBinding
    private val inflate = LayoutInflater.from(context)

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data.size
    }

    override fun getGroup(groupPosition: Int): Any? {
        return this.data.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return this.data.get(groupPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        val holder: GroupViewHolder

        if (view == null) {
            groupBinding = ListGroupFaqBinding.inflate(inflate)
            view = groupBinding.root
            holder = GroupViewHolder()
            holder.label = groupBinding.listTitle
            view.tag = holder
        } else {
            holder = view.tag as GroupViewHolder
        }

        val listTitle = getGroup(groupPosition) as DataFaqItem
        holder.label?.text = listTitle.pertanyaan
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View {
        var convertView = view
        val holder: IteViewHolder
        if (convertView == null) {
            itemBinding = ItemAdapterFaqBinding.inflate(inflate)
            convertView = itemBinding.root
            holder = IteViewHolder()
            holder.label = itemBinding.expandedListItem
            convertView.tag = holder
        } else {
            holder = convertView.tag as IteViewHolder
        }
        val expandedListText = getChild(groupPosition, childPosition) as DataFaqItem
        holder.label!!.text = expandedListText.jawaban
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    inner class IteViewHolder {
        internal var label: TextView? = null
    }

    inner class GroupViewHolder {
        internal var label: TextView? = null
    }

}