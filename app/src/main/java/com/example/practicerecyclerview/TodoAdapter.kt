package com.example.practicerecyclerview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
* Every Adapter of RecyclerView needs have inner class of ViewHolder to hold the view*/
class TodoAdapter(private var todos : MutableList<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    /*
    * add constructor todos list then inherit RecyclerView to define todoAdapter as RecyclerView
    *then Override RecyclerView methods as follow (onCreateVIewHolder , onBindViewHolder, getItemCount)
    * */
    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) // inner class to hold the view


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        /*
        * onCreate function is called when user scroll new view is created
        * */
        val view = LayoutInflater.from(parent.context).inflate((R.layout.item),parent,false)
        /*LayoutInflater - this is a class,
        * which can create a View element from the layout files' content.
        * The inflate method is that one which does it.z
        * */
        return TodoViewHolder(view)
    }
    override fun getItemCount(): Int {
        // Note do not return 0 because it will return empty list
        //always return size of the list mentioned
      return todos.size
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        //onBindViewHolder is use to bind and add the item to corresponding view
       holder.itemView.apply {
           val tvTitle = findViewById<TextView>(R.id.tvTitle)
           tvTitle.text = todos[position].title
       }
    }
    fun deleteItem(position: Int) {
        todos.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setFilteredList(todos: MutableList<Todo>) {
        this.todos = todos
        notifyDataSetChanged()

    }

}