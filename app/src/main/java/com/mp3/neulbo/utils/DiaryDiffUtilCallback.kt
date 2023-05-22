package com.mp3.neulbo.utils

import androidx.recyclerview.widget.DiffUtil
import com.mp3.neulbo.model.pojo.Diary

class DiaryDiffUtilCallback : DiffUtil.ItemCallback<Diary>() {

    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.updatedAt == newItem.updatedAt
    }

}