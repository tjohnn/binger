package ng.max.binger.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_genre.view.*
import ng.max.binger.R
import ng.max.binger.data.Genre
import ng.max.binger.data.ProductionCompany
import ng.max.binger.utils.DisplayUtils

class GenreAdapter:RecyclerView.Adapter<GenreAdapter.ViewHolder>(){
    var genres = arrayListOf<Genre>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_genre, parent, false))
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.genreName.text = genres[position].name
    }

    fun addAll(list: List<Genre>) {
        for (result in list) {
            add(result)
        }
    }

    fun add(r: Genre) {
        genres.add(r)
        notifyItemInserted(genres.size - 1)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var genreName = view.findViewById<TextView>(R.id.genreName)!!
    }
}

class ProCompany(var context: Context):RecyclerView.Adapter<ProCompany.ViewHolder>(){
    var proCompanies = arrayListOf<ProductionCompany>()
    override fun getItemCount(): Int {
        return proCompanies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(DisplayUtils.getImageUrl(proCompanies[position].logoPath))
                .into(holder.ccmpanyLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_production_company, parent, false))
    }

    fun addAll(list: List<ProductionCompany>) {
        for (result in list) {
            add(result)
        }
    }

    fun add(r: ProductionCompany) {
        proCompanies.add(r)
        notifyItemInserted(proCompanies.size - 1)
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var ccmpanyLogo = view.findViewById<ImageView>(R.id.companyLogo)!!
    }
}