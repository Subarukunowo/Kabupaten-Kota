package com.sandy.kabupatenkotariauapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandy.kabupatenkotariauapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var title: String = "Mode List View"
    private var listData: ArrayList<KabKota> = arrayListOf()
    private var currentViewMode: String = "LIST" // Default mode adalah List

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Pulihkan mode tampilan dari savedInstanceState
        currentViewMode = savedInstanceState?.getString("CURRENT_VIEW_MODE") ?: "LIST"
        title = when (currentViewMode) {
            "GRID" -> "Mode Grid View"
            "CARD" -> "Mode Card View"
            else -> "Mode List View"
        }
        supportActionBar?.title = title

        listData.addAll(KabKotaData.listDataKabKota)

        // Tampilkan mode sesuai dengan status yang disimpan
        when (currentViewMode) {
            "GRID" -> showRecyclerGrid()
            "CARD" -> showRecyclerCardView()
            else -> showRecyclerList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_VIEW_MODE", currentViewMode) // Simpan mode tampilan
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                title = "Mode List View"
                currentViewMode = "LIST"
                showRecyclerList()
            }
            R.id.action_grid -> {
                title = "Mode Grid View"
                currentViewMode = "GRID"
                showRecyclerGrid()
            }
            R.id.action_cardview -> {
                title = "Mode Card View"
                currentViewMode = "CARD"
                showRecyclerCardView()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        setActionBarTitle(title)
        return true
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showRecyclerList() {
        binding.rvKabKota.layoutManager = LinearLayoutManager(this)
        val kabKotaAdapter = ListKabKotaAdapter(listData)
        binding.rvKabKota.adapter = kabKotaAdapter
        kabKotaAdapter.setOnItemClickCallback(object : ListKabKotaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KabKota) {
                showDataKabKota(data)
            }
        })
    }

    private fun showRecyclerGrid() {
        binding.rvKabKota.layoutManager = GridLayoutManager(this, 2)
        val gridKabKotaAdapter = GridKabKotaAdapter(listData)
        binding.rvKabKota.adapter = gridKabKotaAdapter
        gridKabKotaAdapter.setOnItemClickCallback(object : GridKabKotaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KabKota) {
                showDataKabKota(data)
            }
        })
    }

    private fun showRecyclerCardView() {
        binding.rvKabKota.layoutManager = LinearLayoutManager(this)
        val cardKabKotaAdapter = CardKabKotaAdapter(listData)
        binding.rvKabKota.adapter = cardKabKotaAdapter
        cardKabKotaAdapter.setOnItemClickCallback(object : CardKabKotaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KabKota) {
                showDataKabKota(data)
            }
        })
    }

    private fun showDataKabKota(data: KabKota) {
        val moveWithObjectIntent = Intent(this@MainActivity, DetailKabKotaActivity::class.java)
        moveWithObjectIntent.putExtra(DetailKabKotaActivity.EXTRA_KAB_KOTA, data)
        startActivity(moveWithObjectIntent)
    }
}
