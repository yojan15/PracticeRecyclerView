package com.example.practicerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicerecyclerview.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
   private lateinit var binding : ActivityMainBinding

    private val todoList = mutableListOf<Todo>()
     lateinit var adapter :TodoAdapter  //variable of our adapter
    //private val adapter = TodoAdapter(todoList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val rvTodo = findViewById<RecyclerView>(R.id.recyclerView)


        // on below line we are initializing our adapter
        adapter = TodoAdapter(todoList)

        rvTodo.layoutManager = LinearLayoutManager(this)
        //on below line we are setting adapter to our recycler view.
        rvTodo.adapter = adapter
        //Note rvTodos refer to RecyclerView from Xml file



        //the we will add a button to add item in our list
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val etTodo = findViewById<EditText>(R.id.editText)
            val title = etTodo.text.toString()

            if (title.isNotEmpty()) {
                todoList.add(Todo(title))
                Log.e("MainActivity", "Item added: $title")
                //Note : the below code will update the position when item inserted
                adapter.notifyItemInserted(todoList.size - 1)
                Log.e("MainActivity", "Item added: $todoList")
                etTodo.text.clear()
            } else {        //Incas user try to input empty item in list it will give a toast message
                Toast.makeText(this, "Please enter a todo item", Toast.LENGTH_SHORT).show()
            }
        }
        // swipe from left or right to delete the item from list
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
                Log.e("MainActivity", "Item removed , new list is :  $todoList")

            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvTodo)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }
//    private fun filterList(query: String?) {
//        Log.e("TAG", "query: $query")
//        val filterList = mutableListOf<Todo>()
//        if(query != null){
//            Log.e("TAG", "todoList: $todoList")
//            for(i in todoList) {
//                if(i.title.lowercase(Locale.ROOT).contains(query)) {
//                    filterList.add(i)
//                }
//                Log.e("TAG", "filterList: $filterList")
//                if(filterList.isEmpty()) {
//                    Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.e("TAG", "setFilteredList: $filterList")
//                    adapter.setFilteredList(filterList)
//                }
//            }
//        }
//    }
//}

    private fun filterList(query: String?) {
        val filteredList = mutableListOf<Todo>()
        if (query != null) {
            for (i in todoList) {
                if (i.title.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(i)
                }
            }
        }
        if (query.isNullOrEmpty()) {
            adapter.setFilteredList(todoList)
        } else {
            adapter.setFilteredList(filteredList)
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}