package eg.mahmoudshawky.theAir.utils

import eg.mahmoudshawky.theAir.data.models.entity.TvShow

interface OnItemClickListener<T> {
    fun onItemClicked(item: T)
}